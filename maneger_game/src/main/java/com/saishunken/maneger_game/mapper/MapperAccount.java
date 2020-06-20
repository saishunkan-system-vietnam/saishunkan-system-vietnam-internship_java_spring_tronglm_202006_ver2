package com.saishunken.maneger_game.mapper;

import java.math.BigInteger;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.saishunken.maneger_game.model.Account;

@Repository
public interface MapperAccount {

   // insert 
	public void insert(Account userAccount);
	
	// update account
	public void update(Account userAccount);
	
	// get by id
	public Account getById(int id);
	
	// get account by email
	public Account getByEmail(String mail);
	
	//get account by name
	public Account getByName(String name);
	
	// get all account
	public List<Account> getAll(Account account);
	
	// total all user
	public BigInteger getTotalUser(Account account);
	
	// get total
	public int getTotalByName(@Param("name_account") String name, @Param("id") Integer id );	
	public int getTotalByPhone(@Param("phone_number") String phone_number, @Param("id") Integer id);
	public int getTotalByMail(@Param("mail") String mail, @Param("id") Integer id);
	
}
