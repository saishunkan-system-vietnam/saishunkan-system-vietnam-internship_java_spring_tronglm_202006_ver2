package com.saishunken.maneger_game.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.saishunken.maneger_game.mapper.MapperMember;
import com.saishunken.maneger_game.model.Member;

@Service
@Transactional
public class MemberService {
	
	@Autowired
	private MapperMember mapperMember;	
	
	// create member
	public void createMember (Member member) {
		if(member.getDel_flg() == null) {
			member.setDel_flg(0);
		}
		
		if(member.getCap_flg() == null) {
			member.setCap_flg(0);
		}
		
		if(member.getCap_flg() == 1) {
			mapperMember.updateCap(member);
		}
			
		if(member.getId_team() == null) {
			member.setId_team(0);
		}
		if(member.getNickname() == null) {
			member.setNickname("");
		}
		mapperMember.insert(member);
	}
	
	//update
	public void update(Member member) {
		if(member.getCap_flg() == null) {
			member.setCap_flg(0);
		}
		
		if(member.getCap_flg() == 1) {
			mapperMember.updateCap(member);
		}
		mapperMember.update(member);
	}
	
	//all
	public List<Member> getAll(Member member){
		return mapperMember.getAll(member);
	}
	
	public int getTotalMember(Member member) { 	
		return mapperMember.getTotalMember(member).intValue();
	}
	
	//team
	public List<Member> getByTeam(Member member){
		return mapperMember.getByTeam(member);
	}
	
	public int getTotalByTeam(Member member) { 	
		return mapperMember.getTotalByTeam(member).intValue();
	}
	
	//id team
	public List<Member> getByIdTeam(Member member){
		return mapperMember.getByIdTeam(member);
	}
	
	public int getTotalByIdTeam(Member member) { 	
		return mapperMember.getTotalByIdTeam(member).intValue();
	}
	// not id team
	public List<Member> getByNotIdTeam(Member member){
		return mapperMember.getByNotIdTeam(member);
	}
	
	public int getTotalByNotIdTeam(Member member) { 	
		return mapperMember.getTotalByNotIdTeam(member).intValue();
	}
}
