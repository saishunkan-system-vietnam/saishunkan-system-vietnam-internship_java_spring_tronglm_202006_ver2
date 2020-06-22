package com.saishunken.maneger_game.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.saishunken.maneger_game.mapper.MapperDetail_match;
import com.saishunken.maneger_game.mapper.MapperMatch;
import com.saishunken.maneger_game.model.Match;

@Service
@Transactional
public class MatchService {
	
	@Autowired
	private MapperMatch mapperMatch;
	
	@Autowired
	private MapperDetail_match mapperDetail_match;
	
	public void addMatch(Match match) {
		match.setDel_flg(0);
		match.setEnd_time(LocalDateTime.now());
		match.setStart_time(LocalDateTime.now());
		match.setStatus_flg(0);
		mapperMatch.insert(match);
	}
}
