package com.saishunken.maneger_game.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.saishunken.maneger_game.mapper.MapperMember;
import com.saishunken.maneger_game.mapper.MapperTeam;
import com.saishunken.maneger_game.model.Member;
import com.saishunken.maneger_game.model.Team;

@Service
@Transactional
public class MemberService {
	
	@Autowired
	private MapperMember mapperMember;
	
	@Autowired
	private MapperTeam mapperTeam;
	
	//get list member in tournament
	public List<Member> getListMemberTNM(Integer id_tnm){
		Team team = new Team();
		Member member = new Member();
		List<Member> listMemberTNm  = new ArrayList<Member>();
		team.setId_tournament(id_tnm);
		List<Team> listTeam = mapperTeam.getAllByTournament(team);
		listTeam.forEach(teamInTNM -> {
			member.setId_team(teamInTNM.getId());
			listMemberTNm.addAll(mapperMember.getByTeam(member));
		});
        Comparator<Member> scoredComparator = new Comparator<Member>() {
            @Override
            public int compare(Member s1, Member s2) {
                return s2.getScored().compareTo(s1.getScored());
            }
        };
        Collections.sort(listMemberTNm, scoredComparator);
		return listMemberTNm;
	}
	
	//get member point in tournamen
	public List<Member> getListMemberPointTNM(Integer id_tnm){
		Team team = new Team();
		List<Member> listMemberTNm  = new ArrayList<Member>();
		team.setId_tournament(id_tnm);
		List<Team> listTeam = mapperTeam.getAllByTournament(team);
		listTeam.forEach(teamInTNM -> {
			listMemberTNm.addAll(mapperMember.getMemberAndPointByTeam(teamInTNM.getId()));
		});
		listMemberTNm.forEach(memberPoint -> {
			memberPoint.setScored(0);
			memberPoint.getListpoint().forEach(point -> {
				memberPoint.setScored(memberPoint.getScored() + point.getScored());
			});
		});
        Comparator<Member> scoredComparator = new Comparator<Member>() {
            @Override
            public int compare(Member s1, Member s2) {
                return s2.getScored().compareTo(s1.getScored());
            }
        };
        Collections.sort(listMemberTNm, scoredComparator);
		return listMemberTNm;
	}
	
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
	
	//update scored
	public void updateScored(Member member) {
	  Member memberDB =	mapperMember.getById(member.getId());
	  if(memberDB.getScored() != null) {
		  Integer scored = memberDB.getScored() + member.getScored();
		  member.setScored(scored);
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
