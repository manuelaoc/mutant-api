package com.magneto.core.controller;

import com.magneto.core.ApplicationMock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = ApplicationMock.class)
@WebMvcTest(StatsQueryController.class)
public class StatsQueryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getStatsCorrectlyTest() throws Exception {
        // arrange - act - assert
        mockMvc.perform(get("/stats").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.countMutantDna", is(2)))
                .andExpect(jsonPath("$.countHumanDna", is(4)))
                .andExpect(jsonPath("$.ratio", is(0.5)));
    }

    @Test
    public void getStatsWithWrongUrlTest() throws Exception {
        // arrange - act - assert
        mockMvc.perform(get("/stat").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
