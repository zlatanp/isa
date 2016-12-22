package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/*
 * prikazuje pocetnu stranu
 * 
 * */


@Controller
public class StartController {
	
	@RequestMapping("/")
	public String startMetoda(){
		return "home.html"; 
	}
}
