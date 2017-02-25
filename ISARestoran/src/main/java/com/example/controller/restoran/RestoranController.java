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


import com.example.dto.hellpers.CompareStringsDTO;
import com.example.dto.korisnici.KorisnikDTO;
import com.example.dto.korisnici.MenadzerDTO;
import com.example.dto.restoran.JeloDTO;
import com.example.dto.restoran.PiceDTO;
import com.example.dto.restoran.RestoranDTO;
import com.example.enums.TipKorisnika;
import com.example.enums.TipRestorana;
import com.example.service.KorisnikService;
import com.example.service.restoranImpl.JeloService;
import com.example.service.restoranImpl.PiceService;
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
	private PiceService piceService;
	
	@Autowired
	private ServletContext servletContext;

	private ObjectMapper objectMapper = new ObjectMapper();

	private static final String restorani_folder = "\\slike\\restorani\\";
	private static final String jela_folder = "\\slike\\jela\\";
	private static final String pica_folder = "\\slike\\pica\\";
	private static int photo_num_restorani, photo_num_pica, photo_num_jela = 1;

	// odmah posle dependency-injectiona se izvrsava
	// za mogucnost uploada fajla
	@PostConstruct
	private void init() {
		int max = FileHelper.getMaxFileName(servletContext.getRealPath("") + restorani_folder);
		if (max > photo_num_restorani) {
			photo_num_restorani = max;
		}
		photo_num_restorani++;

		max = FileHelper.getMaxFileName(servletContext.getRealPath("") + jela_folder);
		String stajeovo = servletContext.getRealPath("") + jela_folder;
		System.out.println("kontekst: " + servletContext.getContextPath());
		System.out.println("sta je ovo:s " + stajeovo);

		if (max > photo_num_jela) {
			photo_num_jela = max;
		}
		photo_num_jela++;

		max = FileHelper.getMaxFileName(servletContext.getRealPath("") + pica_folder);
		if (max > photo_num_pica) {
			photo_num_pica = max;
		}
		photo_num_pica++;
	}

	@RequestMapping(value = "/tipovi", method = RequestMethod.GET, produces = "application/JSON")
	public String tipovi() throws JsonProcessingException {
		List<TipRestorana> lista = new ArrayList<>();
		for (TipRestorana tip : TipRestorana.values()) {
			lista.add(tip);
		}

		return objectMapper.writeValueAsString(lista);
	}

	@RequestMapping(value = "/dobaviRestorane", method = RequestMethod.GET, produces = "application/JSON")
	public String dobaviRestorane() throws JsonProcessingException {
		return objectMapper.writeValueAsString(restoranService.findAll());
	}

	@RequestMapping(value = "/dobaviRestoran", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String dobaviRestoran(@RequestParam("email") String emailMenadzeraRestorana) throws JsonProcessingException {
		MenadzerDTO menadzer = (MenadzerDTO) korisnikService.findByEmail(emailMenadzeraRestorana);
		if (menadzer == null)
			return null;
		int idRestorana = menadzer.getRadi_u();
		return objectMapper.writeValueAsString(restoranService.findById(idRestorana));
	}

	@RequestMapping(value = "/updateRestoran/{email}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public boolean updateRestoran(@RequestBody @Valid RestoranDTO restoran, @PathVariable("email") String email)
			throws JsonProcessingException {
		String realEmail = email + ".com";
		return restoranService.updateRestoran(restoran, realEmail);
	}

	@RequestMapping(value = "/restoranProfil/{id}", method = RequestMethod.GET, produces = "application/json")
	public String getRestoran(@PathVariable("id") int id) throws JsonProcessingException {
		return objectMapper.writeValueAsString(restoranService.findById(id));
	}

	@RequestMapping(value = "/getJela/{id}", method = RequestMethod.GET, produces = "application/json")
	public String getJela(@PathVariable("id") int idRestorana) throws JsonProcessingException {
		return objectMapper.writeValueAsString(jeloService.findAll(idRestorana));
	}

	
	@RequestMapping(value="/getPica/{id}", method= RequestMethod.GET, produces = "application/json")
	public String getPica(@PathVariable("id") int idRestorana) throws JsonProcessingException{
		return objectMapper.writeValueAsString(piceService.findAll(idRestorana));
	}
	
	@RequestMapping(value="/getJelo", method= RequestMethod.POST, consumes="application/json", produces = "application/json")
	public String getJelo(@RequestBody @Valid JeloDTO jelo) throws JsonProcessingException{
		return objectMapper.writeValueAsString(jeloService.findById(jelo.id));
	}
	
	@RequestMapping(value="/getPice", method= RequestMethod.POST, consumes="application/json", produces = "application/json")
	public String getPice(@RequestBody @Valid PiceDTO pice) throws JsonProcessingException{
		return objectMapper.writeValueAsString(piceService.findById(pice.id));
	}
	
	@RequestMapping(value="/obrisiJelo", method = RequestMethod.POST, consumes="application/json")
	public boolean obrisiJelo(@RequestBody @Valid JeloDTO jelo){
		return jeloService.delete(jelo);
	}
	
	@RequestMapping(value="/obrisiPice", method = RequestMethod.POST, consumes="application/json")
	public boolean obrisiPice(@RequestBody @Valid PiceDTO pice){
		return piceService.delete(pice);
	}
	
	@RequestMapping(value="/register", method = RequestMethod.POST)
	public boolean dodajRestoran(@RequestParam(value="uploadfile", required=false) MultipartFile uploadfile, @RequestParam(value="restoran") String restoranJSON) throws JsonParseException, JsonMappingException, IOException{
		RestoranDTO r = objectMapper.readValue(restoranJSON, RestoranDTO.class);
		RestoranDTO zaBazu = restoranService.create(r);
		if(zaBazu == null){
			System.out.println("ovde 1");
			return false;
		}
		if(!r.slika.equals("")){
			if(!(sacuvajSlikuRestoran(zaBazu.id, uploadfile).equals(""))){ 
				System.out.println("ovde 2");
				return true;
			}else {		
				System.out.println("ovde 3");
				return false;
			}
		}
		return true;		
	}


	@RequestMapping(value = "/dodajJelo/{email}", method = RequestMethod.POST)
	public boolean dodajJelo(@PathVariable("email") String managerEmail,
			@RequestParam(value = "uploadfile", required = false) MultipartFile uploadfile,
			@RequestParam(value = "jelo") String jeloJSON)
			throws JsonParseException, JsonMappingException, IOException {

		String realEmail = managerEmail + ".com";
		KorisnikDTO k = korisnikService.findByEmail(realEmail);
		if (k.tip != TipKorisnika.MENADZERRESTORANA || k == null) {
			return false;
		}
		JeloDTO jelo = objectMapper.readValue(jeloJSON, JeloDTO.class);
		JeloDTO zaBazu = jeloService.create(jelo, realEmail);
		if (zaBazu == null) {
			return false;
		}
		if (!jelo.slika.equals("")) {
			if (!(sacuvajSliku(zaBazu.id, uploadfile).equals(""))) { // ako je
																		// uspesno
																		// sacuvao
																		// sliku
				return true;
			} else {
				return false;
			}
		}
		return true;
	}

	
	@RequestMapping(value="/dodajPice/{email}", method = RequestMethod.POST)
	public boolean dodajPice(@PathVariable("email") String emailManager, @RequestParam(value="uploadfile", required=false) MultipartFile uploadFile, @RequestParam(value="pice") String piceJSON) throws JsonParseException, JsonMappingException, IOException{
		String realEmail = emailManager + ".com";
		KorisnikDTO k = korisnikService.findByEmail(realEmail);
		if(k.tip != TipKorisnika.MENADZERRESTORANA || k == null){
			return false;
		}
		PiceDTO pice = objectMapper.readValue(piceJSON, PiceDTO.class);
		PiceDTO zaBazu = piceService.create(pice, realEmail);
		if(zaBazu == null){
			return false;
		}			
		if(!pice.slika.equals("")){
			if(!(sacuvajSlikuPica(zaBazu.id, uploadFile).equals(""))){ // ako je uspesno sacuvao sliku
				return true;
			}else {				
				return false;
			}
		}
		return true;
	}
	

	@RequestMapping(value = "/izmeniJelo", method = RequestMethod.POST)
	public boolean izmeniJelo(@RequestParam(value = "uploadfile", required = false) MultipartFile uploadfile,
			@RequestParam(value = "jelo") String jeloJSON)
			throws JsonParseException, JsonMappingException, IOException {
		JeloDTO jelo = objectMapper.readValue(jeloJSON, JeloDTO.class);
		JeloDTO zaBazu = jeloService.update(jelo);
		if (!jelo.slika.equals("")) {
			if (!(sacuvajSliku(zaBazu.id, uploadfile).equals(""))) {
				return true;
			} else {
				return false;
			}
		}
		return true;
	}

	
	@RequestMapping(value="/izmeniPice", method = RequestMethod.POST)
	public boolean izmeniPice(@RequestParam(value="uploadfile", required= false) MultipartFile uploadFIle, @RequestParam(value="pice") String piceJSON) throws JsonParseException, JsonMappingException, IOException{
		PiceDTO pice = objectMapper.readValue(piceJSON, PiceDTO.class);
		PiceDTO zaBazu = piceService.update(pice);			
		if(!pice.slika.equals("")){
			if(!(sacuvajSlikuPica(zaBazu.id, uploadFIle).equals(""))){
				return true;
			}else {				
				return false;
			}
		}
		return true;
	}
	


	public String sacuvajSliku(int id, MultipartFile uploadSlika) {
		JeloDTO jelo = jeloService.findById(id);
		if (jelo == null) {
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
	
	public String sacuvajSlikuPica(int id, MultipartFile uploadSlika){
		PiceDTO pice = piceService.findById(id);
		if(pice == null){
			return ""; // vrati prazno ako ga nisi nasao
		}
		try {
			byte[] bytes = uploadSlika.getBytes();
			String extenzija = uploadSlika.getOriginalFilename().split("\\.")[1];
			String ime = pica_folder + photo_num_pica + "." + extenzija;
			String saPutanjom = servletContext.getRealPath("") + ime;
			File file = new File(saPutanjom);
			BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(file));
            stream.write(bytes);
            stream.close();
            String ret = photo_num_pica + "." + extenzija;
            piceService.namestiSliku(id, ret);
            photo_num_pica++;
            return objectMapper.writeValueAsString(ret);		
		} catch (IOException e) {
			e.printStackTrace();
			return "";
		}
	}
	
	public String sacuvajSlikuRestoran(int id, MultipartFile uploadSlika){
		RestoranDTO restoran = restoranService.findById(id);
		if(restoran == null){
			return "";
		}
		try {
			byte[] bytes = uploadSlika.getBytes();
			String extenzija = uploadSlika.getOriginalFilename().split("\\.")[1];
			String ime = restorani_folder + photo_num_restorani + "." + extenzija;
			String saPutanjom = servletContext.getRealPath("") + ime;
			File file = new File(saPutanjom);
			BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(file));
            stream.write(bytes);
            stream.close();
            String ret = photo_num_restorani + "." + extenzija;
            restoranService.namestiSliku(id, ret);
            photo_num_restorani++;
            return objectMapper.writeValueAsString(ret);		
		} catch (IOException e) {
			e.printStackTrace();
			return "";
		}
	}
	
	
	@RequestMapping(value = "/traziJelo/{id}", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public String traziJelo(@RequestBody @Valid CompareStringsDTO zaTrazenje, @PathVariable("id") int idRestorana)
			throws JsonProcessingException {
		String[] delovi = zaTrazenje.getVal1().split(" ");
		String retVal = "";
		List<JeloDTO> jela = new ArrayList<JeloDTO>();
		if (delovi.length == 0) {
			retVal = "";
		} else if (delovi.length == 1) {
			String prva = delovi[0];
			String druga = "";
			jela = jeloService.findByParameters(prva, druga, idRestorana);
			retVal = objectMapper.writeValueAsString(jela);
		} else if (delovi.length == 2) {
			String prva = delovi[0];
			String druga = delovi[1];
			jela = jeloService.findByParameters(prva, druga, idRestorana);
			retVal = objectMapper.writeValueAsString(jela);
		}
		return retVal;
	}

	
	@RequestMapping(value="/traziPice/{id}", method = RequestMethod.POST, consumes="application/json", produces="application/json")
	public String traziPice(@RequestBody @Valid CompareStringsDTO zaTrazenje, @PathVariable("id") int idRestorana) throws JsonProcessingException{
		String[] delovi = zaTrazenje.getVal1().split(" ");
		String retVal = "";
		List<PiceDTO> pica = new ArrayList<PiceDTO>();	
		if(delovi.length == 0){
			retVal = "";
		}else if(delovi.length == 1){
			String prva = delovi[0];
			String druga = "";
			pica = piceService.findByParameters(prva, druga, idRestorana);
			retVal = objectMapper.writeValueAsString(pica);
		}else if(delovi.length == 2){
			String prva = delovi[0];
			String druga = delovi[1];
			pica = piceService.findByParameters(prva, druga, idRestorana);
			retVal = objectMapper.writeValueAsString(pica);
		}
		return retVal;
	}

	@RequestMapping(value = "/sortirajrestoranepoimenu", method = {
			RequestMethod.GET }, produces = MediaType.APPLICATION_JSON_VALUE)
	public synchronized List<RestoranDTO> sortRestoranaPoImenu() {
		List<RestoranDTO> restoraniSortirani = new ArrayList<RestoranDTO>();

		List<RestoranDTO> r = restoranService.findAll();

		ArrayList<String> svaslova = new ArrayList<String>();
		svaslova.add("A");
		svaslova.add("a");
		svaslova.add("B");
		svaslova.add("b");
		svaslova.add("C");
		svaslova.add("c");
		svaslova.add("D");
		svaslova.add("d");
		svaslova.add("E");
		svaslova.add("e");
		svaslova.add("F");
		svaslova.add("f");
		svaslova.add("G");
		svaslova.add("g");
		svaslova.add("H");
		svaslova.add("h");
		svaslova.add("I");
		svaslova.add("i");
		svaslova.add("J");
		svaslova.add("j");
		svaslova.add("K");
		svaslova.add("k");
		svaslova.add("L");
		svaslova.add("l");
		svaslova.add("M");
		svaslova.add("m");
		svaslova.add("N");
		svaslova.add("n");
		svaslova.add("O");
		svaslova.add("o");
		svaslova.add("P");
		svaslova.add("p");
		svaslova.add("Q");
		svaslova.add("q");
		svaslova.add("R");
		svaslova.add("r");
		svaslova.add("S");
		svaslova.add("s");
		svaslova.add("T");
		svaslova.add("t");
		svaslova.add("U");
		svaslova.add("u");
		svaslova.add("V");
		svaslova.add("v");
		svaslova.add("W");
		svaslova.add("w");
		svaslova.add("X");
		svaslova.add("x");
		svaslova.add("Y");
		svaslova.add("y");
		svaslova.add("Z");
		svaslova.add("z");

		for (int j = 0; j < svaslova.size(); j += 2) {
			for (int i = 0; i < r.size(); i++) {
				if (r.get(i).getNaziv().startsWith(svaslova.get(j))) {
					restoraniSortirani.add(r.get(i));

				}
			}
		}

		return restoraniSortirani;

	}

	@RequestMapping(value = "/trazirestoran", method = {
			RequestMethod.GET }, produces = MediaType.APPLICATION_JSON_VALUE)
	public synchronized List<RestoranDTO> traziRestoranPoImenu(@RequestParam("tekst") String tekst) {
		List<RestoranDTO> r = restoranService.findAll();
		List<RestoranDTO> restoranizaVratiti = new ArrayList<RestoranDTO>();
		String realText = tekst.toLowerCase();

		if (tekst.isEmpty()) {
			return r;
		} else {
			for (RestoranDTO item : r) {

				if (item.getNaziv().toLowerCase().contains(realText)) {
					restoranizaVratiti.add(item);
				}
			}

			return restoranizaVratiti;
		}

	}
}
