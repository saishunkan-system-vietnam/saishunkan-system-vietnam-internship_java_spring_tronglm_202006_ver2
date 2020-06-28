package com.saishunken.maneger_game.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.saishunken.maneger_game.mapper.MapperTeam;
import com.saishunken.maneger_game.mapper.MapperTournament;
import com.saishunken.maneger_game.model.Tournament;

@Service
@Transactional
public class TournamentService {
	
	
	@Autowired
	private MapperTournament mapperTournament;
	
	@Autowired
	private MapperTeam mapperTeam;
	
	
	//add team in tournament
	
	public void addTeamInTnm(Tournament tournament) {
		tournament.getListTeam().forEach(team -> {
			team.setId_tournament(tournament.getId());
			mapperTeam.update(team);
		});
	}
	
	//create tournament
	public void createTournament (Tournament tournament) {
		tournament.setDel_flg(0);
		if(tournament.getStatus_flg() == null) {
			tournament.setStatus_flg(0);
		}
		if(tournament.getWin_point() == null) {
			tournament.setWin_point(0);;
		}
		if(tournament.getLose_point() == null) {
			tournament.setLose_point(0);
		}
		if(tournament.getEqual_point() == null) {
			tournament.setEqual_point(0);
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
