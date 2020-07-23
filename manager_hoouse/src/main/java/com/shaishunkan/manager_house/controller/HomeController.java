package com.shaishunkan.manager_house.controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.shaishunkan.manager_house.model.Home;
import com.shaishunkan.manager_house.model.Response;
import com.shaishunkan.manager_house.service.HomeService;

@RestController
@RequestMapping("/")
public class HomeController {

	@Autowired
	private HomeService homeService;
	
	@Autowired
	ServletContext servletContext;
	
	@PostMapping("admin/create-home")
	public ResponseEntity<Response> createHome(@ModelAttribute Home home) throws IOException{
		
		try {
			if(home.getMultipartFile() != null) {
				MultipartFile multipartFile = home.getMultipartFile();
				String fileName = multipartFile.getOriginalFilename();
				File file = new File(servletContext.getRealPath("/resources/images/") + fileName);
				multipartFile.transferTo(file);
				home.setImg_home("/resources/images/"+fileName);
			}			
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		File file = new File(servletContext.getRealPath("/resources/images/") + "test_img.jpg");
//		byte[] decodedBytes = Base64.getDecoder().decode(home.getImg_home());	
//		FileUtils.writeByteArrayToFile(file, decodedBytes);		
		Home homeTest = homeService.createHome(home);
		
		if(homeTest != null) {
			return ResponseEntity.ok().body(new Response("0000", "Create Home Successfully!", homeTest));
		}
		return ResponseEntity.ok().body(new Response("0001", "Fail!", null));
	}
}
