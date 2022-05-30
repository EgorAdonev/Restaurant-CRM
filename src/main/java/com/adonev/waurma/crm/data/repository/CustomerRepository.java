package com.adonev.waurma.crm.data.repository;

import com.adonev.waurma.crm.data.entity.Customer;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
