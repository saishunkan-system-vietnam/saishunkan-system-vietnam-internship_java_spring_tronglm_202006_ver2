package com.saishunken.maneger_game.model;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class Team extends AbstractModel {

	private static final long serialVersionUID = 1L;

	private String name_team;
	private Integer id_tournament;
	private Integer points;
	private String link_logo;
	
	private List<Detail_match> listDetailMatch;
	
	
	

	public List<Detail_match> getListDetailMatch() {
		return listDetailMatch;
	}

	public void setListDetailMatch(List<Detail_match> listDetailMatch) {
		this.listDetailMatch = listDetailMatch;
	}

	private String founding_date;
	
	private String info;
	
	
	public String getFounding_date() {
		return founding_date;
	}

	public void setFounding_date(String founding_date) {
		this.founding_date = founding_date;
	}

	private MultipartFile multipartFile;


	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

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

	public MultipartFile getMultipartFile() {
		return multipartFile;
	}

	public void setMultipartFile(MultipartFile multipartFile) {
		this.multipartFile = multipartFile;
	}

}
