package com.rviewer.skeletons.infrastructure.controllers;



import com.rviewer.skeletons.domain.responses.DispenserFlowVolumeRequest;
import com.rviewer.skeletons.domain.responses.DispenserResponse;
import com.rviewer.skeletons.domain.responses.DispenserStatusRequest;
import com.rviewer.skeletons.domain.services.DispenserService;
import com.rviewer.skeletons.infrastructure.persistence.Dispenser;
import com.rviewer.skeletons.infrastructure.persistence.Status;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/beer-tap-dispenser/dispenser")
@Slf4j
public class DispenserController {
    private  final DispenserService dispenserService;

    public DispenserController(DispenserService dispenserService) {
        this.dispenserService = dispenserService;
    }

    @PostMapping
    public ResponseEntity<Dispenser> createDispenser(@RequestBody DispenserFlowVolumeRequest flowVolume){
        log.info("new dispenser flow_volume {}",flowVolume);
        return  ResponseEntity.ok(dispenserService.createDispenser(flowVolume));
    }
    @PutMapping(value = "/{id}/status")
    public ResponseEntity<String> updateDispenserConfigs(@PathVariable("id") UUID id, @RequestBody DispenserStatusRequest status){
        log.info("new dispenserStatistics uuid {} and status {}",id ,status);
        return ResponseEntity.ok(dispenserService.changeStatus(id,status));
    }

  @GetMapping(value = "/{id}/dispenser")
    public ResponseEntity<Dispenser> getDispenserDetails(@PathVariable("id")UUID id){
      log.info("get dispenserStatistics with uuid {} ",id );
        return  ResponseEntity.ok(dispenserService.getDispenserById(id));
  }

    @GetMapping(value = "/{id}/spending")
    public ResponseEntity<DispenserResponse> getDispenserActionStatistics(@PathVariable("id")UUID id){
        log.info("get dispenser Actions Statistics with uuid {} ",id );
        return  ResponseEntity.ok(dispenserService.getDispenserStatistics(id));
    }
}
