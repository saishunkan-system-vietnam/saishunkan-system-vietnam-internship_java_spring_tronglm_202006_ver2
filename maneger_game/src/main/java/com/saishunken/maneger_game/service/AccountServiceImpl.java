package com.saishunken.maneger_game.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.saishunken.maneger_game.mapper.MapperAccount;
import com.saishunken.maneger_game.model.Account;

@Service
public class AccountServiceImpl implements UserDetailsService{
	
	@Autowired
	private MapperAccount mapperAccount;
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Account account = mapperAccount.getByName(username);
		
		if (account == null) {
			throw new UsernameNotFoundException("User " + account + " was not found in the database");
		}

		GrantedAuthority authority = new SimpleGrantedAuthority(account.getRole_name());

		List<GrantedAuthority> grants = new ArrayList<GrantedAuthority>();

		grants.add(authority);

		account.setRoles(grants);

		return account;	
	}
}
