package com.shaishunkan.manager_house.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shaishunkan.manager_house.mapper.MapperAccount;
import com.shaishunkan.manager_house.mapper.MapperContract;
import com.shaishunkan.manager_house.mapper.MapperCustomer;
import com.shaishunkan.manager_house.mapper.MapperServiceContract;
import com.shaishunkan.manager_house.model.Account;
import com.shaishunkan.manager_house.model.Contract;

@Service
@Transactional
public class ContractService {
	
	BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

	@Autowired
	private MapperContract mapperContract;

	@Autowired
	private MapperCustomer mapperCustomer;

	@Autowired
	private MapperServiceContract mapperServiceContract;

	@Autowired
	private MapperAccount mapperAccount;

	public Contract createrContract(Contract contract) {
		contract.setDel_flg(0);
		contract.setStatus_flg(0);
		int temp1 = mapperContract.insert(contract);
		if (temp1 > 0) {
			contract.getListCustomer().forEach(customer -> {
				customer.setDel_flg(0);
				customer.setStatus_flg(0);
				customer.setId_contract(contract.getId());
				int temp2 = mapperCustomer.insert(customer);				
				if (temp2 > 0) {
					Account account = new Account();
						account.setDel_flg(0);
						account.setStatus_flg(0);
						account.setMail(customer.getMail());
						account.setPassword(encoder.encode("123456"));					
						account.setId_customer(customer.getId());
						account.setRole("member");
						mapperAccount.insert(account);	
						System.out.println(customer);
				}						
			});
			contract.getListServiceContract().forEach(serviceContract -> {
				serviceContract.setDel_flg(0);
				serviceContract.setId_contract(contract.getId());
				mapperServiceContract.insert(serviceContract);
			});
		} else {
			return null;
		}
		return contract;
	}
}
