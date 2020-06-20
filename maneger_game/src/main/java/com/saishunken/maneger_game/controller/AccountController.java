package com.saishunken.maneger_game.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.saishunken.maneger_game.mapper.MapperAccount;
import com.saishunken.maneger_game.model.Account;
import com.saishunken.maneger_game.model.Response;
import com.saishunken.maneger_game.service.AccountService;


@RestController
@RequestMapping("/")
public class AccountController {
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private MapperAccount mapperAccount;
	
	BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	
	
	@GetMapping("admin/getall")
	public ResponseEntity<Response> getAllUser(@RequestParam Optional<Integer> page,
			@RequestParam Optional<String> nameAccount) {
		Response response = new Response();
		Account account = new Account();
		account.setRole_name("user");
		if (page.isPresent()) {
			account.setPage(page.get());
		} else {
			account.setPage(1);
		}
		account.setName_account(nameAccount.filter(x -> x.length() >= 1).map(Object::toString).orElse(""));
		account.setOffset((account.getPage() - 1) * account.getMaxPageItem());
		response.setTotalItem(accountService.getTotal(account));
		response.setCode("0000");
		response.setMessage("Get data successfully");
		response.setPayload(accountService.getAll(account));
		return ResponseEntity.ok().body(response);
	}
	
	@GetMapping("account/getbyid")
	public ResponseEntity<Response> getUserById(@RequestParam int id) {
		Response response = new Response();
		Account account = mapperAccount.getById(id);
		if (account != null) {
			response.setCode("0000");
			response.setMessage("Get data successfully");
			response.setPayload(account);
			return ResponseEntity.ok().body(response);
		}	
		return ResponseEntity.ok().body(new Response("0003", "data not found", 0, null));
	}
	
	@GetMapping("account/getsession")
	public ResponseEntity<Response> getSession() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();		
	    return ResponseEntity.ok().body(new Response("0000", "Get data successfully", 0, auth.getPrincipal()));
	}
	
	@GetMapping("/isname")
	public ResponseEntity<Response> isTotalName(@RequestParam String name, @RequestParam Optional<Integer> id) {
		Integer userID;
		if(id.isPresent()) {
			userID  = id.get();
		} else {
			userID = null;
		}		
		if(mapperAccount.getTotalByName(name ,userID) > 0) {
			return ResponseEntity.ok().body(new Response("0004", "Name exist", 0, null));
		}		
	    return ResponseEntity.ok().body(new Response("0000", "oke", 0, null));
	}
	
	@GetMapping("/isphone")
	public ResponseEntity<Response> isTotalPhone(@RequestParam String phone,  @RequestParam Optional<Integer> id) {	
		Integer userID;
		if(id.isPresent()) {
			userID  = id.get();
		} else {
			userID = null;
		}
		if(mapperAccount.getTotalByPhone(phone, userID) > 0) {
			return ResponseEntity.ok().body(new Response("0005", "Phone exist", 0, null));
		}		
	    return ResponseEntity.ok().body(new Response("0000", "oke", 0, null));
	}
	
	@GetMapping("/ismail")
	public ResponseEntity<Response> isTotalMail(@RequestParam String mail,  @RequestParam Optional<Integer> id) {
		Integer userID;
		if(id.isPresent()) {
			userID  = id.get();
		} else {
			userID = null;
		}
		if(mapperAccount.getTotalByMail(mail, userID) > 0) {
			return ResponseEntity.ok().body(new Response("0006", "Mail exist", 0, null));
		}		
	    return ResponseEntity.ok().body(new Response("0000", "oke", 0, null));
	}

	@PostMapping("/register")
	public ResponseEntity<Response> createUser(@RequestBody Account account) {
		Integer id = null;
		account.setPassword(encoder.encode(account.getPassword()));
		if(mapperAccount.getTotalByName(account.getName_account(), id) > 0 || mapperAccount.getTotalByMail(account.getMail(), id) > 0 || mapperAccount.getTotalByPhone(account.getPhone_number(), id) > 0) {
			return ResponseEntity.ok().body(new Response("0007", "Create account failed", 0, null));
			
		}else {
			accountService.create(account);
			return ResponseEntity.ok().body(new Response("0000", "Create account successfully", 0, null));
		}
	}
	
	@PostMapping("/updateAccount")
	public ResponseEntity<Response> update(@RequestBody Account account) {
		if (account.getPassword() != null) {
			account.setPassword(encoder.encode(account.getPassword()));
		}
		Integer id = account.getId();
		if(mapperAccount.getTotalByMail(account.getMail(), id) > 0 || mapperAccount.getTotalByPhone(account.getPhone_number(), id) > 0) {
			return ResponseEntity.ok().body(new Response("0007", "Update account failed", 0, null));
		}
		accountService.update(account);
		return ResponseEntity.ok().body(new Response("0000", "Update successfully", 0, null));				
	}
}
