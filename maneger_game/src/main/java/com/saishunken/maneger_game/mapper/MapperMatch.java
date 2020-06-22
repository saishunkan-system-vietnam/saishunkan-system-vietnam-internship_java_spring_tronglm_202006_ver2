package com.saishunken.maneger_game.mapper;

import org.springframework.stereotype.Repository;

import com.saishunken.maneger_game.model.Match;

@Repository
public interface MapperMatch {

	public void insert(Match match);
	
	public void update(Match match);
	
	public Match getById(int id);
}
