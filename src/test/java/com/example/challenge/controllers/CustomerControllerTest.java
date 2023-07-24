package com.example.challenge.controllers;

import com.example.challenge.models.Customer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RunWith(SpringRunner.class)
@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final Map<Long, Customer> customers = new HashMap<>();

    @Before
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

    @Test
    public void testCreateCustomer() throws Exception {
        Customer newCustomer = new Customer(5L, "John Doe");
        String requestBody = "{\"id\": 5, \"name\": \"John Doe\"}";

        mockMvc.perform(MockMvcRequestBuilders.post("/customers")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(newCustomer.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(newCustomer.getName()));
    }

    @Test
    public void testGetExistingCustomer() throws Exception {
        Customer existingCustomer = customers.get(1L);

        mockMvc.perform(MockMvcRequestBuilders.get("/customers/{id}", 1L))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(existingCustomer.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(existingCustomer.getName()));
    }

    @Test
    public void testGetNonExistentCustomer() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/customers/{id}", 999L))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
