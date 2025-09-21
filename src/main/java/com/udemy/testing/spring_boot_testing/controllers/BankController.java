package com.udemy.testing.spring_boot_testing.controllers;

import com.udemy.testing.spring_boot_testing.models.Bank;
import com.udemy.testing.spring_boot_testing.services.BankService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/banks")
@Tag(name = "Bank Controller", description = "Bank management endpoints")
public class BankController {
    @Autowired
    private BankService bankService;

    @Operation(summary = "Get a bank by ID", description = "Returns a bank based on ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Bank found"),
        @ApiResponse(responseCode = "404", description = "Bank not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Bank> getBank(@PathVariable Long id) {
        return bankService.getBank(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Create a new bank", description = "Creates a new bank and returns it")
    @ApiResponse(responseCode = "200", description = "Bank created successfully")
    @PostMapping
    public ResponseEntity<Bank> createBank(@RequestBody Bank bank) {
        return ResponseEntity.ok(bankService.createBank(bank.getName(), bank.getTransfersTotal()).orElse(null));
    }

    @Operation(summary = "Update a bank", description = "Updates an existing bank's information")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Bank updated successfully"),
        @ApiResponse(responseCode = "404", description = "Bank not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Bank> updateBank(@PathVariable Long id, @RequestBody Bank bank) {
        return bankService.updateBank(id, bank.getName(), bank.getTransfersTotal())
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Delete a bank", description = "Deletes a bank by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Bank deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Bank not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBank(@PathVariable Long id) {
        boolean deleted = bankService.deleteBank(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
