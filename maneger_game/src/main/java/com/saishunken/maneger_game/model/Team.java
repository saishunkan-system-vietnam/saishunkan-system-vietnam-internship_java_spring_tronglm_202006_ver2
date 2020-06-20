package com.saishunken.maneger_game.model;

public class Team extends AbstractModel{
	
	private static final long serialVersionUID = 1L;
	
	private String name_team;
	private Integer id_tournament;
	private Integer points;
	private String link_logo;
	
	public String getName_team() {
		return name_team;
	}
	public void setName_team(String name_team) {
		this.name_team = name_team;
	}
	public Integer getId_tournament() {
		return id_tournament;
	}
	public void setId_tournament(Integer id_tournament) {
		this.id_tournament = id_tournament;
	}
	public Integer getPoints() {
		return points;
	}
	public void setPoints(Integer points) {
		this.points = points;
	}
	public String getLink_logo() {
		return link_logo;
	}
	public void setLink_logo(String link_logo) {
		this.link_logo = link_logo;
	}
	
	
	

}
