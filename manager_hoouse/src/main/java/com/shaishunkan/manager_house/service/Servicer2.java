package com.shaishunkan.manager_house.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shaishunkan.manager_house.mapper.MapperServicer;

@Service
@Transactional
public class Servicer2 {
	
	@Autowired
	private MapperServicer mapperService;
}
