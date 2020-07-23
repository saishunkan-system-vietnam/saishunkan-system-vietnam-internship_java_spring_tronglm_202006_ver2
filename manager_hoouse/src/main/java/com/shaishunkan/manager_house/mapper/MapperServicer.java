package com.shaishunkan.manager_house.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.shaishunkan.manager_house.model.Servicer;

@Repository
public interface MapperServicer {
	
	public int insert(Servicer service);
	
	public int update(Servicer service);
	
	public Servicer getById(int id);
	
	public List<Servicer> getAllServiceByHome(Servicer service);
	
	public int getTotalByName(@Param("name") String name, @Param("id") Integer id );
	
}
