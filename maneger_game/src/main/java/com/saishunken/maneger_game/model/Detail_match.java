package com.saishunken.maneger_game.model;

public class Detail_match  extends AbstractModel{
	
	private static final long serialVersionUID = 1L;
	
	private Integer id_team;
	private Integer id_match;
	private Integer result;
	private Integer win_flg;
	
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
