package com.rviewer.skeletons.domain.services;


import com.rviewer.skeletons.domain.responses.*;

import com.rviewer.skeletons.domain.services.persistence.DispenserRepository;

import com.rviewer.skeletons.exception.DispenserStatusException;
import com.rviewer.skeletons.infrastructure.persistence.Dispenser;
import com.rviewer.skeletons.infrastructure.persistence.DispenserAction;
import com.rviewer.skeletons.infrastructure.persistence.Status;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor

public class DispenserService {

    // This Will Make Sure The List Is Always Sorted With Opening Time
    private static final Comparator<DispenserAction> BY_OPEN_AT = (a, b) -> b.getOpenAt().compareTo(a.getOpenAt());
    private final DispenserRepository dispenserRepository;


    // create a dispenser
    public Dispenser createDispenser(DispenserFlowVolumeRequest flowVolume) {
        var dispenser = Dispenser.builder()
                .flowVolume(flowVolume.flowVolume())
                .build();
        dispenser.createAction();

        return dispenserRepository.save(dispenser);
    }

    public String changeStatus(UUID dispenserId, DispenserStatusRequest dispenserStatusRequest) {
        Dispenser dispenser = dispenserRepository.findById(dispenserId).orElseThrow();
        if (dispenser.getStatus() == dispenserStatusRequest.status()) {
            log.error("Error : {} --> {}", dispenser.getStatus(), dispenserStatusRequest.status());
            throw new DispenserStatusException(409,"Dispenser is already opened/closed");
        }
        List<DispenserAction> actions = dispenser.getDispenserActions();


        actions.sort(BY_OPEN_AT);
        DispenserAction action = actions.get(0);

        if (action.getOpenAt() != null && dispenser.getStatus() == Status.CLOSE) {
            //***** Creating an Action to save Dispenser Actions
            dispenser.setStatus(dispenserStatusRequest.status());
            DispenserAction newDispenser = new DispenserAction();

            // todo : if update_at is to come from Request then  there are Time Validations that needs to be done first.
            // todo :opening time (updated_at) should come from the request.NB : as it is real time -> (but localDateTime.now() is better).
            newDispenser.setOpenAt(LocalDateTime.now());


            actions.add(newDispenser);
            dispenserRepository.save(dispenser);
            return "Updated ..";
        }


        dispenser.setStatus(dispenserStatusRequest.status());
        if (dispenserStatusRequest.status().equals(Status.CLOSE)) {
            // todo :closing time (updated_at) should come from the request. NB : as it is real time-> (but localDateTime.now() is better).

            action.setCloseAt(LocalDateTime.now());
        } else {
            // todo :opening time (updated_at) should come from the request. NB : as it is real time-> (but localDateTime.now() is better).

            action.setOpenAt(LocalDateTime.now());
        }
        actions.set(0, action);
        dispenser.setDispenserActions(actions);
        dispenserRepository.save(dispenser);
        return "Updated.....";
    }

    public Dispenser getDispenserById(UUID dispenserId) {
        return dispenserRepository.findById(dispenserId).orElseThrow();
    }

    public DispenserResponse getDispenserStatistics(UUID dispenserId) {
        List<DispenserActionResponse> dispenserResponse = new ArrayList<>();
        // todo : normally this value is to come from Database, considering that it might change.
        final double valuePerLitre = 12.25;

        Double amount = 0.0;


        Dispenser dispenser = dispenserRepository.findById(dispenserId).orElseThrow();

        List<DispenserAction> dispenserActionList = new ArrayList<>(dispenser.getDispenserActions());
        for (DispenserAction dispenserAction : dispenserActionList) {
            var countLiters=countLitres(dispenserAction);
            dispenserResponse.add(DispenserActionDto.map(dispenserAction, dispenser.getFlowVolume(),countLiters));
            amount += dispenser.getFlowVolume() * countLiters * valuePerLitre;

        }
        dispenser.setTotalSpent(amount);
        dispenserRepository.save(dispenser);

        return DispenserDto.map(dispenser,dispenserResponse);
    }

    long countLitres(DispenserAction dispenserAction) {

        if(dispenserAction.getCloseAt() ==null){
         // when closed At is null, we use LocalDateTime.now() to get the closeAt for that moment, but we do not update Database,
            Duration duration = Duration.between(dispenserAction.getOpenAt(), LocalDateTime.now());
            return duration.toSecondsPart();
        }
        // ** DURATION IS MORE ACCURATE **
       Duration duration = Duration.between(dispenserAction.getOpenAt(), dispenserAction.getCloseAt());
       return duration.toSecondsPart();
   }



}
