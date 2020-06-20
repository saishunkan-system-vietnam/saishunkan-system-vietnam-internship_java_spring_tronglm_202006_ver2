package com.saishunken.maneger_game.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.saishunken.maneger_game.mapper.MapperAccount;
import com.saishunken.maneger_game.model.Account;

@Service
@Transactional
public class AccountService {
	
	@Autowired
	private MapperAccount mapperAccount;
	
	public List<Account> getAll(Account account){
		return mapperAccount.getAll(account);
	}
	
	public int getTotal(Account account) { 	
		return mapperAccount.getTotalUser(account).intValue();
	}
	
	public void create(Account account) {
		
		if(account.getDel_flg() == null) {
			account.setDel_flg(0);
		}
		if(account.getRole_name() == null) {
			account.setRole_name("user");
		}
		mapperAccount.insert(account);
	}
	
	public void update(Account account) {
		account.setEdit_date(LocalDateTime.now());
		if(account.getDel_flg() == null) {
			account.setDel_flg(0);
		}
		if(account.getRole_name() == null) {
			account.setRole_name("user");
		}
		mapperAccount.update(account);
	}
	
	
	
}
