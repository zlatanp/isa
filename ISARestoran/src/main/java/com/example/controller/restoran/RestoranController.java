package com.example.controller.restoran;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.validation.Valid;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.beans.korisnici.Gost;
import com.example.beans.korisnici.Korisnik;
import com.example.beans.korisnici.MenadzerRestorana;
import com.example.beans.restoran.Restoran;
import com.example.beans.restoran.Rezervacija;
import com.example.beans.restoran.Sto;
import com.example.beans.restoran.ZakupPomocna;
import com.example.dto.hellpers.CompareStringsDTO;
import com.example.dto.korisnici.KorisnikDTO;
import com.example.dto.korisnici.MenadzerDTO;
import com.example.dto.korisnici.ZaposleniDTO;
import com.example.dto.restoran.JeloDTO;
import com.example.dto.restoran.PiceDTO;
import com.example.dto.restoran.RestoranDTO;
import com.example.dto.restoran.RezervacijaDTO;
import com.example.dto.restoran.StoDTO;
import com.example.enums.TipKorisnika;
import com.example.enums.TipRestorana;
import com.example.service.GostService;
import com.example.service.KorisnikService;
import com.example.service.RestoranMyService;
import com.example.service.RezervacijaService;
import com.example.service.StoMyService;
import com.example.service.korisniciImpl.KonobarService;
import com.example.service.korisniciImpl.ZaposlenService;
import com.example.service.restoranImpl.JeloService;
import com.example.service.restoranImpl.PiceService;
import com.example.service.restoranImpl.RestoranService;
import com.example.service.restoranImpl.StoService;
import com.example.utilities.FileHelper;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.code.geocoder.Geocoder;
import com.google.code.geocoder.GeocoderRequestBuilder;
import com.google.code.geocoder.model.GeocodeResponse;
import com.google.code.geocoder.model.GeocoderGeometry;
import com.google.code.geocoder.model.GeocoderRequest;
import com.google.code.geocoder.model.GeocoderResult;

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
	private StoService stoService;
	
	@Autowired
	private ZaposlenService zaposleniService;
	
	@Autowired
	private KonobarService konobarService;
	
	@Autowired
	private ServletContext servletContext;
	
	@Autowired
	private GostService gostService;
	
	@Autowired
	private RestoranMyService restoranMyService;
	
	@Autowired 
	private RezervacijaService rezervacijaService;
	
	@Autowired
	private StoMyService stoMyService;

	private ObjectMapper objectMapper = new ObjectMapper();

	private static final String restorani_folder = "\\slike\\restorani\\";
	private static final String jela_folder = "\\slike\\jela\\";
	private static final String pica_folder = "\\slike\\pica\\";
	private static int photo_num_restorani, photo_num_pica, photo_num_jela = 1;

	final Geocoder geocoder = new Geocoder();

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

	@RequestMapping(value="/dobaviZaposlene/{email}", method = RequestMethod.GET, produces = "application/json")
	public String getZaposleni(@PathVariable("email") String email) throws JsonProcessingException{
		String realEmail = email + ".com";		
		KorisnikDTO k = korisnikService.findByEmail(realEmail);
		if(k == null){
			return "";
		}
		
		final DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
		objectMapper.setDateFormat(df);
		return objectMapper.writeValueAsString(zaposleniService.nadjiZaposleneZaRestoran(realEmail));
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
	
	@RequestMapping(value="/traziZaposlenog/{id}", method = RequestMethod.POST, consumes="application/json", produces="application/json")
	public String traziZaposlenog(@RequestBody @Valid CompareStringsDTO zaTrazenje, @PathVariable("id") int idRestorana) throws JsonProcessingException{
		String[] delovi = zaTrazenje.getVal1().split(" ");
		String retVal = "";
		List<ZaposleniDTO> zaposleni = new ArrayList<ZaposleniDTO>();	
		if(delovi.length == 0){
			retVal = "";
		}else if(delovi.length == 1){
			String prva = delovi[0];
			String druga = "";
			zaposleni = zaposleniService.findByParameters(prva, druga, idRestorana);
			retVal = objectMapper.writeValueAsString(zaposleni);
		}else if(delovi.length == 2){
			String prva = delovi[0];
			String druga = delovi[1];
			zaposleni = zaposleniService.findByParameters(prva, druga, idRestorana);
			retVal = objectMapper.writeValueAsString(zaposleni);
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


	@RequestMapping(value = "/sortPoUdaljenosti", method = {
			RequestMethod.GET }, produces = MediaType.APPLICATION_JSON_VALUE)
	public synchronized ArrayList<RestoranDTO> sortPoUdaljenosti(@RequestParam("grad") String grad) {
		List<RestoranDTO> r = restoranService.findAll();
		ArrayList<RestoranDTO> restoranizaVratiti = new ArrayList<RestoranDTO>();

		ArrayList<Double> koordinateMoje = new ArrayList<Double>();
		ArrayList<Double> koordinateRestorana = new ArrayList<Double>();
		Map<RestoranDTO, Double> mapaSaRastojanjima = new HashMap<RestoranDTO, Double>();
		
		Map<RestoranDTO, Double> result = new LinkedHashMap<>();
		
		if (grad.isEmpty()) {

			return restoranizaVratiti;
		} else {
			koordinateMoje = koordinate(grad);

			for (RestoranDTO item : r) {
				String restoranGrad = item.getGrad();
				koordinateRestorana = koordinate(restoranGrad);
				double rastojanje = calculateDistance(koordinateRestorana.get(0), koordinateRestorana.get(1),
						koordinateMoje.get(0), koordinateMoje.get(1));
				mapaSaRastojanjima.put(item, rastojanje);
			}

			if (mapaSaRastojanjima.size() > 0) {

				result = sortByValue(mapaSaRastojanjima);
				
			}
			
			for(RestoranDTO rr : result.keySet()){
				restoranizaVratiti.add(rr);
			}

			return restoranizaVratiti;
		}

	}

	private ArrayList<Double> koordinate(String grad) {
		ArrayList<Double> lista = new ArrayList<Double>();
		if (grad != null) {
			GeocoderRequest geocoderRequest = new GeocoderRequestBuilder().setAddress(grad.toString()).setLanguage("en").getGeocoderRequest();
			GeocodeResponse geocoderResponse = geocoder.geocode(geocoderRequest);
			List<GeocoderResult> someList = geocoderResponse.getResults();
			GeocoderResult data = someList.get(0);
			GeocoderGeometry data_2 = data.getGeometry();
			BigDecimal Latitude = data_2.getLocation().getLat();
			BigDecimal Longitude = data_2.getLocation().getLng();

			lista.add(Latitude.doubleValue());
			lista.add(Longitude.doubleValue());

		}
		return lista;
	}

	private double calculateDistance(double posALat, double posALon, double posBLat, double posBLon) {
		int earthRadius = 6371;
		
		double lat = posBLat - posALat; // Difference of latitude
		double lon = posBLon - posALon; // Difference of longitude

		double disLat = (lat * Math.PI * earthRadius) / 180; // Vertical
																// distance
		double disLon = (lon * Math.PI * earthRadius) / 180; // Horizontal
																// distance

		double ret = Math.pow(disLat, 2) + Math.pow(disLon, 2);
		ret = Math.sqrt(ret); // Total distance (calculated by Pythagore: a^2 +
								// b^2 = c^2)

		return ret;
	}
	
	private static <K, V> Map<RestoranDTO, Double> sortByValue(Map<RestoranDTO, Double> map) {
	    List<Entry<RestoranDTO, Double>> list = new LinkedList<>(map.entrySet());
	    Collections.sort(list, new Comparator<Object>() {
	        @SuppressWarnings("unchecked")
	        public int compare(Object o1, Object o2) {
	            return ((Comparable<Double>) ((Map.Entry<RestoranDTO, Double>) (o1)).getValue()).compareTo(((Map.Entry<RestoranDTO, Double>) (o2)).getValue());
	        }
	    });

	    Map<RestoranDTO, Double> result = new LinkedHashMap<>();
	    for (Iterator<Entry<RestoranDTO, Double>> it = list.iterator(); it.hasNext();) {
	        Map.Entry<RestoranDTO, Double> entry = (Map.Entry<RestoranDTO, Double>) it.next();
	        result.put(entry.getKey(), entry.getValue());
	    }

	    return result;
	}
	

	@RequestMapping(value = "/dajRestoranSaId", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String dobaviRestoranID(@RequestParam("id") int idRestorana) throws JsonProcessingException {
		
		return objectMapper.writeValueAsString(restoranService.findById(idRestorana));
	}

	@RequestMapping(value="/getCanvas/{email}", method = RequestMethod.GET )
	public String getCanvas(@PathVariable("email") String email){
		String realEmail = email + ".com";
		KorisnikDTO k = korisnikService.findByEmail(realEmail);
		String raspored = restoranService.getRaspored(k);	
		return raspored;
	}

	
	@RequestMapping(value="/getCanvas/{email}/{id}", method=RequestMethod.GET)
	public String getCanvasById(@PathVariable("email") String emailUser, @PathVariable("id") int restId) throws JsonProcessingException{
		String realEmail = emailUser + ".com";
		KorisnikDTO korisnik = korisnikService.findByEmail(realEmail);
		if (korisnik == null)
			return "";
		return restoranService.getRasporedById(restId);
	}
	
	@RequestMapping(value="/dobaviKanvasZaHome/{id}", method=RequestMethod.GET)
	public String getCanvasByIdHome(@PathVariable("id") int restId) throws JsonProcessingException{
		if( restoranService.getRasporedById(restId)!= null){
			return restoranService.getRasporedById(restId);
		}else{
			return "nema";
		}
	}
	
	@RequestMapping(value="/saveCanvas/{email}",method=RequestMethod.POST, consumes="application/json") 
	public boolean sacuvajRaspored(@PathVariable("email") String emailUser, @RequestBody String canvasJSON){
		String real = emailUser + ".com";
		return restoranService.update(canvasJSON, real);
	}
	
	@RequestMapping(value="/getTable",method = RequestMethod.POST)
	public String getTable(@RequestBody String naziv) throws JsonParseException, JsonMappingException, IOException{
		naziv = naziv.substring(0, naziv.length()-1);
		StoDTO sto = stoService.findByName(naziv);
		if(sto!=null){
			return objectMapper.writeValueAsString(sto);
		}
		else{
			return objectMapper.writeValueAsString("");
		}
	}
	
	@RequestMapping(value="/saveTable/{email}",method = RequestMethod.POST, consumes="application/json", produces="application/json")
	public boolean sacuvajStolove(@RequestBody List<StoDTO> stolovi, @PathVariable("email") String emailUser){
		String realEmail = emailUser + ".com";
		for(int i=0; i<stolovi.size(); i++){
			stoService.create(stolovi.get(i), realEmail);
		}
		return true;
	}
	
	@RequestMapping(value="/oceneKonobara/{email:.+}", method=RequestMethod.GET, produces = "application/json")
	public String getOceneKonobara(@PathVariable("email") String email) throws JsonProcessingException{
		return objectMapper.writeValueAsString(konobarService.dobaviOceneZaKonobara(email));
	}
	
	@RequestMapping(value = "/rezervacije/proveriSto", method = { RequestMethod.GET }, consumes="application/json", produces="application/json")
	public boolean proveraRezervisanogStola(@ModelAttribute("imeStola") String imeStola, @ModelAttribute("datum") String datum, @ModelAttribute("sati") String sati) throws JsonProcessingException{
		Iterable<Rezervacija> rezervacje = rezervacijaService.getAllRezervacije();
		
		System.out.println(imeStola);
		 Date date = null;
			DateFormat readFormat = new SimpleDateFormat( "MM/dd/yyyy h:mm a");
			try {
			       date = readFormat.parse(datum);
			    } catch ( ParseException e ) {
			        e.printStackTrace();
			    }
			
			
		
		boolean flag = false;
		Date mojOd = date;
		System.out.println("odkad sam tamo : "+mojOd.toString());
		Calendar konverter = Calendar.getInstance(); 
		konverter.setTime(mojOd); 
		konverter.add(Calendar.HOUR, Integer.parseInt(sati));
		Date mojDo = konverter.getTime();
		System.out.println("dokad sam tamo : "+mojDo.toString());
		for(Rezervacija r : rezervacje){
			System.out.print("petlja jelima tu stola uopste bre!?");
			System.out.println(r.getStolovi().size());
			if(r.getStolovi().size()>0){
				System.out.print("uso jelima tu stola uopste bre!?");
				for(Iterator<Sto> it = r.getStolovi().iterator(); it.hasNext(); ){
					Sto s = it.next();
					if(s.getNaziv().equals(imeStola)){
						Date stoOd = r.getDatum();
						Calendar konverter2 = Calendar.getInstance(); 
						konverter2.setTime(stoOd); 
						konverter2.add(Calendar.HOUR, r.getTrajanje());
						Date postojeciDo = konverter.getTime();
						if (!porediDatume(mojOd, mojDo, r.getDatum(), postojeciDo))
							flag =  true;
					}
				}
			}
		}
		
		return flag;
	}
	
	@RequestMapping(value = "/rezervacije/dodajRezervacijuBezPrijatelja", method = RequestMethod.POST, consumes="application/json", produces="application/json")
	public boolean rezervacijaBezPrijatelja(@RequestBody ZakupPomocna stolovi) throws JsonProcessingException, ParseException{
		System.out.println(stolovi.email);
		System.out.println(stolovi.datum);
		System.out.println(stolovi.sati);
		System.out.println(stolovi.idRestorana);
		System.out.println(stolovi.getListaStolova().size());
		
		Iterable<Sto> svistolovi = stoMyService.getAllStolovi();
		
		Set<Sto> listaStolovaZaUpis = new HashSet<Sto>();
		int brojStolica = 0;
		
		
		for(Sto s : svistolovi){
			for(int i=0;i<stolovi.getListaStolova().size();i++){
				if(s.getNaziv().equals(stolovi.getListaStolova().get(i).getNaziv())){
					System.out.println("woohoo");
					listaStolovaZaUpis.add(s);
					brojStolica += s.getBrojStolica();
				}
			}
			
		}
		
		 Date date = null;
		DateFormat readFormat = new SimpleDateFormat( "MM/dd/yyyy h:mm a");
		try {
		       date = readFormat.parse( stolovi.datum );
		    } catch ( ParseException e ) {
		        e.printStackTrace();
		    }
		
		
		System.out.println(date.toString());
		
		String emailZakupca = stolovi.email;
		Gost g = new Gost();
		Iterable<Gost> gosti = gostService.getAllGosti();
		for(Gost kk : gosti){
			if(kk.getEmail().equals(emailZakupca)){
				g = kk;
			}
		}
		
		int trajanje = Integer.parseInt(stolovi.sati);
		
		Restoran restt = restoranMyService.getRestoranById(Integer.parseInt(stolovi.getIdRestorana()));
		
		Rezervacija r = new Rezervacija();
		r.setDatum(date);
		r.setTrajanje(trajanje);
		r.setGost(g);
		r.setStolovi(null);
		r.setRestoran(restt);
		r.setBroj_stolica(brojStolica);
		
		rezervacijaService.saveRezervacija(r);
		
		
		
		return true;
		
		
	}
	

	@RequestMapping(value = "/rezervacije/dajPosete", method = { RequestMethod.GET })
	public String dajPosete(@RequestParam("mojEmail") String mojEmail) throws JsonProcessingException, ParseException{
		
		 ArrayList<Rezervacija> zavratiti = new ArrayList<Rezervacija>();
		Iterable<Rezervacija> sve = rezervacijaService.getAllRezervacije();
		Iterable<Gost> svigosti = gostService.getAllGosti();
		
		JSONObject json = new JSONObject();
		JSONArray array = new JSONArray();
		
		Gost g = null;
		for(Gost gg : svigosti){
			if(gg.getEmail().equals(mojEmail)){
				System.out.println("nasaoga");
				g =gg;
				break;
			}
		}
		
		for(Rezervacija r : sve){
			if(r.getGost().getId() == g.getId()){
				System.out.println(g.getId());
				zavratiti.add(r);
				JSONObject item = new JSONObject();
				item.put("brojStolica", r.getBroj_stolica());
				item.put("datum", r.getDatum().toString());
				item.put("zadrzavanje", r.getTrajanje());
				item.put("restoran", r.getRestoran().getNaziv());
				array.put(item);
		}
			}
		
		json.put("lista", array);
		return json.toString();
	}

	private boolean porediDatume(Date mojOD, Date mojDo, Date stoOd, Date stoDo){
		if (stoOd.compareTo(mojOD) < 0 && stoDo.compareTo(mojOD) <= 0)
			return true;
		if (stoOd.compareTo(mojDo) >= 0 && stoDo.compareTo(mojDo) > 0)
			return true;
		return false;
	}
}
