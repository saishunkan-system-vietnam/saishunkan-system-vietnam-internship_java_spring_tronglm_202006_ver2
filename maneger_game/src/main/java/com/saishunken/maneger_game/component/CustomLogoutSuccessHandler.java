package com.saishunken.maneger_game.component;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.saishunken.maneger_game.model.Response;

@Component
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {

	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse httpResponse, Authentication authentication)
			throws IOException, ServletException {
		// TODO Auto-generated method stub

//	    if (authentication != null && authentication.getDetails() != null) {
//            try {
            	Response response = new Response();
            	response.setMessage("Logout successfully");
        		response.setCode("0000");
        		ObjectMapper mapper = new ObjectMapper();		
        		String json = mapper.writeValueAsString(response);
        		httpResponse.setStatus(HttpStatus.OK.value());
        		httpResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
        		httpResponse.getWriter().write(json);
        		httpResponse.getWriter().flush();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
	}

}
