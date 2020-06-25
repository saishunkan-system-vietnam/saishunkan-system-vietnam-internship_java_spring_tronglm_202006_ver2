package com.saishunken.maneger_game.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

public class AbstractModel implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id;
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime create_date = LocalDateTime.now();
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime edit_date = LocalDateTime.now();
	private Integer page = 1;
	private int maxPageItem = 5;
	private int offset;
	private Integer del_flg;
	// private List<T> results;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getDel_flg() {
		return del_flg;
	}

	public void setDel_flg(Integer del_flg) {
		this.del_flg = del_flg;
	}

	public LocalDateTime getCreate_date() {
		return create_date;
	}

	public void setCreate_date(LocalDateTime create_date) {
		this.create_date = create_date;
	}

	public LocalDateTime getEdit_date() {
		return edit_date;
	}

	public void setEdit_date(LocalDateTime edit_date) {
		this.edit_date = edit_date;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public int getMaxPageItem() {
		return maxPageItem;
	}

	public void setMaxPageItem(int maxPageItem) {
		this.maxPageItem = maxPageItem;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}
	
	
//	public List<T> getResults() {
//		return results;
//	}
//	public void setResults(List<T> results) {
//		this.results = results;
//	}

}
