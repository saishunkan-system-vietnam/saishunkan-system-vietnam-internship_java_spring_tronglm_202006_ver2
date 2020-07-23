package com.shaishunkan.manager_house.mapper;

import org.springframework.stereotype.Repository;

import com.shaishunkan.manager_house.model.Contract;

@Repository
public interface MapperContract {
	
	public int insert(Contract contract);
	
	public int update(Contract contract);
	
	public Contract getById(int id);
	
	public Contract getComtractByRoom(Integer id_room_2);
}
