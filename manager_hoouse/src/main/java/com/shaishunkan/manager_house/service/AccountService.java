package com.shaishunkan.manager_house.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shaishunkan.manager_house.mapper.MapperAccount;
import com.shaishunkan.manager_house.model.Account;

@Service
@Transactional
public class AccountService {
	
	BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	
	@Autowired
	private MapperAccount mapperAccount;
	
	public Account createAccount(Account account) {
		account.setPassword(encoder.encode(account.getPassword()));
		account.setDel_flg(0);
		if(account.getRole() == null) {
			account.setRole("member");
		}		
		if(mapperAccount.insert(account) > 0) {
			return account;
		}
		return null;
	}
	
	public Account updateAccount(Account account) {
		account.setUpdate_date(LocalDateTime.now());
		int i = mapperAccount.update(account);
		if(i > 0) {
			return account;
		}
		return null;
	}
	
}
