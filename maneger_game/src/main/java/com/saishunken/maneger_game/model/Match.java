package com.saishunken.maneger_game.model;

import java.time.LocalDateTime;
import java.util.List;

public class Match extends AbstractModel{
	
	private static final long serialVersionUID = 1L;
	
	private LocalDateTime start_time;
	
	private String str_start_time;
	
	private LocalDateTime end_time;
	
	private Integer status_flg;
	
	private String place_match;
	
	private List<PointMemberMatch> listPointMemberMatch;
	
	private List<Member> listMember;
		
	private List<Detail_match> listDetailMatch;
	
	private List<Team> teams;
	
	
	
	public List<PointMemberMatch> getListPointMemberMatch() {
		return listPointMemberMatch;
	}

	public void setListPointMemberMatch(List<PointMemberMatch> listPointMemberMatch) {
		this.listPointMemberMatch = listPointMemberMatch;
	}

	public List<Team> getTeams() {
		return teams;
	}
			
	public List<Member> getListMember() {
		return listMember;
	}

	public void setListMember(List<Member> listMember) {
		this.listMember = listMember;
	}

	public void setTeams(List<Team> teams) {
		this.teams = teams;
	}
	public String getStr_start_time() {
		return str_start_time;
	}
	public void setStr_start_time(String str_start_time) {
		this.str_start_time = str_start_time;
	}
	public List<Detail_match> getListDetailMatch() {
		return listDetailMatch;
	}
	public void setListDetailMatch(List<Detail_match> listDetailMatch) {
		this.listDetailMatch = listDetailMatch;
	}
	
	public LocalDateTime getStart_time() {
		return start_time;
	}
	public void setStart_time(LocalDateTime start_time) {
		this.start_time = start_time;
	}
	public LocalDateTime getEnd_time() {
		return end_time;
	}
	public void setEnd_time(LocalDateTime end_time) {
		this.end_time = end_time;
	}
	public Integer getStatus_flg() {
		return status_flg;
	}
	public void setStatus_flg(Integer status_flg) {
		this.status_flg = status_flg;
	}
	public String getPlace_match() {
		return place_match;
	}
	public void setPlace_match(String place_match) {
		this.place_match = place_match;
	}
	
	
}
