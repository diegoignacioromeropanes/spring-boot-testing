package com.udemy.testing.spring_boot_testing.controllers;

import com.udemy.testing.spring_boot_testing.models.Account;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AccountControllerIntegrationTest {

    @Autowired
    TestRestTemplate restTemplate;

    @Test
    void testCreateAndGetAccount() {
        Account account = new Account("John Doe", new BigDecimal("100.00"));
        ResponseEntity<Account> createResponse = restTemplate.postForEntity("/accounts", account, Account.class);
        assertEquals(HttpStatus.OK, createResponse.getStatusCode());
        assertNotNull(createResponse.getBody());
        assertEquals("John Doe", createResponse.getBody().getPerson());
        assertEquals("100.00", createResponse.getBody().getBalance().toString());

        ResponseEntity<Account> getResponse = restTemplate.getForEntity("/accounts/4", Account.class);
        assertEquals(HttpStatus.OK, getResponse.getStatusCode());
        assertNotNull(getResponse.getBody());
        assertEquals("John Doe", getResponse.getBody().getPerson());
        assertEquals(0, new BigDecimal("100.00").compareTo(getResponse.getBody().getBalance()));
    }

    @Test
    void testUpdateAccount() {
        ResponseEntity<Account> getResponse = restTemplate.getForEntity("/accounts/3", Account.class);
        assertEquals(HttpStatus.OK, getResponse.getStatusCode());
        assertNotNull(getResponse.getBody());
        assertEquals("Charlie", getResponse.getBody().getPerson());
        assertEquals(0, new BigDecimal("250.00").compareTo(getResponse.getBody().getBalance()));

        Account account = getResponse.getBody();
        account.setPerson("Charlie Sheen");
        account.setBalance(new BigDecimal("300.00"));

        ResponseEntity<Account> updateResponse = restTemplate.exchange(
                "/accounts/3",
                HttpMethod.PUT,
                new HttpEntity<>(account),
                Account.class);
        assertEquals(HttpStatus.OK, updateResponse.getStatusCode());
        assertNotNull(updateResponse.getBody());
        assertEquals("Charlie Sheen", updateResponse.getBody().getPerson());
        assertEquals(0, new BigDecimal("300.00").compareTo(updateResponse.getBody().getBalance()));

        ResponseEntity<Account> verifyResponse = restTemplate.getForEntity("/accounts/3", Account.class);
        assertEquals(HttpStatus.OK, verifyResponse.getStatusCode());
        assertNotNull(verifyResponse.getBody());
        assertEquals("Charlie Sheen", verifyResponse.getBody().getPerson());
        assertEquals(0, new BigDecimal("300.00").compareTo(verifyResponse.getBody().getBalance()));
    }

    @Test
    void testDeleteAccount() {
        ResponseEntity<Account> getResponse = restTemplate.getForEntity("/accounts/3", Account.class);
        assertEquals(HttpStatus.OK, getResponse.getStatusCode());
        assertNotNull(getResponse.getBody());
        assertEquals("Charlie", getResponse.getBody().getPerson());
        assertEquals(0, new BigDecimal("250.00").compareTo(getResponse.getBody().getBalance()));

        ResponseEntity<Void> deleteResponse = restTemplate.exchange(
                "/accounts/3",
                HttpMethod.DELETE,
                null,
                Void.class);
        assertEquals(HttpStatus.NO_CONTENT, deleteResponse.getStatusCode());

        ResponseEntity<Account> verifyResponse = restTemplate.getForEntity("/accounts/3", Account.class);
        assertEquals(HttpStatus.NOT_FOUND, verifyResponse.getStatusCode());
    }
}
