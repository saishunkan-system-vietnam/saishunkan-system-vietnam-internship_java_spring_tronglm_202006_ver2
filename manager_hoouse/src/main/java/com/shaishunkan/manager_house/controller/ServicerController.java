package com.shaishunkan.manager_house.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shaishunkan.manager_house.mapper.MapperServicer;
import com.shaishunkan.manager_house.model.Response;
import com.shaishunkan.manager_house.model.Servicer;

@RestController
@RequestMapping("/")
public class ServicerController {
	
	@Autowired
	private MapperServicer mapperServicer;
	
	
//	@PostMapping("admin/create-service")
//	public ResponseEntity<Response> createRoom(@RequestBody Servicer service){
//		Integer id = null;
//		Servicer serv = new Servicer();
//		int countName = mapperServicer.getTotalByName(service.getName(), id);
//			if(countName > 0) {
//				serv.setName(service.getName());
//				return ResponseEntity.ok().body(new Response("0003", "Invalid data!", serv));
//			}						
//		serv = roomService.createRoom(room);
//		if(roomTest != null) {
//			return ResponseEntity.ok().body(new Response("0000", "Create Room Successfully!", roomTest));
//		}
//		return ResponseEntity.ok().body(new Response("0001", "Fail!", null));
//	}
	
	@GetMapping("admin/get-all-service-by-id_home")
	public ResponseEntity<Response> getAllServiceByIdHome(@RequestParam Optional<String> name, @RequestParam int id_home_2){
		Servicer service = new Servicer();
		service.setId_home_2(id_home_2);
		service.setName(name.filter(x -> x.length() >= 1).map(Object::toString).orElse(""));
		return ResponseEntity.ok().body(new Response("0000", "Get Data Successfully!", mapperServicer.getAllServiceByHome(service)));
	}
}
