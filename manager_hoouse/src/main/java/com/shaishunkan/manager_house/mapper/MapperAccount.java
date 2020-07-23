package com.shaishunkan.manager_house.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.shaishunkan.manager_house.model.Account;

@Repository
public interface MapperAccount {
	
	public int insert(Account account);
	
	public int update(Account account);
	
	public Account getByEmail(String mail);
	
	public List<Account> getAllAccount(Account account);
	
	public Account getById(int id);
}
