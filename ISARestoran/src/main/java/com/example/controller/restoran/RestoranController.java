package com.example.controller.restoran;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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
import org.springframework.web.multipart.MultipartFile;

import com.example.dto.korisnici.KorisnikDTO;
import com.example.dto.korisnici.MenadzerDTO;
import com.example.dto.restoran.JeloDTO;
import com.example.dto.restoran.RestoranDTO;
import com.example.enums.TipKorisnika;
import com.example.enums.TipRestorana;
import com.example.service.KorisnikService;
import com.example.service.restoranImpl.JeloService;
import com.example.service.restoranImpl.RestoranService;
import com.example.utilities.FileHelper;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
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
	
	@RequestMapping(value="/dodajJelo/{email}", method = RequestMethod.POST)
	public boolean dodajJelo(@PathVariable("email") String managerEmail, @RequestParam(value="uploadfile", required=false) MultipartFile uploadfile, @RequestParam(value= "jelo") String jeloJSON) throws JsonParseException, JsonMappingException, IOException{
		
		String realEmail = managerEmail + ".com";
		System.out.println("ovde1");
		KorisnikDTO k = korisnikService.findByEmail(realEmail);
		if(k.tip != TipKorisnika.MENADZERRESTORANA || k == null){
			System.out.println("ovde2");
			return false;
		}
		JeloDTO jelo = objectMapper.readValue(jeloJSON, JeloDTO.class);
		JeloDTO zaBazu = jeloService.create(jelo, realEmail);
		if(zaBazu == null){
			System.out.println("ovde3");
			return false;
		}			
		if(!jelo.slika.equals("")){
			if(!(sacuvajSliku(zaBazu.id, uploadfile).equals(""))){ // ako je uspesno sacuvao sliku
				return true;
			}else {
				System.out.println("ovde4");
				return false;
			}
		}
		return true;		
	}
	
	public String sacuvajSliku(int id, MultipartFile uploadSlika){
		JeloDTO jelo = jeloService.findById(id);
		if(jelo == null){
			return ""; // vrati prazno ako ga nisi nasao
		}
		try {
			byte[] bytes = uploadSlika.getBytes();
			String extenzija = uploadSlika.getOriginalFilename().split("\\.")[1];
			String ime = jela_folder + photo_num_jela + "." + extenzija;
			String saPutanjom = servletContext.getRealPath("") + ime;
			File file = new File(saPutanjom);
			BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(file));
            stream.write(bytes);
            stream.close();
            String ret = photo_num_jela + "." + extenzija;
            jeloService.namestiSliku(id, ret);
            photo_num_jela++;
            return objectMapper.writeValueAsString(ret);		
		} catch (IOException e) {
			e.printStackTrace();
			return "";
		}
	}
	
}
