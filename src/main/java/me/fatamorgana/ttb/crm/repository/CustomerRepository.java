package me.fatamorgana.ttb.crm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import me.fatamorgana.ttb.crm.model.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
