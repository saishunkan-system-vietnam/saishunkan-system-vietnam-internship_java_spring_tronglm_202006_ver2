package com.saishunken.maneger_game.mapper;

import java.math.BigInteger;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.saishunken.maneger_game.model.Team;

@Repository
public interface MapperTeam {
	
	public void insert(Team team);
	
	public void update(Team team);
	
	public Team getById(int id);
	
	public int getTotalByNameTeam(@Param("name_team") String name_team, @Param("id") Integer id );
	
	//all
	public List<Team> getAll (Team team);
	
	public BigInteger getTotalTeam(Team team);
	
	//by tournament
	public List<Team> getAllByTournament (Team team);
	
	public BigInteger getTotalByTournament(Team team);
	
	//by id tournament
	public List<Team> getAllByIdTournament (Team team);
	
	public BigInteger getTotalByIdTournament(Team team);
	
	//by not id tournament
	public List<Team> getAllByNotIdTournament (Team team);
	
	public BigInteger getTotalByNotIdTournament(Team team);
	
	
	
}