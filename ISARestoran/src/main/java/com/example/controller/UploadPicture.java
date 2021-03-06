package com.example.controller;

import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.util.Base64;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.beans.korisnici.Korisnik;
import com.example.service.KorisnikService;

@RestController
@RequestMapping("/picture")
public class UploadPicture {
	
	@Autowired
	private KorisnikService korisnikService;

	@RequestMapping(value = "/upload", method = {RequestMethod.POST})
	public synchronized void uploadPicture(HttpServletResponse response, @RequestParam("file") MultipartFile file, @RequestParam("email") String email) throws IOException{
	
			
			if (!file.isEmpty() && email.contains("@") && file.getSize()<999998) {
				//Upis u fajl sistem, lokal
				 BufferedImage src = ImageIO.read(new ByteArrayInputStream(file.getBytes()));
				 File destination = new File(System.getProperty("catalina.base")+File.separator+"mydata.jpg"); // something like C:/Users/tom/Documents/nameBasedOnSomeId.png
				 ImageIO.write(src, "jpg", destination);
				 
				//Citanje fajla i upis u bazu
					
					File fi = new File(System.getProperty("catalina.base")+File.separator+"mydata.jpg");
					byte[] fileContent = Files.readAllBytes(fi.toPath());
				
					Iterable<Korisnik> listaKorisnika = korisnikService.getAllKorisnici();
				
					for (Korisnik korisnik : listaKorisnika) {
							if(korisnik.getEmail().equals(email)){
									korisnik.setSlika(fileContent);
									korisnikService.saveKorisnik(korisnik);
									
							}
					}
				response.sendRedirect("/home.html");
			}else{
				response.sendRedirect("/home.html");
			}
						
	}
	
	@ResponseBody
	@RequestMapping(value = "/dajSliku", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
	public byte[] testphoto(HttpServletResponse response) throws IOException {
		Iterable<Korisnik> listaKorisnika = korisnikService.getAllKorisnici();
		byte[] slika = null;
		
		for (Korisnik korisnik : listaKorisnika) {
				if(korisnik.getEmail().equals("jasmina.eminovski@gmail.com")){
						slika = korisnik.getSlika();
				}
		}
		
		byte[] encodedBytes = Base64.getEncoder().encode(slika);
		
//		response.setContentType("image/jpeg");
//		ServletOutputStream responseOutputStream = response.getOutputStream();
//		responseOutputStream.write(slika);
//		responseOutputStream.flush();
//		responseOutputStream.close();
		
		return encodedBytes;
	}
	
	
}
