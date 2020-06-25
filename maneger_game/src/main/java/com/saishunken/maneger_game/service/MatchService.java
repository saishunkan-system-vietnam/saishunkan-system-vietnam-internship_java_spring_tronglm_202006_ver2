package com.saishunken.maneger_game.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.saishunken.maneger_game.mapper.MapperDetail_match;
import com.saishunken.maneger_game.mapper.MapperMatch;
import com.saishunken.maneger_game.model.Match;

@Service
@Transactional
public class MatchService {
	
	@Autowired
	private MapperMatch mapperMatch;
	
	@Autowired
	private MapperDetail_match mapperDetail_match;
	
	public int addMatch(Match match) {
		match.setDel_flg(0);
		match.setEnd_time(LocalDateTime.now());
		match.setStart_time(LocalDateTime.now());
		
		if(match.getStatus_flg() == null) {
			match.setStatus_flg(0);
		}		
		int isCheck = mapperMatch.insert(match);
		if(isCheck > 0) {
			match.getListDetailMatch().forEach(detailMatch -> {
				detailMatch.setId_match(match.getId());
				detailMatch.setDel_flg(0);
				if(detailMatch.getResult() == null) {
					detailMatch.setResult(0);
				}
				if(detailMatch.getWin_flg() == null) {
					detailMatch.setWin_flg(0);
				}
				int idDetail = mapperDetail_match.insert(detailMatch);
				if(idDetail > 0) {
					
				}
			});	
		}
		return isCheck;
	}


//	public int addDeatailMatch(Detail_match detail_match) {
//		detail_match.setDel_flg(0);
//		if(detail_match.getResult() == null) {
//			detail_match.setResult(0);
//		}
//		if(detail_match.getWin_flg() == null) {
//			detail_match.setWin_flg(0);
//		}
//		return 	mapperDetail_match.insert(detail_match);
//	}
}