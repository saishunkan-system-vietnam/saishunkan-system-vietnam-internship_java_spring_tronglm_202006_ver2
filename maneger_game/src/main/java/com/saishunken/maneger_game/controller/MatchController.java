package com.saishunken.maneger_game.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.saishunken.maneger_game.mapper.MapperMatch;
import com.saishunken.maneger_game.model.Match;
import com.saishunken.maneger_game.model.Response;
import com.saishunken.maneger_game.service.MatchService;

@RestController
@RequestMapping("/")
public class MatchController {
		

	@Autowired
	private MapperMatch mapperMatch;
	
	@Autowired
	private MatchService matchService;
	
//	@GetMapping("match/getbyid")
//	public ResponseEntity<Response> getMatchById(@RequestParam int id) {
//		Response response = new Response();
//		Match match = mapperMatch.getById(id);
//		if (match != null) {
//			response.setCode("0000");
//			response.setMessage("Get data successfully");
//			response.setPayload(match);
//			return ResponseEntity.ok().body(response);
//		}	
//		return ResponseEntity.ok().body(new Response("0003", "data not found", 0, null));
//	}
	
	@PostMapping("admin/match/create")
	public ResponseEntity<Response> createMatch(@RequestBody Match match){
		int id = matchService.addMatch(match);
		if(id < 0) {
			match.getId();
			return ResponseEntity.ok().body(new Response("0007", "Create false", 0, null));
		}
		return ResponseEntity.ok().body(new Response("0000", "Create Team successfully", match.getId(), match));
	}
	
	@PostMapping("admin/match/update")
	public ResponseEntity<Response> updateMatch(@RequestBody Match match){
		matchService.updateMatch(match);
		return ResponseEntity.ok().body(new Response("0000", "Update Team successfully",0, null));
	}
	
	@PostMapping("admin/match/update-all")
	public ResponseEntity<Response> updateMatchAll(@RequestBody Match match){
		matchService.updateMatchAll(match);
		return ResponseEntity.ok().body(new Response("0000", "Update Team successfully",0, null));
	}
	
	@PostMapping("admin/match/delete")
	public ResponseEntity<Response> deleteMatch(@RequestBody Match match){
		mapperMatch.update(match);
		return ResponseEntity.ok().body(new Response("0000", "Delete Team successfully",0, null));
	}
	
	
	@GetMapping("match/get-all")
	public ResponseEntity<Response> getMatchAll(@RequestParam int id) {
		List<Match> list = mapperMatch.getAllMatch(id);	
		return ResponseEntity.ok().body(new Response("0000", "get list oke", 0, list));
	}
	
	@GetMapping("match/get-match")
	public ResponseEntity<Response> getMatchById(@RequestParam int id) {
		Response response = new Response();
		Match object = mapperMatch.getMatchDetail(id);
		if (object != null) {
			response.setCode("0000");
			response.setMessage("Get data successfully");
			response.setPayload(object);
			return ResponseEntity.ok().body(response);
		}	
		return ResponseEntity.ok().body(new Response("0003", "data not found", 0, null));
	}
	
	@GetMapping("match/get-match-point")
	public ResponseEntity<Response> getMatchAnPointMember(@RequestParam int id) {
		Response response = new Response();
		Match object = mapperMatch.getMatchDetailAll(id);
		if (object != null) {
			response.setCode("0000");
			response.setMessage("Get data successfully");
			response.setPayload(object);
			return ResponseEntity.ok().body(response);
		}	
		return ResponseEntity.ok().body(new Response("0003", "data not found", 0, null));
	}
	
	@PostMapping("admin/match_one/update")
	public ResponseEntity<Response> updateMatchOne(@RequestBody Match match){
		mapperMatch.update(match);
		return ResponseEntity.ok().body(new Response("0000", "Update Team successfully",0, null));
	}

//	@PostMapping("admin/detailmatch/create")
//	public ResponseEntity<Response> createDetailMatch(@RequestBody Detail_match detail_match){
//		int id = matchService.addDeatailMatch(detail_match);
//		if(id > 0) {
//			detail_match.getId();
//		}
//		return ResponseEntity.ok().body(new Response("0000", "Create Team successfully", 0, detail_match));
//	}
}
