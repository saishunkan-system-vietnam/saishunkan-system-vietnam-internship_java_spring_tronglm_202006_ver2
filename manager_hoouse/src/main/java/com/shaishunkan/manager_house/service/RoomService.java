package com.shaishunkan.manager_house.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shaishunkan.manager_house.mapper.MapperRoom;
import com.shaishunkan.manager_house.model.Room;

@Service
@Transactional
public class RoomService {
	
	@Autowired
	private MapperRoom mapperRoom;
	
	public Room createRoom(Room room) {
		room.setStatus_flg(0);
		room.setDel_flg(0);
		if(room.getUnit_of_time() == null) {
			room.setUnit_of_time(1);
		}
		int i = mapperRoom.insert(room);
		if(i > 0) {
			return room;
		}
		return null;
	}
}
