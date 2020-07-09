package com.saishunken.maneger_game.controller;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.saishunken.maneger_game.mapper.MapperTeam;
import com.saishunken.maneger_game.model.Response;
import com.saishunken.maneger_game.model.Team;
import com.saishunken.maneger_game.service.TeamService;

@RestController
@RequestMapping("/")
public class TeamCotroller {

	@Autowired
	private TeamService teamService;

	@Autowired
	private MapperTeam mapperTeam;
	
	@Autowired
	ServletContext servletContext;

	// check name team
	@GetMapping("/isteamname")
	public ResponseEntity<Response> isTeamName(@RequestParam String name_team, @RequestParam Optional<Integer> id) {
		Integer teamId;
		if (id.isPresent()) {
			teamId = id.get();
		} else {
			teamId = null;
		}
		if (mapperTeam.getTotalByNameTeam(name_team, teamId) > 0) {
			return ResponseEntity.ok().body(new Response("0005", "Name Team exist", 0, null));
		}
		return ResponseEntity.ok().body(new Response("0000", "oke", 0, null));
	}
	
	
	@GetMapping("team/get-rank")
	public ResponseEntity<Response> getRank(@ModelAttribute Team team) {		
		return ResponseEntity.ok().body(new Response("0000", "get rank successfully", 0, teamService.getListRankTeam(team)));
	}

	// create team
	@PostMapping("admin/createTeam")
	public ResponseEntity<Response> createTeam(@ModelAttribute Team team) {
		Integer id = null;
		try {
			if(team.getMultipartFile() != null) {
				MultipartFile multipartFile = team.getMultipartFile();
				String fileName = multipartFile.getOriginalFilename();
				File file = new File(servletContext.getRealPath("/resources/images/") + fileName);
				multipartFile.transferTo(file);
				team.setLink_logo("/resources/images/"+fileName);
			}			
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (mapperTeam.getTotalByNameTeam(team.getName_team(), id) > 0) {
			return ResponseEntity.ok().body(new Response("0007", "Create Team failed", 0, null));

		} else {
			teamService.createTeam(team);
			return ResponseEntity.ok().body(new Response("0000", "Create Team successfully", 0, null));
		}
	}
	
	//delete team in tournament
	@PostMapping("admin/updateTeamInTnm")
	public ResponseEntity<Response> updateTeamInTnm(@RequestBody Team team){
		if(team.getId() == null || team.getId_tournament() == null) {
			return ResponseEntity.ok().body(new Response("0005", "Not data update", 0, null));
		}
		mapperTeam.update(team);
		return ResponseEntity.ok().body(new Response("0000", "Successfully", 0, null));
	}

	// update
	@PostMapping("admin/updateTeam")
	public ResponseEntity<Response> updateUser(@ModelAttribute Team team) {
		Integer id = team.getId();
		try {
			if(team.getMultipartFile() != null) {
				MultipartFile multipartFile = team.getMultipartFile();
				String fileName = multipartFile.getOriginalFilename();
				File file = new File(servletContext.getRealPath("/resources/images/") + fileName);
				multipartFile.transferTo(file);
				team.setLink_logo("/resources/images/"+fileName);
			}
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (mapperTeam.getTotalByNameTeam(team.getName_team(), id) > 0) {
			return ResponseEntity.ok().body(new Response("0007", "Update Team failed", 0, null));

		} else {
			team.setEdit_date(LocalDateTime.now());
			mapperTeam.update(team);
			return ResponseEntity.ok().body(new Response("0000", "Update Team successfully", 0, null));
		}
	}

	// get by id
	@GetMapping("admin/team/getbyid")
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

	// get all
	@GetMapping("admin/team/getall")
	public ResponseEntity<Response> getAllMember(@RequestParam Optional<String> name_team) {
		Response response = new Response();
		Team team = new Team();
			team.setPage(1);
		team.setName_team(name_team.filter(x -> x.length() >= 1).map(Object::toString).orElse(""));
		team.setOffset(0);
		team.setMaxPageItem(100);
		response.setTotalItem(teamService.getTotalTeam(team));
		response.setCode("0000");
		response.setMessage("Get data successfully");
		response.setPayload(teamService.getAll(team));
		return ResponseEntity.ok().body(response);
	}

	// get by tournament
	@GetMapping("team/gettournament")
	public ResponseEntity<Response> getTournament(@RequestParam Integer id_tournament,
			@RequestParam Optional<Integer> page, @RequestParam Optional<String> name_team) {
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
	
	
	
	//get point in team
	@GetMapping("team/getpoint-tnm")
	public ResponseEntity<Response> getPoint(@RequestParam int id) {
		
		
		return ResponseEntity.ok().body(new Response("0000", "Get data successfully", 1, mapperTeam.getById(id)));
	}

	// by id tournament
	@GetMapping("team/getbyidtournament")
	public ResponseEntity<Response> getByIdTournament(@RequestParam Integer id) {
		if(mapperTeam.getByIdTournament(id).getListDetailMatch() == null) {
			if(mapperTeam.getById(id) != null) {
				return ResponseEntity.ok().body(new Response("0000", "Get data successfully", 1, mapperTeam.getById(id)));
			}
			return ResponseEntity.ok().body(new Response("0002", "Data not found", 0, null));
		}
		if(mapperTeam.getByIdTournament(id).getListDetailMatch().size() == 0) {
			if(mapperTeam.getById(id) != null) {
				return ResponseEntity.ok().body(new Response("0000", "Get data successfully", 1, mapperTeam.getById(id)));
			}
			return ResponseEntity.ok().body(new Response("0002", "Data not found", 0, null));
		}
		return ResponseEntity.ok().body(new Response("0008", "Data can not delete", 0, null));
	}
	
	

	// by not id tournament
	@GetMapping("team/getbynotidtournament")
	public ResponseEntity<Response> getByNotIdTournament( @RequestParam Optional<Integer> page,
			@RequestParam Optional<String> name_team) {
		Response response = new Response();
		Team team = new Team();
		if (page.isPresent()) {
			team.setPage(page.get());
		} else {
			team.setPage(1);
		}
		//team.setId_tournament(id_tournament);
		team.setName_team(name_team.filter(x -> x.length() >= 1).map(Object::toString).orElse(""));
		team.setOffset((team.getPage() - 1) * team.getMaxPageItem());
		response.setTotalItem(teamService.getTotalByNotIdTournament(team));
		response.setCode("0000");
		response.setMessage("Get data successfully");
		response.setPayload(teamService.getAllByNotIdTournament(team));
		return ResponseEntity.ok().body(response);
	}

}
