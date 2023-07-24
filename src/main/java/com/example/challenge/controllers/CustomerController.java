package com.example.challenge.controllers;

import com.example.challenge.models.Customer;
import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/customers")
public class CustomerController {
    private final Map<Long, Customer> customers = new HashMap<>();

    @PostConstruct
    public void init() {
        // Prepopulate customers with the provided data
        List<Customer> prepopulatedCustomers = Arrays.asList(
                new Customer(1L, "Arisha Barron"),
                new Customer(2L, "Branden Gibson"),
                new Customer(3L, "Rhonda Church"),
                new Customer(4L, "Georgina Hazel")
        );

        prepopulatedCustomers.forEach(customer -> customers.put(customer.getId(), customer));
    }

    @PostMapping
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
        customers.put(customer.getId(), customer);
        return ResponseEntity.status(HttpStatus.CREATED).body(customer);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomer(@PathVariable Long id) {
        Customer customer = customers.get(id);
        if (customer != null) {
            return ResponseEntity.ok(customer);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
