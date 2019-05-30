package com.dkadirbekov.dao;

import com.dkadirbekov.model.Customer;

import java.util.List;

/**
 * DAO class for {@link Customer} entity
 */
public interface CustomerDao {

    List<Customer> getAll();

    void add(Customer customer);

    Customer getByCode(String code);

}
