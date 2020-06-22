package com.saishunken.maneger_game.mapper;

import java.math.BigInteger;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.saishunken.maneger_game.model.Tournament;

@Repository
public interface MapperTournament {
	
	public void insert(Tournament tournament);
	
	public void update(Tournament tournament);
	
	public Tournament getById(int id);
	
	//all
	public List<Tournament> getAll(Tournament tournament);
	
	public BigInteger getTotalTournament(Tournament tournament);
	
	//by status
	public List<Tournament> getAllByStatus(Tournament tournament);
	
	public BigInteger getTotalByStatus(Tournament tournament);
	
	public int getTotalByNameTournament(@Param("name_tnm") String name_tnm, @Param("id") Integer id );
}

