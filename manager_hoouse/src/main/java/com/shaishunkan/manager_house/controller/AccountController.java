package com.shaishunkan.manager_house.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shaishunkan.manager_house.mapper.MapperAccount;
import com.shaishunkan.manager_house.model.Account;
import com.shaishunkan.manager_house.model.Response;
import com.shaishunkan.manager_house.model.Room;
import com.shaishunkan.manager_house.service.AccountService;


@RestController
@RequestMapping("/")
public class AccountController {
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private MapperAccount mapperAccount;
	
	
	@PostMapping("admin/create-account")
	public ResponseEntity<Response> createUser(@RequestBody Account account){
		Account acc = accountService.createAccount(account);
		if(acc != null) {
			return ResponseEntity.ok().body(new Response("0000", "Create Account Successfully!", acc));
		}
		return ResponseEntity.ok().body(new Response("0001", "Fail!", null));
	}
	
	@GetMapping("account/getsession")
	public ResponseEntity<Response> getSession() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return ResponseEntity.ok().body(new Response("0000", "Get data successfully", auth.getPrincipal()));
	}
	
	@PostMapping("update-account")
	public ResponseEntity<Response> updateUser(@RequestBody Account account){
		Account acc = accountService.updateAccount(account);
		if(acc != null) {
			return ResponseEntity.ok().body(new Response("0000", "Update Account Successfully!", account));
		}
		return ResponseEntity.ok().body(new Response("0001", "Fail!", null));
	}
	
	@GetMapping("admin/get-all-account")
	public ResponseEntity<Response> getAllAccount(@RequestParam Optional<String> mail) {
		Account acc = new Account();
		acc.setMail(mail.filter(x -> x.length() >= 1).map(Object::toString).orElse(""));
		acc.setRole("admin");
		return ResponseEntity.ok().body(new Response("0000", "Get data successfully", mapperAccount.getAllAccount(acc)));	
	}
	
	@GetMapping("admin/get-id-account")
	public ResponseEntity<Response> getById(@RequestParam int id){
		Account acc = mapperAccount.getById(id);
		if(acc != null) {
			return ResponseEntity.ok().body(new Response("0000", "Get Data Successfully!", acc));
		}
		return ResponseEntity.ok().body(new Response("0003", "Data Not Found!", null));
	}
}
