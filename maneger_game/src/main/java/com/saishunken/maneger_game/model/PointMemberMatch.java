package com.saishunken.maneger_game.model;

public class PointMemberMatch extends AbstractModel{

	private static final long serialVersionUID = 1L;
	
	private Integer id_member;
	private Integer id_match;
	private Integer scored;
	private String at_time;
	private String name_member;
	private String nickname;
	
	public PointMemberMatch() {
		super();
	}
	public PointMemberMatch(Integer id_member, Integer id_match, Integer scored, String at_time) {
		super();
		this.id_member = id_member;
		this.id_match = id_match;
		this.scored = scored;
		this.at_time = at_time;
	}
	
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
	public Integer getId_member() {
		return id_member;
	}
	public void setId_member(Integer id_member) {
		this.id_member = id_member;
	}
	public Integer getId_match() {
		return id_match;
	}
	public void setId_match(Integer id_match) {
		this.id_match = id_match;
	}
	public Integer getScored() {
		return scored;
	}
	public void setScored(Integer scored) {
		this.scored = scored;
	}
	public String getAt_time() {
		return at_time;
	}
	public void setAt_time(String at_time) {
		this.at_time = at_time;
	}
	
	
	
}
