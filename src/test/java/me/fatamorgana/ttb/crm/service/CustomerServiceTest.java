package me.fatamorgana.ttb.crm.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import me.fatamorgana.ttb.crm.constant.StatusCode;
import me.fatamorgana.ttb.crm.model.Customer;
import me.fatamorgana.ttb.crm.repository.CustomerRepository;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    private Customer customer;
    private Customer updatedCustomer;


    @BeforeEach
    void setUp() {
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
        updatedCustomer.setCustomerId(1L);
        updatedCustomer.setFirstname("Barack");
        updatedCustomer.setLastname("Obama");
        updatedCustomer.setCustomerDate(LocalDateTime.now());
        updatedCustomer.setIsVip(true);
        updatedCustomer.setStatusCode(StatusCode.ACTIVE);
        updatedCustomer.setCreatedOn(current);
        updatedCustomer.setModifiedOn(current);
    }

    @Test
    void testGetCustomerById_ShouldReturnCustomer() {
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));

        Optional<Customer> maybeCustomer = customerService.getCustomerById(1L);
        assertNotNull(maybeCustomer);
        assertTrue(maybeCustomer.isPresent());
        
        Customer customer = maybeCustomer.orElseThrow();
        assertNotNull(customer);
        assertEquals("Kopkaj", customer.getFirstname());
        assertEquals("Oupapatig", customer.getLastname());
        verify(customerRepository, times(1)).findById(1L);
    }

    @Test
    void testGetCustomerById_NotFound() {
        when(customerRepository.findById(2L)).thenReturn(Optional.empty());

        Optional<Customer> maybeCustomer = customerService.getCustomerById(2L);
        assertNotNull(maybeCustomer);
        assertTrue(maybeCustomer.isEmpty());
    }

    @Test
    void testSaveCustomer_ShouldSaveAndReturnCustomer() {
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        Customer savedCustomer = customerService.createCustomer(customer);

        assertNotNull(savedCustomer);
        assertEquals("Kopkaj", savedCustomer.getFirstname());
        verify(customerRepository, times(1)).save(customer);
    }

    @Test
    void testSaveCustomer_ShouldUpdateAndReturnCustomer() {
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(customerRepository.save(eq(updatedCustomer))).thenReturn(updatedCustomer);
        Customer newCustomer = customerService.updateCustomer(1L, updatedCustomer);

        assertNotNull(newCustomer);
        assertEquals("Barack", newCustomer.getFirstname());
        assertEquals("Obama", newCustomer.getLastname());
        verify(customerRepository, times(1)).findById(1L);
        verify(customerRepository, times(1)).save(customer);
    }

    @Test
    void testSaveCustomer_ShouldUpdateFailed() {
        when(customerRepository.findById(2L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, 
        		() -> customerService.updateCustomer(2L, updatedCustomer),
        		"Customer not found with ID 2");
    }
}
