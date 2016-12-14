package isa.restoran.controller;

import isa.restoran.service.KorisnikService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
@Configuration
@EnableAutoConfiguration
@ComponentScan
@Controller
public class WebController extends WebMvcConfigurerAdapter  {

	@Autowired
	private KorisnikService korisnikService;
	

//	@RequestMapping(value = "/korisnik",
//			method = RequestMethod.GET)
//	public Collection<Korisnik> getAllUsers(){
//		return korisnikService.getAllUsers();
//	}
	
	@RequestMapping("/")
	public String home(){
		return "home.html";
	}
	
	
}
