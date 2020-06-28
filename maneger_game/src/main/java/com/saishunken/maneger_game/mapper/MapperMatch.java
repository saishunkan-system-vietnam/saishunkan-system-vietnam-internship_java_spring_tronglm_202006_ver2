package com.saishunken.maneger_game.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.saishunken.maneger_game.model.Match;

@Repository
public interface MapperMatch {

	public int insert(Match match);
	
	public void update(Match match);
	
	public Match getById(int id);
	
	public List<Object> getAllMatch();
}