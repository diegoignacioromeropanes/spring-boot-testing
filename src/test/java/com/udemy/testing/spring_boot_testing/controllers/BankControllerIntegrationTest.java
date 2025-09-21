package com.udemy.testing.spring_boot_testing.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.udemy.testing.spring_boot_testing.models.Bank;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BankControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreateAndGetBank() throws Exception {
        Bank bank = new Bank("Banco de Chile", 55);
        mockMvc.perform(post("/banks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(bank)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Banco de Chile"));

        mockMvc.perform(get("/banks/3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Banco de Chile"))
                .andExpect(jsonPath("$.transfersTotal").value(55));
    }

    @Test
    void testUpdateBank() throws Exception {
        mockMvc.perform(get("/banks/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Bank"))
                .andExpect(jsonPath("$.transfersTotal").value(5));

        Bank updated = new Bank("Test Bank Updated", 30);
        mockMvc.perform(put("/banks/2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Bank Updated"))
                .andExpect(jsonPath("$.transfersTotal").value(30));

        mockMvc.perform(get("/banks/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Bank Updated"))
                .andExpect(jsonPath("$.transfersTotal").value(30));

    }

    @Test
    void testDeleteBank() throws Exception {
        mockMvc.perform(get("/banks/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Bank"))
                .andExpect(jsonPath("$.transfersTotal").value(5));

        mockMvc.perform(delete("/banks/2"))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/banks/2"))
                .andExpect(status().isNotFound());
    }
}

