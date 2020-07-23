package com.shaishunkan.manager_house.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shaishunkan.manager_house.mapper.MapperRoom;
import com.shaishunkan.manager_house.model.Contract;
import com.shaishunkan.manager_house.model.Response;
import com.shaishunkan.manager_house.model.Room;
import com.shaishunkan.manager_house.service.ContractService;

@RestController
@RequestMapping("/")
public class ContractController {
	
	@Autowired
	private ContractService contractService;
	
	@Autowired
	private MapperRoom mapperRoom;
	
	
	@PostMapping("admin/create-contract")
	public ResponseEntity<Response> createMatch(@RequestBody Contract contract){
		Contract ctr = contractService.createrContract(contract);
		if(ctr != null) {
			Room room = new Room();
			room.setId(contract.getId_room_2());
			room.setStatus_flg(1);
			int temp = mapperRoom.update(room);
			if(temp > 0) {
				return ResponseEntity.ok().body(new Response("0000", "Create successfully!", ctr));
			}		
		}
		return ResponseEntity.ok().body(new Response("0001", "Fail!", null));
	}
	
}
