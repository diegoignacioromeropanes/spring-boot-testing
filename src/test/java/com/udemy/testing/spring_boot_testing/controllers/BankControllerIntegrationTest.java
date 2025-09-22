package com.udemy.testing.spring_boot_testing.controllers;

import com.udemy.testing.spring_boot_testing.models.Bank;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BankControllerIntegrationTest {

    @Autowired
    TestRestTemplate restTemplate;

    @Test
    void testCreateAndGetBank() {
        Bank bank = new Bank("Banco de Chile", 55);
        ResponseEntity<Bank> createResponse = restTemplate.postForEntity("/banks", bank, Bank.class);
        assertEquals(HttpStatus.OK, createResponse.getStatusCode());
        assertNotNull(createResponse.getBody());
        assertEquals("Banco de Chile", createResponse.getBody().getName());

        ResponseEntity<Bank> getResponse = restTemplate.getForEntity("/banks/3", Bank.class);
        assertEquals(HttpStatus.OK, getResponse.getStatusCode());
        assertNotNull(getResponse.getBody());
        assertEquals("Banco de Chile", getResponse.getBody().getName());
        assertEquals(55, getResponse.getBody().getTransfersTotal());
    }

    @Test
    void testUpdateBank() {
        ResponseEntity<Bank> getResponse = restTemplate.getForEntity("/banks/2", Bank.class);
        assertEquals(HttpStatus.OK, getResponse.getStatusCode());
        assertNotNull(getResponse.getBody());
        assertEquals("Test Bank", getResponse.getBody().getName());
        assertEquals(5, getResponse.getBody().getTransfersTotal());

        Bank bank = getResponse.getBody();
        bank.setName("Test Bank Updated");
        bank.setTransfersTotal(30);

        ResponseEntity<Bank> updateResponse = restTemplate.exchange(
                "/banks/2",
                HttpMethod.PUT,
                new HttpEntity<>(bank),
                Bank.class);
        assertEquals(HttpStatus.OK, updateResponse.getStatusCode());
        assertNotNull(updateResponse.getBody());
        assertEquals("Test Bank Updated", updateResponse.getBody().getName());
        assertEquals(30, updateResponse.getBody().getTransfersTotal());

        ResponseEntity<Bank> verifyResponse = restTemplate.getForEntity("/banks/2", Bank.class);
        assertEquals(HttpStatus.OK, verifyResponse.getStatusCode());
        assertNotNull(verifyResponse.getBody());
        assertEquals("Test Bank Updated", verifyResponse.getBody().getName());
        assertEquals(30, verifyResponse.getBody().getTransfersTotal());
    }

    @Test
    void testDeleteBank() {
        ResponseEntity<Bank> getResponse = restTemplate.getForEntity("/banks/2", Bank.class);
        assertEquals(HttpStatus.OK, getResponse.getStatusCode());
        assertNotNull(getResponse.getBody());
        assertEquals("Test Bank", getResponse.getBody().getName());
        assertEquals(5, getResponse.getBody().getTransfersTotal());

        ResponseEntity<Void> deleteResponse = restTemplate.exchange(
                "/banks/2",
                HttpMethod.DELETE,
                null,
                Void.class);
        assertEquals(HttpStatus.NO_CONTENT, deleteResponse.getStatusCode());
        assertFalse(deleteResponse.hasBody());

        ResponseEntity<Bank> verifyResponse = restTemplate.getForEntity("/banks/2", Bank.class);
        assertEquals(HttpStatus.NOT_FOUND, verifyResponse.getStatusCode());
        assertFalse(verifyResponse.hasBody());
    }
}
