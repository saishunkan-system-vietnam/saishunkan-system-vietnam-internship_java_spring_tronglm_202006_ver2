package com.saishunken.maneger_game.mapper;

import java.math.BigInteger;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.saishunken.maneger_game.model.Member;

@Repository
public interface MapperMember {
	
	public void insert(Member member);
	
	
	//all
	public List<Member> getAll (Member member);
	
	public BigInteger getTotalMember(Member member);
	
	//team
	public List<Member> getByTeam(Member member);
	
	public BigInteger getTotalByTeam(Member member);
	
	//id team
	public List<Member> getByIdTeam(Member member);
	
	public BigInteger getTotalByIdTeam(Member member);
	
	// not id team
	public List<Member> getByNotIdTeam(Member member);
	
	public BigInteger getTotalByNotIdTeam(Member member);
	
	public Member getById(int id);
	
	public void update(Member member);
	
	public void updateCap(Member member);
	
	//total nickname
	public int getTotalByNickName(@Param("nickname") String nickname, @Param("id") Integer id );

}
