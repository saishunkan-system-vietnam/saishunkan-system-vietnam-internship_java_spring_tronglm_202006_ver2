package com.shaishunkan.manager_house.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.shaishunkan.manager_house.model.Customer;

@Repository
public interface MapperCustomer {

	
	public int insert(Customer customer);
	
	public int update(Customer customer);
	
	public List<Customer> getByStatus(int status_flg);
}
