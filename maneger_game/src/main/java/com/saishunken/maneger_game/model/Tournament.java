package com.saishunken.maneger_game.model;

import java.time.LocalDate;
import java.util.List;

public class Tournament  extends AbstractModel{
	
	private static final long serialVersionUID = 1L;
	
	private String name_tnm;	
	private String info_tnm;	
	private Integer status_flg;	
	private LocalDate start_date;
	private LocalDate end_date;
	private Integer win_point;
	private Integer equal_point;
	private Integer lose_point;
	
	private List<Team> listTeam;
	
	public List<Team> getListTeam() {
		return listTeam;
	}
	public void setListTeam(List<Team> listTeam) {
		this.listTeam = listTeam;
	}
	public Integer getWin_point() {
		return win_point;
	}
	public void setWin_point(Integer win_point) {
		this.win_point = win_point;
	}
	public Integer getEqual_point() {
		return equal_point;
	}
	public void setEqual_point(Integer equal_point) {
		this.equal_point = equal_point;
	}
	public Integer getLose_point() {
		return lose_point;
	}
	public void setLose_point(Integer lose_point) {
		this.lose_point = lose_point;
	}
	public String getName_tnm() {
		return name_tnm;
	}
	public void setName_tnm(String name_tnm) {
		this.name_tnm = name_tnm;
	}
	public String getInfo_tnm() {
		return info_tnm;
	}
	public void setInfo_tnm(String info_tnm) {
		this.info_tnm = info_tnm;
	}
	public Integer getStatus_flg() {
		return status_flg;
	}
	public void setStatus_flg(Integer status_flg) {
		this.status_flg = status_flg;
	}
	public LocalDate getStart_date() {
		return start_date;
	}
	public void setStart_date(LocalDate start_date) {
		this.start_date = start_date;
	}
	public LocalDate getEnd_date() {
		return end_date;
	}
	public void setEnd_date(LocalDate end_date) {
		this.end_date = end_date;
	}
	
	
}
