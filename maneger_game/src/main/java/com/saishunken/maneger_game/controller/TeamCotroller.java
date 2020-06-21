package com.saishunken.maneger_game.controller;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.saishunken.maneger_game.mapper.MapperTeam;
import com.saishunken.maneger_game.model.Response;
import com.saishunken.maneger_game.model.Team;
import com.saishunken.maneger_game.service.TeamService;

@RestController
@RequestMapping("/admin")
public class TeamCotroller {
	
	@Autowired
	private TeamService teamService;
	
	@Autowired
	private MapperTeam mapperTeam;
	
	//check name team
	@GetMapping("/isteamname")
	public ResponseEntity<Response> isTeamName(@RequestParam String name_team,  @RequestParam Optional<Integer> id) {	
		Integer teamId;
		if(id.isPresent()) {
			teamId  = id.get();
		} else {
			teamId = null;
		}
		if(mapperTeam.getTotalByNameTeam(name_team, teamId) > 0) {
			return ResponseEntity.ok().body(new Response("0005", "Name Team exist", 0, null));
		}		
	    return ResponseEntity.ok().body(new Response("0000", "oke", 0, null));
	}
	
	//create team
	@PostMapping("/createTeam")
	public ResponseEntity<Response> createTeam(@RequestBody Team team) {
		Integer id = null;
		if(mapperTeam.getTotalByNameTeam(team.getName_team(), id) > 0) {
			return ResponseEntity.ok().body(new Response("0007", "Create Team failed", 0, null));
			
		}else {
			teamService.createTeam(team);
			return ResponseEntity.ok().body(new Response("0000", "Create Team successfully", 0, null));
		}
	}
	
	//update
	@PostMapping("/updateTeam")
	public ResponseEntity<Response> updateUser(@RequestBody Team team) {
		Integer id = team.getId();
		if(mapperTeam.getTotalByNameTeam(team.getName_team(), id) > 0) {
			return ResponseEntity.ok().body(new Response("0007", "Update Team failed", 0, null));
			
		}else {
			team.setEdit_date(LocalDateTime.now());
			mapperTeam.update(team);
			return ResponseEntity.ok().body(new Response("0000", "Update Team successfully", 0, null));
		}
	}
	
	//get by id
	@GetMapping("/team/getbyid")
	public ResponseEntity<Response> getUserById(@RequestParam int id) {
		Response response = new Response();
		Team team = mapperTeam.getById(id);
		if (team != null) {
			response.setCode("0000");
			response.setMessage("Get data successfully");
			response.setPayload(team);
			return ResponseEntity.ok().body(response);
		}	
		return ResponseEntity.ok().body(new Response("0003", "data not found", 0, null));
	}
	
	//get all
	@GetMapping("/team/getall")
	public ResponseEntity<Response> getAllMember(@RequestParam Optional<Integer> page,
			@RequestParam Optional<String> name_team) {
		Response response = new Response();
		Team team = new Team();
		if (page.isPresent()) {
			team.setPage(page.get());
		} else {
			team.setPage(1);
		}
		team.setName_team(name_team.filter(x -> x.length() >= 1).map(Object::toString).orElse(""));
		team.setOffset((team.getPage() - 1) * team.getMaxPageItem());
		response.setTotalItem(teamService.getTotalTeam(team));
		response.setCode("0000");
		response.setMessage("Get data successfully");
		response.setPayload(teamService.getAll(team));
		return ResponseEntity.ok().body(response);
	}
	
	//get by tournament
	@GetMapping("/team/gettournament")
	public ResponseEntity<Response> getTournament(@RequestParam Integer id_tournament ,@RequestParam Optional<Integer> page,
			@RequestParam Optional<String> name_team) {
		Response response = new Response();
		Team team = new Team();
		if (page.isPresent()) {
			team.setPage(page.get());
		} else {
			team.setPage(1);
		}
		team.setId_tournament(id_tournament);
		team.setName_team(name_team.filter(x -> x.length() >= 1).map(Object::toString).orElse(""));
		team.setOffset((team.getPage() - 1) * team.getMaxPageItem());
		response.setTotalItem(teamService.getTotalByTournament(team));
		response.setCode("0000");
		response.setMessage("Get data successfully");
		response.setPayload(teamService.getAllByTournament(team));
		return ResponseEntity.ok().body(response);
	}
	
		//by id tournament
		@GetMapping("/team/getbyidtournament")
		public ResponseEntity<Response> getByIdTournament(@RequestParam Optional<Integer> page,
				@RequestParam Optional<String> name_team) {
			Response response = new Response();
			Team team = new Team();
			if (page.isPresent()) {
				team.setPage(page.get());
			} else {
				team.setPage(1);
			}
			team.setName_team(name_team.filter(x -> x.length() >= 1).map(Object::toString).orElse(""));
			team.setOffset((team.getPage() - 1) * team.getMaxPageItem());
			response.setTotalItem(teamService.getTotalByIdTournament(team));
			response.setCode("0000");
			response.setMessage("Get data successfully");
			response.setPayload(teamService.getAllByIdTournament(team));
			return ResponseEntity.ok().body(response);
		}
		
		//by not id tournament
		@GetMapping("/team/getbynotidtournament")
		public ResponseEntity<Response> getByNotIdTournament(@RequestParam Optional<Integer> page,
				@RequestParam Optional<String> name_team) {
			Response response = new Response();
			Team team = new Team();
			if (page.isPresent()) {
				team.setPage(page.get());
			} else {
				team.setPage(1);
			}
			team.setName_team(name_team.filter(x -> x.length() >= 1).map(Object::toString).orElse(""));
			team.setOffset((team.getPage() - 1) * team.getMaxPageItem());
			response.setTotalItem(teamService.getTotalByNotIdTournament(team));
			response.setCode("0000");
			response.setMessage("Get data successfully");
			response.setPayload(teamService.getAllByNotIdTournament(team));
			return ResponseEntity.ok().body(response);
		}
		
		
		
	

}
