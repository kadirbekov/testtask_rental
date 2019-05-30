package com.dkadirbekov.dao.impl;

import com.dkadirbekov.dao.CustomerDao;
import com.dkadirbekov.model.Customer;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

/**
 * {@inheritDoc}
 */
public class CustomerDaoMemoryImpl implements CustomerDao {

    private static CustomerDaoMemoryImpl instance;

    private Set<Customer> customers;
    private AtomicLong seq;

    private CustomerDaoMemoryImpl() {
        customers = new LinkedHashSet<>();
        seq = new AtomicLong(0);
        migrate();
    }

    private void migrate() {
        add(new Customer("admin"));
        add(new Customer("test"));
    }

    public static CustomerDaoMemoryImpl getInstance() {
        if (instance == null) {
            synchronized (CustomerDaoMemoryImpl.class) {
                if (instance == null) {
                    instance = new CustomerDaoMemoryImpl();
                }
            }
        }
        return instance;
    }

    @Override
    public List<Customer> getAll() {
        return customers.stream().collect(Collectors.toList());
    }

    @Override
    public void add(Customer customer) {
        customer.setId(seq.incrementAndGet());
        customer.setBonusPoints(0);
        customers.add(customer);
    }

    @Override
    public Customer getByCode(String code) {
        return customers.stream().filter(customer -> customer.getCode().equals(code)).findFirst().orElse(null);
    }
}
