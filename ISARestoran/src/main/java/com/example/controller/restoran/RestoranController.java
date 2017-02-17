package com.example.controller.restoran;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.restoran.RestoranDTO;
import com.example.enums.TipRestorana;
import com.example.service.restoranImpl.RestoranService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/restoran")
public class RestoranController {

	@Autowired
	private RestoranService restoranService;
	
	private ObjectMapper objectMapper = new ObjectMapper();
	
	@RequestMapping(value="/tipovi", method = RequestMethod.GET, produces = "application/JSON")
	public String tipovi() throws JsonProcessingException{
		List<TipRestorana> lista = new ArrayList<>();
		for(TipRestorana tip : TipRestorana.values()){
			lista.add(tip);
		}
		
		return objectMapper.writeValueAsString(lista);
	}
	
	@RequestMapping(value="/register", method = RequestMethod.POST, produces = "application/JSON", consumes="application/JSON")
	public boolean registracijaRestorana(@RequestBody @Valid RestoranDTO restoran){
		restoranService.create(restoran);
		return true;
	}
	
	
	
}
