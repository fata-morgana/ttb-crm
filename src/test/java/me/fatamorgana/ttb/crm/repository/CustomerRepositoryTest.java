package me.fatamorgana.ttb.crm.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import me.fatamorgana.ttb.crm.constant.StatusCode;
import me.fatamorgana.ttb.crm.model.Customer;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;
    
    private Customer customer;
	private LocalDateTime current = LocalDateTime.now();
    
    @BeforeEach
    void setUp() {
    	customer = new Customer();
    	customer.setFirstname("Kopkaj");
    	customer.setLastname("Oupapatig");
    	customer.setCustomerDate(current);
    	customer.setIsVip(false);
    	customer.setStatusCode(StatusCode.ACTIVE);
    	customer.setCreatedOn(current);
    	customer.setModifiedOn(current);
    	
    	customer = customerRepository.save(customer);
    }

    @Test
    void testSaveAndFindCustomer() {
    	LocalDateTime localCurrent = LocalDateTime.now();
        Customer newCustomer = new Customer();
        newCustomer.setFirstname("Barack");
        newCustomer.setLastname("Obama");        
        newCustomer.setCustomerDate(current);
        newCustomer.setIsVip(false);
        newCustomer.setStatusCode(StatusCode.ACTIVE);
        newCustomer.setCreatedOn(localCurrent);
        newCustomer.setModifiedOn(localCurrent);

        newCustomer = customerRepository.save(newCustomer);
        Optional<Customer> found = customerRepository.findById(newCustomer.getCustomerId());

        assertTrue(found.isPresent());
        assertEquals("Barack", found.get().getFirstname());
        assertEquals("Obama", found.get().getLastname());
        assertEquals(current, found.get().getCustomerDate());
        assertFalse(found.get().getIsVip());
        assertEquals(StatusCode.ACTIVE, found.get().getStatusCode());
    }

    @Test
    void testFindById_CustomerExists() {
        Optional<Customer> foundCustomer = customerRepository.findById(customer.getCustomerId());
        assertTrue(foundCustomer.isPresent());
        assertEquals("Kopkaj", foundCustomer.get().getFirstname());
        assertEquals("Oupapatig", foundCustomer.get().getLastname());
        assertEquals(current, foundCustomer.get().getCustomerDate());
        assertFalse(foundCustomer.get().getIsVip());
        assertEquals(StatusCode.ACTIVE, foundCustomer.get().getStatusCode());
    }

    @Test
    void testFindById_CustomerNotFound() {
        Optional<Customer> foundCustomer = customerRepository.findById(1111L);
        assertFalse(foundCustomer.isPresent());
    }

    @Test
    void testFindAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        assertFalse(customers.isEmpty());
        assertEquals(1, customers.size());
    }

    @Test
    void testDeleteById() {
        customerRepository.deleteById(customer.getCustomerId());
        Optional<Customer> deletedCustomer = customerRepository.findById(customer.getCustomerId());
        assertFalse(deletedCustomer.isPresent());
    }
}
