package com.shaishunkan.manager_house.service;

import java.io.IOException;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shaishunkan.manager_house.mapper.MapperHome;
import com.shaishunkan.manager_house.model.Home;

@Service
@Transactional
public class HomeService {

	@Autowired
	private MapperHome mapperHome;

	private String outputFilePath = "test_image_copy.jpg";

//	public static BufferedImage decodeToImage(String imageString) {
//		 
//        BufferedImage image = null;
//        byte[] imageByte;
//        try {
//            BASE64Decoder decoder = new BASE64Decoder();
//            imageByte = decoder.decodeBuffer(imageString);
//            ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
//            image = ImageIO.read(bis);
//            bis.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return image;
//    }

	public Home createHome(Home home) throws IOException {
		String encodedString = home.getImg_home();
		// home.setImg_home(patch);
		home.setDel_flg(0);
		home.setUpdate_date(LocalDateTime.now());
//		BufferedImage img_ = decodeToImage(home.getImg_home());
//		if(img_ != null) {
//		}
		home.setImg_home("111");
		int i = mapperHome.insert(home);
		if (i > 0) {
			return home;
		}
		return null;
	}
}
