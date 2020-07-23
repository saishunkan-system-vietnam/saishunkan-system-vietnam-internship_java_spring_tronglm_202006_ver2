package com.shaishunkan.manager_house.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shaishunkan.manager_house.mapper.MapperRoom;
import com.shaishunkan.manager_house.model.Response;
import com.shaishunkan.manager_house.model.Room;
import com.shaishunkan.manager_house.service.RoomService;

@RestController
@RequestMapping("/")
public class RoomController {

	@Autowired
	private RoomService roomService;
	
	@Autowired
	private MapperRoom mapperRoom;
	
	@PostMapping("admin/create-room")
	public ResponseEntity<Response> createRoom(@RequestBody Room room){
		Integer id = null;
		Room roomTest = new Room();
		int countName = mapperRoom.getTotalByName(room.getName(), id);
		int countNumber = mapperRoom.getTotalByNumber(room.getNumerical_order(), id);
		if(countName > 0 || countNumber > 0) {
			if(countName > 0) {
				roomTest.setName(room.getName());
			}
			if(countNumber > 0) {
				roomTest.setNumerical_order(room.getNumerical_order());
			}
			return ResponseEntity.ok().body(new Response("0003", "Invalid data!", roomTest));
		}
		roomTest = roomService.createRoom(room);
		if(roomTest != null) {
			return ResponseEntity.ok().body(new Response("0000", "Create Room Successfully!", roomTest));
		}
		return ResponseEntity.ok().body(new Response("0001", "Fail!", null));
	}
	
	@PostMapping("admin/update-room")
	public ResponseEntity<Response> updateRoom(@RequestBody Room room){
		Room roomTest = new Room();
		int countName = mapperRoom.getTotalByName(room.getName(), room.getId());
		int countNumber = mapperRoom.getTotalByNumber(room.getNumerical_order(), room.getId());
		if(countName > 0 || countNumber > 0) {
			if(countName > 0) {
				roomTest.setName(room.getName());
			}
			if(countNumber > 0) {
				roomTest.setNumerical_order(room.getNumerical_order());
			}
			return ResponseEntity.ok().body(new Response("0003", "Invalid data!", roomTest));
		}
		int i = mapperRoom.update(room);
		if(i > 0) {
			return ResponseEntity.ok().body(new Response("0000", "Update Room Successfully!", null));
		}
		return ResponseEntity.ok().body(new Response("0001", "Fail!", null));
	}
	
	@GetMapping("admin/get-id-room")
	public ResponseEntity<Response> getById(@RequestParam int id){
		Room room = mapperRoom.getById(id);
		if(room != null) {
			return ResponseEntity.ok().body(new Response("0000", "Get Data Successfully!", room));
		}
		return ResponseEntity.ok().body(new Response("0003", "Data Not Found!", null));
	}
	
	@GetMapping("admin/get-all-room")
	public ResponseEntity<Response> getAll(@RequestParam Optional<String> name, @RequestParam int id_home){
		Room room = new Room();
		room.setId_home(id_home);
		room.setName(name.filter(x -> x.length() >= 1).map(Object::toString).orElse(""));
		return ResponseEntity.ok().body(new Response("0000", "Get Data Successfully!", mapperRoom.getAllRoom(room)));
	}
}
