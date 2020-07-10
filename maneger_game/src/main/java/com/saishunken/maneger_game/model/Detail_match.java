package com.saishunken.maneger_game.model;

import java.util.List;

public class Detail_match  extends AbstractModel{
	
	private static final long serialVersionUID = 1L;
	
	private Integer id_team;
	private String name_team;
	private Integer id_match;
	private Integer result;
	private Integer win_flg;
	private List<PointMemberMatch> listPointMDM;
	
		
	public List<PointMemberMatch> getListPointMDM() {
		return listPointMDM;
	}
	public void setListPointMDM(List<PointMemberMatch> listPointMDM) {
		this.listPointMDM = listPointMDM;
	}
	public String getName_team() {
		return name_team;
	}
	public void setName_team(String name_team) {
		this.name_team = name_team;
	}
	public Integer getId_team() {
		return id_team;
	}
	public void setId_team(Integer id_team) {
		this.id_team = id_team;
	}
	public Integer getId_match() {
		return id_match;
	}
	public void setId_match(Integer id_match) {
		this.id_match = id_match;
	}
	public Integer getResult() {
		return result;
	}
	public void setResult(Integer result) {
		this.result = result;
	}
	public Integer getWin_flg() {
		return win_flg;
	}
	public void setWin_flg(Integer win_flg) {
		this.win_flg = win_flg;
	}	

}
