package com.shaishunkan.manager_house.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.shaishunkan.manager_house.model.Room;

@Repository
public interface MapperRoom {
	
	public int insert(Room room);
	
	public int update(Room room);
	
	public Room getById(int id);
	
	public List<Room> getAllRoom(Room room);
	
// get total
	public int getTotalByName(@Param("name") String name, @Param("id") Integer id );	
	public int getTotalByNumber(@Param("numerical_order") Integer numerical_order, @Param("id") Integer id);
}
