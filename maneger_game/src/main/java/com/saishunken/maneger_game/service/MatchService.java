package com.saishunken.maneger_game.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.saishunken.maneger_game.mapper.MapperDetail_match;
import com.saishunken.maneger_game.mapper.MapperMatch;
import com.saishunken.maneger_game.mapper.MapperPointMemberMatch;
import com.saishunken.maneger_game.model.Match;

@Service
@Transactional
public class MatchService {

	@Autowired
	private MapperMatch mapperMatch;

	@Autowired
	private MapperDetail_match mapperDetail_match;

	@Autowired
	private MemberService memberService;
	
	@Autowired
	private MapperPointMemberMatch maperPointMember;

	public int addMatch(Match match) {
		String str = match.getStr_start_time();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		match.setStart_time(LocalDateTime.parse(str, formatter));

		match.setDel_flg(0);
		match.setEnd_time(LocalDateTime.now());

		if (match.getStatus_flg() == null) {
			match.setStatus_flg(0);
		}
		int isCheck = mapperMatch.insert(match);
		if (isCheck > 0) {
			match.getListDetailMatch().forEach(detailMatch -> {
				detailMatch.setId_match(match.getId());
				detailMatch.setDel_flg(0);
				if (detailMatch.getResult() == null) {
					detailMatch.setResult(0);
				}
				if (detailMatch.getWin_flg() == null) {
					detailMatch.setWin_flg(0);
				}
				mapperDetail_match.insert(detailMatch);

			});
		}
		return isCheck;
	}

	public void updateMatch(Match match) {
		String str;
		if (match.getStr_start_time() != null) {
			str = match.getStr_start_time();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
			match.setStart_time(LocalDateTime.parse(str, formatter));
		} else {
			match.setStart_time(null);
		}
		mapperMatch.update(match);
		if (match.getListDetailMatch() != null) {
			match.getListDetailMatch().forEach(detailMatch -> {
				if (detailMatch.getResult() == null) {
					detailMatch.setResult(0);
				}
				if (detailMatch.getWin_flg() == null) {
					detailMatch.setWin_flg(0);
				}
				mapperDetail_match.update(detailMatch);
			});
		}
		if (match.getListMember() != null) {
			match.getListMember().forEach(member -> {
				memberService.updateScored(member);
			});
		}
	}
	
	public void updateMatchAll(Match match) {
		String str;
		if (match.getStr_start_time() != null) {
			str = match.getStr_start_time();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
			match.setStart_time(LocalDateTime.parse(str, formatter));
		} else {
			match.setStart_time(null);
		}
		mapperMatch.update(match);
		if (match.getListDetailMatch() != null) {
			match.getListDetailMatch().forEach(detailMatch -> {
				if (detailMatch.getResult() == null) {
					detailMatch.setResult(0);
				}
				if (detailMatch.getWin_flg() == null) {
					detailMatch.setWin_flg(0);
				}
				mapperDetail_match.update(detailMatch);
			});
		}
		maperPointMember.deleteByIdMatch(match.getId());
		match.getListPointMemberMatch().forEach(element -> {
				maperPointMember.insert(element);			
		});
	}
}
