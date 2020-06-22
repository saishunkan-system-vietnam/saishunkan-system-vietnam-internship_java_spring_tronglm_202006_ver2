package com.saishunken.maneger_game.controller;

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
@RequestMapping("/admin")
public class MatchController {
	
	@Autowired
	private MapperMatch mapperMatch;
	
	@Autowired
	private MatchService matchService;
	
	
	@PostMapping("match/create")
	public ResponseEntity<Response> createMatch(@RequestBody Match match){
		 matchService.addMatch(match);
		return ResponseEntity.ok().body(new Response("0000", "Create Team successfully", 0, null));
	}
	
	@GetMapping("/match/getbyid")
	public ResponseEntity<Response> getbyid (@RequestParam int id){
		Match match = mapperMatch.getById(id);
		return  ResponseEntity.ok().body(new Response("0000", "get oke", 0, match));
	}
}
