package com.saishunken.maneger_game.component;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.saishunken.maneger_game.model.Account;
import com.saishunken.maneger_game.model.Response;

@Component
public class AuthenticationSuccessHandlerImp implements AuthenticationSuccessHandler{

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse httpResponse,
			Authentication authentication) throws IOException, ServletException {
		// TODO Auto-generated method stub
		Response response = new Response();
		
		response.setPayload(authentication.getPrincipal());
		response.setMessage("Login successfully");
		response.setCode("0000");
       // Account userDetails = (Account) authentication.getPrincipal();		
		ObjectMapper mapper = new ObjectMapper();		
		String json = mapper.writeValueAsString(response);
		httpResponse.setStatus(HttpStatus.OK.value());
		httpResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
		httpResponse.getWriter().write(json);
		//authentication.getAuthorities();	
		httpResponse.getWriter().flush();
		
	}
}
