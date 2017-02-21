package com.example.controller.restoran;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.korisnici.MenadzerDTO;
import com.example.dto.restoran.RestoranDTO;
import com.example.enums.TipRestorana;
import com.example.service.KorisnikService;
import com.example.service.restoranImpl.JeloService;
import com.example.service.restoranImpl.RestoranService;
import com.example.utilities.FileHelper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/restoran")
public class RestoranController {

	@Autowired
	private RestoranService restoranService;
	
	@Autowired
	private KorisnikService korisnikService;
	
	@Autowired
	private JeloService jeloService;
	
	@Autowired
	private ServletContext servletContext;
	
	private ObjectMapper objectMapper = new ObjectMapper();
	
	private static final String restorani_folder = "\\slike\\restorani\\";
	private static final String jela_folder = "\\slike\\jela\\";
	private static final String pica_folder = "\\slike\\pica\\";
	private static int photo_num_restorani, photo_num_pica, photo_num_jela = 1;
	
	
	//odmah posle dependency-injectiona se izvrsava
	//za mogucnost uploada fajla
	@PostConstruct
	private void init(){
		int max = FileHelper.getMaxFileName(servletContext.getRealPath("")+ restorani_folder);
		if(max > photo_num_restorani){
			photo_num_restorani = max;
		}
		photo_num_restorani++;
		
		max = FileHelper.getMaxFileName(servletContext.getRealPath("")+ jela_folder);
		if(max > photo_num_jela){
			photo_num_jela = max;
		}
		photo_num_jela++;
		
		max = FileHelper.getMaxFileName(servletContext.getRealPath("")+ pica_folder);
		if(max > photo_num_pica){
			photo_num_pica = max;
		}
		photo_num_pica++;
	}
	
	
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
	
	@RequestMapping(value="/dobaviRestorane", method = RequestMethod.GET, produces="application/JSON")
	public String dobaviRestorane() throws JsonProcessingException{
		return objectMapper.writeValueAsString(restoranService.findAll());
	}
	
	@RequestMapping(value="/dobaviRestoran", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String dobaviRestoran(@RequestParam("email") String emailMenadzeraRestorana) throws JsonProcessingException{
		MenadzerDTO menadzer = (MenadzerDTO)korisnikService.findByEmail(emailMenadzeraRestorana);
		if(menadzer == null)
			return null;
		int idRestorana = menadzer.getRadi_u();
		return objectMapper.writeValueAsString(restoranService.findById(idRestorana));
	}
	
	@RequestMapping(value="/updateRestoran/{email}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public boolean updateRestoran(@RequestBody @Valid RestoranDTO restoran, @PathVariable("email") String email) throws JsonProcessingException{
		String realEmail = email + ".com";
		return restoranService.updateRestoran(restoran, realEmail);	
	}
	
	@RequestMapping(value="/restoranProfil/{id}", method= RequestMethod.GET, produces = "application/json")
	public String getRestoran(@PathVariable("id") int id) throws JsonProcessingException{
		return objectMapper.writeValueAsString(restoranService.findById(id));
	}
	
	@RequestMapping(value="/getJela/{id}", method= RequestMethod.GET, produces = "application/json")
	public String getJela(@PathVariable("id") int idRestorana) throws JsonProcessingException{
		return objectMapper.writeValueAsString(jeloService.findAll(idRestorana));
	}
	
}
