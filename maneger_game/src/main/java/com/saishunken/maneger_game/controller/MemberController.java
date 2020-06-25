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

import com.saishunken.maneger_game.mapper.MapperMember;
import com.saishunken.maneger_game.mapper.MapperTeam;
import com.saishunken.maneger_game.model.Member;
import com.saishunken.maneger_game.model.Response;
import com.saishunken.maneger_game.model.Team;
import com.saishunken.maneger_game.service.MemberService;

@RestController
@RequestMapping("/admin")
public class MemberController {
	
	@Autowired
	private MapperMember mapperMember;
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private MapperTeam mapperTeam;
	
	//all
	@GetMapping("/getall/member")
	public ResponseEntity<Response> getAllMember(@RequestParam Optional<Integer> page,
			@RequestParam Optional<String> nameMember) {
		Response response = new Response();
		Member member = new Member();
		if (page.isPresent()) {
			member.setPage(page.get());
		} else {
			member.setPage(1);
		}
		member.setName_member(nameMember.filter(x -> x.length() >= 1).map(Object::toString).orElse(""));
		member.setOffset((member.getPage() - 1) * member.getMaxPageItem());
		response.setTotalItem(memberService.getTotalMember(member));
		response.setCode("0000");
		response.setMessage("Get data successfully");
		response.setPayload(memberService.getAll(member));
		return ResponseEntity.ok().body(response);
	}
	
	//get team
	@GetMapping("/getbyteam/member")
	public ResponseEntity<Response> getMemberByTeam(@RequestParam Integer id_team, @RequestParam Optional<Integer> page,
			@RequestParam Optional<String> nameMember) {
		Response response = new Response();
		Member member = new Member();
		if (page.isPresent()) {
			member.setPage(page.get());
		} else {
			member.setPage(1);
		}
		if (nameMember.isPresent()) {
			member.setName_member(nameMember.filter(x -> x.length() >= 1).map(Object::toString).orElse(""));
		} else {
			member.setName_member(null);
		}		
		member.setId_team(id_team);
		//member.setName_member(nameMember.filter(x -> x.length() >= 1).map(Object::toString).orElse(""));
		member.setOffset((member.getPage() - 1) * member.getMaxPageItem());
		Team team = mapperTeam.getById(id_team);
		if(team != null) {
			response.setTotalItem(memberService.getTotalByTeam(member));
			response.setCode("0000");
			response.setMessage("Get data successfully");
			response.setPayload(memberService.getByTeam(member));
			return ResponseEntity.ok().body(response);
		} 
		return ResponseEntity.ok().body(new Response("0003", "data not found", 0, null));
		
		
	}
	
	//get by id team
	@GetMapping("/getbyidteam/member")
	public ResponseEntity<Response> getMemberByIdTeam(@RequestParam Optional<Integer> page,
			@RequestParam Optional<String> nameMember) {
		Response response = new Response();
		Member member = new Member();
		if (page.isPresent()) {
			member.setPage(page.get());
		} else {
			member.setPage(1);
		}
		member.setName_member(nameMember.filter(x -> x.length() >= 1).map(Object::toString).orElse(""));
		member.setOffset((member.getPage() - 1) * member.getMaxPageItem());		
		response.setTotalItem(memberService.getTotalByIdTeam(member));
		response.setCode("0000");
		response.setMessage("Get data successfully");
		response.setPayload(memberService.getByIdTeam(member));
		return ResponseEntity.ok().body(response);
	}
	
	//get by not id team
	@GetMapping("/getbynotidteam/member")
	public ResponseEntity<Response> getMemberByNotIdTeam(@RequestParam Optional<Integer> page,
			@RequestParam Optional<String> nameMember) {
		Response response = new Response();
		Member member = new Member();
		if (page.isPresent()) {
			member.setPage(page.get());
		} else {
			member.setPage(1);
		}
		member.setName_member(nameMember.filter(x -> x.length() >= 1).map(Object::toString).orElse(""));
		member.setOffset((member.getPage() - 1) * member.getMaxPageItem());
		response.setTotalItem(memberService.getTotalByNotIdTeam(member));
		response.setCode("0000");
		response.setMessage("Get data successfully");
		response.setPayload(memberService.getByNotIdTeam(member));
		return ResponseEntity.ok().body(response);
	}
	
	//check nickname
	@GetMapping("/isnickname")
	public ResponseEntity<Response> isNickName(@RequestParam String nickname,  @RequestParam Optional<Integer> id) {	
		Integer memberId;
		if(id.isPresent()) {
			memberId  = id.get();
		} else {
			memberId = null;
		}
		if(mapperMember.getTotalByNickName(nickname, memberId) > 0) {
			return ResponseEntity.ok().body(new Response("0005", "Nick Name exist", 0, null));
		}		
	    return ResponseEntity.ok().body(new Response("0000", "oke", 0, null));
	}
	// create member
	@PostMapping("/createMember")
	public ResponseEntity<Response> createUser(@RequestBody Member member) {
		Integer id = null;
		if(mapperMember.getTotalByNickName(member.getNickname(), id) > 0) {
			return ResponseEntity.ok().body(new Response("0007", "Create Member failed", 0, null));
			
		}else {
			memberService.createMember(member);
			return ResponseEntity.ok().body(new Response("0000", "Create Member successfully", 0, null));
		}
	}
	
	// update member
	@PostMapping("/updateMember")
	public ResponseEntity<Response> updateUser(@RequestBody Member member) {
		Integer id = member.getId();
		if(mapperMember.getTotalByNickName(member.getNickname(), id) > 0) {
			return ResponseEntity.ok().body(new Response("0007", "Update Member failed", 0, null));
			
		}else {
			member.setEdit_date(LocalDateTime.now());
			mapperMember.update(member);
			return ResponseEntity.ok().body(new Response("0000", "Update Member successfully", 0, null));
		}
	}
	
	//get by id
	@GetMapping("/member/getbyid")
	public ResponseEntity<Response> getUserById(@RequestParam int id) {
		Response response = new Response();
		Member member = mapperMember.getById(id);
		if (member != null) {
			response.setCode("0000");
			response.setMessage("Get data successfully");
			response.setPayload(member);
			return ResponseEntity.ok().body(response);
		}	
		return ResponseEntity.ok().body(new Response("0003", "data not found", 0, null));
	}
}
