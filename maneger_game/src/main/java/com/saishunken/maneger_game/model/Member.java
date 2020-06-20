package com.saishunken.maneger_game.model;

public class Member extends AbstractModel{

	private static final long serialVersionUID = 1L;
	
	private String name_member;
	private String nickname;
	private Integer id_team;
	private Integer cap_flg;
	private Integer scored;
	
	public String getName_member() {
		return name_member;
	}
	public void setName_member(String name_member) {
		this.name_member = name_member;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public Integer getId_team() {
		return id_team;
	}
	public void setId_team(Integer id_team) {
		this.id_team = id_team;
	}
	public Integer getCap_flg() {
		return cap_flg;
	}
	public void setCap_flg(Integer cap_flg) {
		this.cap_flg = cap_flg;
	}
	public Integer getScored() {
		return scored;
	}
	public void setScored(Integer scored) {
		this.scored = scored;
	}
	
	
	
}
