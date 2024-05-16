package com.abc.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abc.model.Customer;

public interface CustomerRepo extends JpaRepository<Customer, Integer> {

}
