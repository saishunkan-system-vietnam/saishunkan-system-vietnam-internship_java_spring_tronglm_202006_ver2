
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

import com.saishunken.maneger_game.mapper.MapperTournament;
import com.saishunken.maneger_game.model.Response;
import com.saishunken.maneger_game.model.Tournament;
import com.saishunken.maneger_game.service.TournamentService;

@RestController
@RequestMapping("/")
public class TournamentController {
	
	@Autowired
	private TournamentService tournamentService;
	
	@Autowired
	private MapperTournament mapperTournament;
	
	
	//check name tournament
	@GetMapping("/istournamentname")
	public ResponseEntity<Response> isTeamName(@RequestParam String name_tnm,  @RequestParam Optional<Integer> id) {
		Integer tnmId;
		if(id.isPresent()) {
			tnmId  = id.get();
		} else {
			tnmId = null;
		}
		if(mapperTournament.getTotalByNameTournament(name_tnm, tnmId) > 0) {
			return ResponseEntity.ok().body(new Response("0005", "Name Tournament exist", 0, null));
		}		
	    return ResponseEntity.ok().body(new Response("0000", "oke", 0, null));
	}
	
	//add team in tournament
	@PostMapping("admin/tournament/addTeam")
	public ResponseEntity<Response> addTeam(@RequestBody Tournament tournament) {
		tournamentService.addTeamInTnm(tournament);
		return ResponseEntity.ok().body(new Response("0000", "Add team in tournament successfully", 0, null));

	}
	
	//add tournament
	@PostMapping("admin/tournament/create")
	public ResponseEntity<Response> createTournament(@RequestBody Tournament tournament) {
		Integer id = null;
		if(mapperTournament.getTotalByNameTournament(tournament.getName_tnm(), id)> 0){
			return ResponseEntity.ok().body(new Response("0007", "Create Tournament failed", 0, null));
			
		}else {
			tournamentService.createTournament(tournament);
			return ResponseEntity.ok().body(new Response("0000", "Create Tournament successfully", 0, null));
		}
	}
	
	//update
	@PostMapping("admin/tournament/update")
	public ResponseEntity<Response> updateUser(@RequestBody Tournament tournament) {
		Integer id = tournament.getId();
		if((mapperTournament.getTotalByNameTournament(tournament.getName_tnm(), id)) > 0) {
			return ResponseEntity.ok().body(new Response("0007", "Update Tournament failed", 0, null));
			
		}else {
			tournament.setEdit_date(LocalDateTime.now());
			mapperTournament.update(tournament);
			return ResponseEntity.ok().body(new Response("0000", "Update Tournament successfully", 0, null));
		}
	}
	
	//get by id
	@GetMapping("tournament/getbyid")
	public ResponseEntity<Response> getUserById(@RequestParam int id) {
		Response response = new Response();
		Tournament tournament = mapperTournament.getById(id);
		if (tournament != null) {
			response.setCode("0000");
			response.setMessage("Get data successfully");
			response.setPayload(tournament);
			return ResponseEntity.ok().body(response);
		}	
		return ResponseEntity.ok().body(new Response("0003", "data not found", 0, null));
	}
	
	//get all
	@GetMapping("tournament/getall")
	public ResponseEntity<Response> getAllTournament(@RequestParam Optional<Integer> page,
			@RequestParam Optional<String> name_tnm) {
		Response response = new Response();
		Tournament tournament = new Tournament();
		if (page.isPresent()) {
			tournament.setPage(page.get());
		} else {
			tournament.setPage(1);
		}
		tournament.setName_tnm(name_tnm.filter(x -> x.length() >= 1).map(Object::toString).orElse(""));
		tournament.setOffset((tournament.getPage() - 1) * tournament.getMaxPageItem());
		response.setTotalItem(tournamentService.getTotalTournament(tournament));
		response.setCode("0000");
		response.setMessage("Get data successfully");
		response.setPayload(tournamentService.getAll(tournament));
		return ResponseEntity.ok().body(response);
	}	
	
	//by status
	@GetMapping("/tournament/getbystatus")
	public ResponseEntity<Response> getByStatus(@RequestParam Integer status_flg ,@RequestParam Optional<Integer> page,
			@RequestParam Optional<String> name_tnm) {
		Response response = new Response();
		Tournament tournament = new Tournament();
		if (page.isPresent()) {
			tournament.setPage(page.get());
		} else {
			tournament.setPage(1);
		}
		tournament.setStatus_flg(status_flg);
		tournament.setName_tnm(name_tnm.filter(x -> x.length() >= 1).map(Object::toString).orElse(""));
		tournament.setOffset((tournament.getPage() - 1) * tournament.getMaxPageItem());
		response.setTotalItem(tournamentService.getTotalByStatus(tournament));
		response.setCode("0000");
		response.setMessage("Get data successfully");
		response.setPayload(tournamentService.getAllByStatus(tournament));
		return ResponseEntity.ok().body(response);
	}
}
