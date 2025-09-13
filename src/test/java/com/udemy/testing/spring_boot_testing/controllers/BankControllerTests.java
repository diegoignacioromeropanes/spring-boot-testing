package com.udemy.testing.spring_boot_testing.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.udemy.testing.spring_boot_testing.models.Bank;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class BankControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreateAndGetBank() throws Exception {
        Bank bank = new Bank(1L, "Bank A", 10);
        mockMvc.perform(post("/banks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(bank)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Bank A"));

        mockMvc.perform(get("/banks/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.transferTotal").value(10));
    }

    @Test
    void testUpdateBank() throws Exception {
        Bank bank = new Bank(2L, "Bank B", 20);
        mockMvc.perform(post("/banks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(bank)))
                .andExpect(status().isOk());

        Bank updated = new Bank(2L, "Bank B Updated", 30);
        mockMvc.perform(put("/banks/2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Bank B Updated"))
                .andExpect(jsonPath("$.transferTotal").value(30));
    }

    @Test
    void testDeleteBank() throws Exception {
        Bank bank = new Bank(3L, "Bank C", 40);
        mockMvc.perform(post("/banks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(bank)))
                .andExpect(status().isOk());

        mockMvc.perform(delete("/banks/3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Bank C"));

        mockMvc.perform(get("/banks/3"))
                .andExpect(status().isNotFound());
    }
}

