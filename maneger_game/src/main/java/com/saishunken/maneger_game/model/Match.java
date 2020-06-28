package com.saishunken.maneger_game.model;

import java.time.LocalDateTime;
import java.util.List;

public class Match extends AbstractModel{
	
	private static final long serialVersionUID = 1L;
	
	private LocalDateTime start_time;
	private LocalDateTime end_time;
	private Integer status_flg;
	private String place_match;
	
	private List<Detail_match> listDetailMatch;
	
	private Team teams;
	
	

	public Team getTeams() {
		return teams;
	}
	public void setTeams(Team teams) {
		this.teams = teams;
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
