package com.saishunken.maneger_game.component;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.saishunken.maneger_game.model.Response;

@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint{

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse httpResponse,
			AuthenticationException authException) throws IOException, ServletException {
		// TODO Auto-generated method stub
		
		Response response = new Response();
		response.setMessage("Please login");
		response.setCode("0001");
		ObjectMapper mapper = new ObjectMapper();		
		String json = mapper.writeValueAsString(response);
		httpResponse.setStatus(HttpStatus.OK.value());
		httpResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
		httpResponse.getWriter().write(json);
		httpResponse.getWriter().flush();
	}

}
