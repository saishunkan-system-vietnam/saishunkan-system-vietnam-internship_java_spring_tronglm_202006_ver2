package com.shaishunkan.manager_house.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.shaishunkan.manager_house.model.Home;

@Repository
public interface MapperHome {
	
	public int insert(Home home);
	
	public int update(Home home);
	
	public List<Home> getAllHome(String name);

}
