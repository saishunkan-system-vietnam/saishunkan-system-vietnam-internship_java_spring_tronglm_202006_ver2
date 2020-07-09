package com.saishunken.maneger_game.service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.saishunken.maneger_game.mapper.MapperTeam;
import com.saishunken.maneger_game.mapper.MapperTournament;
import com.saishunken.maneger_game.model.Team;
import com.saishunken.maneger_game.model.Tournament;

@Service
@Transactional
public class TeamService {
	
	@Autowired
	private MapperTeam mapperTeam;
	
	@Autowired
	private MapperTournament mapperTournament;
	
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
	
	//get points Team
	public Team getPointsTeam(Team team) {
		Integer winPoint = mapperTeam.getTotalPointWin(team.getId());
		Integer lostPoint = mapperTeam.getTotalPointlost(team.getId());
		Integer equalPoint = mapperTeam.getTotalPointequal(team.getId());
		team.setTotalWin(winPoint);
		team.setTotalLose(lostPoint);
		team.setTotalEqual(equalPoint);		
		Tournament tournament = mapperTournament.getById(team.getId_tournament());
		if(tournament != null) {
			Integer totalPoint = tournament.getWin_point() * winPoint + tournament.getLose_point() * lostPoint + tournament.getEqual_point() * equalPoint;
			team.setPoints(totalPoint);
			return team;
		}
		return null;
	}
	
	//get list rank team
	public List<Team> getListRankTeam(Team team) {
		List<Team> list = mapperTeam.getAllByTournament(team);
		list.forEach(teamTnm -> {
			getPointsTeam(teamTnm);

		});			
        Comparator<Team> pointComparator1 = new Comparator<Team>() {
            @Override
            public int compare(Team s1, Team s2) {
                return s2.getPoints().compareTo(s1.getPoints());
            }
        };
        Collections.sort(list, pointComparator1);
		return list;
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
