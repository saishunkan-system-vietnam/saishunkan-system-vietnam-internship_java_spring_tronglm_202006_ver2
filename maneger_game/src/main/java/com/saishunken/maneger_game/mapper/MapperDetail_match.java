package com.saishunken.maneger_game.mapper;

import org.springframework.stereotype.Repository;

import com.saishunken.maneger_game.model.Detail_match;

@Repository
public interface MapperDetail_match {

	public int insert(Detail_match detail_match);
	
	public void update(Detail_match detail_match);
	
	public Detail_match getById(Detail_match detail_match);
	
}
