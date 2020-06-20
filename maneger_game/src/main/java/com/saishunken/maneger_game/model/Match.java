package com.saishunken.maneger_game.model;

import java.time.LocalDate;

public class Match extends AbstractModel{
	
	private static final long serialVersionUID = 1L;
	
	private LocalDate match_day;
	private Integer status_lfg;
	public LocalDate getMatch_day() {
		return match_day;
	}
	public void setMatch_day(LocalDate match_day) {
		this.match_day = match_day;
	}
	public Integer getStatus_lfg() {
		return status_lfg;
	}
	public void setStatus_lfg(Integer status_lfg) {
		this.status_lfg = status_lfg;
	}
	
	
	
	
}
