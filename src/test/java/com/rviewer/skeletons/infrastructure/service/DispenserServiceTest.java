package com.rviewer.skeletons.infrastructure.service;


import com.rviewer.skeletons.domain.responses.DispenserFlowVolumeRequest;
import com.rviewer.skeletons.domain.services.DispenserService;
import com.rviewer.skeletons.domain.services.persistence.DispenserRepository;
import com.rviewer.skeletons.infrastructure.persistence.Dispenser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class DispenserServiceTest {
    @Mock
    private DispenserRepository dispenserRepository;

@Mock
    private DispenserService dispenserUnderTestService;

    @BeforeEach
    void setUp(){

        dispenserUnderTestService = new DispenserService(dispenserRepository);
    }



    @Test
    void canWeCreateDispenser() {
        //given

        DispenserFlowVolumeRequest dispenserFlowVolumeRequest = new DispenserFlowVolumeRequest(0.01);


        //when
       dispenserUnderTestService.createDispenser(dispenserFlowVolumeRequest);

        //then
        ArgumentCaptor<Dispenser> dispenserArgumentCaptor= ArgumentCaptor.forClass(Dispenser.class);
        verify(dispenserRepository).save(dispenserArgumentCaptor.capture());
        Dispenser capturedDispenser =   dispenserArgumentCaptor.getValue();
        assertThat(capturedDispenser).isEqualTo(dispenserFlowVolumeRequest);

    }


    // todo : getDispenserStatistics test

    @Test
    @Disabled
    void getDispenserTest() {
        //when
        dispenserUnderTestService.getDispenserById(UUID.fromString("0a2befab-0645-43f0-978f-e98bf69ecdb6"));

        //then
        verify(dispenserRepository).findById(UUID.fromString("0a2befab-0645-43f0-978f-e98bf69ecdb6"));

    }



   // todo : change dispenser statistics

    @Test
    @Disabled
    void changeDispenserStatistics() {
    }

    // todo : test count statistics

    @Test
    @Disabled
    void countLitres() {
    }




}