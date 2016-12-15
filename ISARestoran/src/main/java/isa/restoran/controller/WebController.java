package isa.restoran.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import isa.restoran.services.korisnici.KorisnikService;
@Configuration
@EnableAutoConfiguration
@ComponentScan
@Controller
public class WebController extends WebMvcConfigurerAdapter  {
	
	@RequestMapping("/")
	public String home(){
		return "home.html";
	}
	
	
}
