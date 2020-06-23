package com.saishunken.maneger_game.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.saishunken.maneger_game.mapper.MapperTournament;
import com.saishunken.maneger_game.model.Tournament;

@Service
@Transactional
public class TournamentService {
	
	
	@Autowired
	private MapperTournament mapperTournament;
	
	//create tournament
	public void createTournament (Tournament tournament) {
		tournament.setDel_flg(0);
		if(tournament.getStatus_flg() == null) {
			tournament.setStatus_flg(0);
		}
		mapperTournament.insert(tournament);
	}
	
	//get all
	public List<Tournament> getAll(Tournament tournament){
		return mapperTournament.getAll(tournament);		
	}
	
	public int getTotalTournament(Tournament tournament) { 	
		return mapperTournament.getTotalTournament(tournament).intValue();
	}
	
	//by status
	public List<Tournament> getAllByStatus(Tournament tournament){
		return mapperTournament.getAllByStatus(tournament);		
	}
	
	public int getTotalByStatus(Tournament tournament) { 	
		return mapperTournament.getTotalByStatus(tournament).intValue();
	}
}
