package com.example.controller;

import java.awt.image.BufferedImage;
import java.io.*;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/picture")
public class UploadPicture {
	private static final Logger logger = LoggerFactory.getLogger(UploadPicture.class);

	@RequestMapping(value = "/upload", method = {RequestMethod.POST})
	public synchronized String uploadPicture(@RequestParam("file") MultipartFile file) throws IOException{
		
			System.out.println("udjesli");
		
			if (!file.isEmpty()) {
				 BufferedImage src = ImageIO.read(new ByteArrayInputStream(file.getBytes()));
				 File destination = new File("C:/Users/ZlajoX/Documents/newImage.jpg"); // something like C:/Users/tom/Documents/nameBasedOnSomeId.png
				 ImageIO.write(src, "jpg", destination);
				 return "usos";
	}
	return "proso";		
	}
	
	
}
