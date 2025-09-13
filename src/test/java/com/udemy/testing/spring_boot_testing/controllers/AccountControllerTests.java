package com.udemy.testing.spring_boot_testing.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.udemy.testing.spring_boot_testing.models.Account;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AccountControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreateAndGetAccount() throws Exception {
        Account account = new Account(1L, "John Doe", new BigDecimal("100.00"));
        mockMvc.perform(post("/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(account)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.person").value("John Doe"));

        mockMvc.perform(get("/accounts/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.balance").value(100.00));
    }

    @Test
    void testUpdateAccount() throws Exception {
        Account account = new Account(2L, "Jane Doe", new BigDecimal("200.00"));
        mockMvc.perform(post("/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(account)))
                .andExpect(status().isOk());

        Account updated = new Account(2L, "Jane Smith", new BigDecimal("300.00"));
        mockMvc.perform(put("/accounts/2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.person").value("Jane Smith"))
                .andExpect(jsonPath("$.balance").value(300.00));
    }

    @Test
    void testDeleteAccount() throws Exception {
        Account account = new Account(3L, "Mark Doe", new BigDecimal("400.00"));
        mockMvc.perform(post("/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(account)))
                .andExpect(status().isOk());

        mockMvc.perform(delete("/accounts/3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.person").value("Mark Doe"));

        mockMvc.perform(get("/accounts/3"))
                .andExpect(status().isNotFound());
    }
}

