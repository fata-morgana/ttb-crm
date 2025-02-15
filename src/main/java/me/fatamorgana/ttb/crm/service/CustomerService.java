package me.fatamorgana.ttb.crm.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import me.fatamorgana.ttb.crm.model.Customer;
import me.fatamorgana.ttb.crm.repository.CustomerRepository;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Optional<Customer> getCustomerById(Long id) {
        return customerRepository.findById(id);
    }

    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public Customer updateCustomer(Long id, Customer updatingCustomer) {
        return customerRepository.findById(id).map(customer -> {
            customer.setFirstname(updatingCustomer.getFirstname());
            customer.setLastname(updatingCustomer.getLastname());
            customer.setCustomerDate(updatingCustomer.getCustomerDate());
            customer.setIsVip(updatingCustomer.getIsVip());
            customer.setStatusCode(updatingCustomer.getStatusCode());
            return customerRepository.save(customer);
        }).orElseThrow(() -> new RuntimeException("Customer not found with ID " + id));
    }

    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }
}

