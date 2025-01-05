package com.rviewer.skeletons.infrastructure.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.rviewer.skeletons.domain.services.DispenserService;
import com.rviewer.skeletons.infrastructure.persistence.Dispenser;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DispenserController.class)
@AutoConfigureMockMvc
class DispenserControllerTest {

    private static  final  String END_POINT_PATH ="/api/v1/beer-tap-dispenser/dispenser";

    @Autowired
    private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;
    @MockBean
    DispenserService dispenserService;



    @Test
    void testcreateDispenserShouldReturnSuccessRequest() throws Exception {
        Dispenser dispenser= Dispenser.builder()
                .flowVolume(0.2)
                .build();

       String requestBody=  objectMapper.writeValueAsString(dispenser);
       mockMvc.perform(post(END_POINT_PATH).contentType("application/json").content(requestBody)).andExpect(status().
               is2xxSuccessful()

       ).andDo(print());

    }

    @Test
    @Disabled
    void changeDispenserStatistics() {
    }

    @Test
    @Disabled
    void getDispenserStatistics() {
    }
}