package me.fatamorgana.ttb.crm.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import me.fatamorgana.ttb.crm.constant.StatusCode;
import me.fatamorgana.ttb.crm.model.Customer;
import me.fatamorgana.ttb.crm.service.CustomerService;

@ExtendWith(MockitoExtension.class)
class CustomerControllerTest {

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private CustomerController customerController;

    private MockMvc mockMvc;
    private Customer customer;
    private Customer updatedCustomer;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();

        LocalDateTime current = LocalDateTime.now();
    	
        customer = new Customer();
        customer.setCustomerId(1L);
        customer.setFirstname("Kopkaj");
        customer.setLastname("Oupapatig");
        customer.setCustomerDate(LocalDateTime.now());
        customer.setIsVip(true);
        customer.setStatusCode(StatusCode.ACTIVE);
        customer.setCreatedOn(current);
        customer.setModifiedOn(current);
        

        updatedCustomer = new Customer();
        updatedCustomer.setFirstname("Barack");
        updatedCustomer.setLastname("Obama");
        updatedCustomer.setStatusCode(StatusCode.ACTIVE);
    }

    @Test
    void testGetCustomerById_ShouldReturnCustomer() throws Exception {
        when(customerService.getCustomerById(1L)).thenReturn(Optional.of(customer));

        mockMvc.perform(get("/api/v1/customers/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstname", is("Kopkaj")))
                .andExpect(jsonPath("$.lastname", is("Oupapatig")))
                .andExpect(jsonPath("$.statusCode", is("Active")));

        verify(customerService, times(1)).getCustomerById(1L);
    }

    @Test
    void testGetCustomerById_ShouldReturnCustomer_NotFound() throws Exception {
        when(customerService.getCustomerById(2L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/customers/2"))
                .andExpect(status().isNotFound());

        verify(customerService, times(1)).getCustomerById(2L);
    }

    @Test
    void testCreateCustomer_ShouldReturnCreatedCustomer() throws Exception {
        when(customerService.createCustomer(any(Customer.class))).thenReturn(customer);

        mockMvc.perform(post("/api/v1/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "firstname": "Kopkaj",
                            "lastname": "Oupapatig",
                            "statusCode": "Active"
                        }
                        """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstname", is("Kopkaj")))
                .andExpect(jsonPath("$.lastname", is("Oupapatig")))
                .andExpect(jsonPath("$.statusCode", is("Active")));

        verify(customerService, times(1)).createCustomer(any(Customer.class));
    }

    @Test
    void testUpdateCustomer_ShouldUpdateAndReturnCustomer() throws Exception {
        when(customerService.updateCustomer(eq(1L), eq(updatedCustomer))).thenReturn(updatedCustomer);
        
        mockMvc.perform(put("/api/v1/customers/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "firstname": "Barack",
                            "lastname": "Obama",
                            "statusCode": "Active"
                        }
                        """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstname", is("Barack")))
                .andExpect(jsonPath("$.lastname", is("Obama")))
                .andExpect(jsonPath("$.statusCode", is("Active")));
        
        verify(customerService, times(1)).updateCustomer(eq(1L), eq(updatedCustomer));
    }

    @Test
    void testSaveCustomer_ShouldUpdateFailed() throws Exception {
        when(customerService.updateCustomer(eq(2L), eq(updatedCustomer))).thenThrow(new RuntimeException("Customer not found with ID 2"));
        
        mockMvc.perform(put("/api/v1/customers/2")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "firstname": "Barack",
                            "lastname": "Obama",
                            "statusCode": "Active"
                        }
                        """))
                .andExpect(status().isNotFound());
        
        verify(customerService, times(1)).updateCustomer(eq(2L), eq(updatedCustomer));
    }
}
