package com.shaishunkan.manager_house.mapper;

import org.springframework.stereotype.Repository;

import com.shaishunkan.manager_house.model.ServiceContract;

@Repository
public interface MapperServiceContract {
	
	public int insert(ServiceContract serviceContract);
	
	public int update(ServiceContract serviceContract);

}
