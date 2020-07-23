package com.shaishunkan.manager_house.model;

import java.time.LocalDate;

public class Equipment extends AbstractModel{

	private static final long serialVersionUID = 1L;
	
	private Integer id_room_1;
	private String name;
	private Integer status_flg;
	private LocalDate date_purchase;
	
	
	public Integer getId_room_1() {
		return id_room_1;
	}
	public void setId_room_1(Integer id_room_1) {
		this.id_room_1 = id_room_1;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getStatus_flg() {
		return status_flg;
	}
	public void setStatus_flg(Integer status_flg) {
		this.status_flg = status_flg;
	}
	public LocalDate getDate_purchase() {
		return date_purchase;
	}
	public void setDate_purchase(LocalDate date_purchase) {
		this.date_purchase = date_purchase;
	}

}
