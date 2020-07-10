package com.saishunken.maneger_game.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.saishunken.maneger_game.model.PointMemberMatch;

@Repository
public interface MapperPointMemberMatch {
	
	public void insert(PointMemberMatch pointMembermatch);
	
	public void update(PointMemberMatch pointMembermatch);
	
	public PointMemberMatch getById(int id);
	
	public List<PointMemberMatch> getByIdMatch(int id);
	
	public void deleteByIdMatch(int id_match);
	
	public PointMemberMatch getByIdMM(@Param("id_member") int id_member, @Param("id_match") int id_match);
}
