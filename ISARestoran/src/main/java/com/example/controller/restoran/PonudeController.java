package com.example.controller.restoran;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.service.KorisnikService;
import com.example.service.korisniciImpl.PonudjacService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/ponude")
public class PonudeController {

	@Autowired
	private KorisnikService korisnikService;
	
	@Autowired
	private PonudjacService ponudjacService;
	
	private ObjectMapper objectMapper = new ObjectMapper();
	
	@RequestMapping(value="/dobaviPonudjace", method = RequestMethod.GET, produces = "application/json")
	public String dobaviPonudjace() throws JsonProcessingException{
		return objectMapper.writeValueAsString(ponudjacService.findAll());
	}
}
