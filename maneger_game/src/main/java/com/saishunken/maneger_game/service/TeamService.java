package com.saishunken.maneger_game.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.saishunken.maneger_game.mapper.MapperTeam;
import com.saishunken.maneger_game.model.Team;

@Service
@Transactional
public class TeamService {
	
	@Autowired
	private MapperTeam mapperTeam;
	
	// create team
	public void createTeam (Team team) {
		if(team.getDel_flg() == null) {
			team.setDel_flg(0);
		}
		if(team.getId_tournament() == null) {
			team.setId_tournament(0);
		}
		if(team.getPoints() == null) {
			team.setPoints(0);
		}
		if(team.getLink_logo() == null) {
			team.setLink_logo(null);
		}
		mapperTeam.insert(team);
	}
	
	
	//get all
	public List<Team> getAll(Team team){
		return mapperTeam.getAll(team);
	}
	
	public int getTotalTeam(Team team) { 	
		return mapperTeam.getTotalTeam(team).intValue();
	}
	
	//get by tournament
	public List<Team> getAllByTournament(Team team){
		return mapperTeam.getAllByTournament(team);
	}
	
	public int getTotalByTournament(Team team) { 	
		return mapperTeam.getTotalByTournament(team).intValue();
	}
	
	//by id tournament
//	public List<Team> getAllByIdTournament(Team team){
//		return mapperTeam.getAllByIdTournament(team);
//	}
	
	public int getTotalByIdTournament(int id) {
		return mapperTeam.getTotalByIdTournament(id).intValue();
	}
	
	//by not id tournament
	public List<Team> getAllByNotIdTournament(Team team){
		return mapperTeam.getAllByNotIdTournament(team);
	}
	
	public int getTotalByNotIdTournament(Team team) { 	
		return mapperTeam.getTotalByNotIdTournament(team).intValue();
	}

}
