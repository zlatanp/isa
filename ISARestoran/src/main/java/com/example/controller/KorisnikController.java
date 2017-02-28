package com.example.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

import java.security.SecureRandom;
import java.math.BigInteger;
import java.nio.file.Files;

import javax.mail.*;
import javax.mail.internet.*;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.beans.korisnici.Gost;
import com.example.beans.korisnici.Korisnik;
import com.example.beans.korisnici.MenadzerRestorana;
import com.example.beans.korisnici.MenadzerSistema;
import com.example.dto.hellpers.ChangePasswordDTO;
import com.example.dto.korisnici.GostDTO;
import com.example.dto.korisnici.KonobarDTO;
import com.example.dto.korisnici.KorisnikDTO;
import com.example.dto.korisnici.KuvarDTO;
import com.example.dto.korisnici.MenadzerDTO;
import com.example.dto.korisnici.MenadzerSistemaDTO;
import com.example.dto.korisnici.SankerDTO;
import com.example.dto.restoran.RestoranDTO;
import com.example.enums.TipKorisnika;
import com.example.enums.TypeEmail;
import com.example.service.GostService;
import com.example.service.KorisnikService;
import com.example.service.korisniciImpl.KonobarService;
import com.example.service.korisniciImpl.KuvarService;
import com.example.service.korisniciImpl.MenadzerService;
import com.example.service.korisniciImpl.MenadzerSistemaService;
import com.example.service.korisniciImpl.SankerService;
import com.example.service.restoranImpl.RestoranService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


@RestController
@RequestMapping("/korisnik")
public class KorisnikController {

	@Autowired
	private KorisnikService korisnikService;

	@Autowired
	private GostService gostService;

	@Autowired
	private MenadzerSistemaService menadzerSistemaService;
	
	@Autowired
	private MenadzerService menadzerService;
	
	@Autowired
	private KonobarService konobarService;
	
	@Autowired
	private KuvarService kuvarService;
	
	@Autowired
	private SankerService sankerService;
	
	@Autowired
	private RestoranService restoranService;
	
	
	private ObjectMapper objectMapper = new ObjectMapper();

//	@RequestMapping(value = "/login", method = { RequestMethod.POST }, produces = MediaType.APPLICATION_JSON_VALUE)
//	public synchronized void login(HttpServletResponse response, @ModelAttribute("email") String email,
//			@ModelAttribute("password") String password) throws IOException {
//		boolean postoji = false;
//		boolean aktiviran = false;
//		boolean nasaoMenSistema = false;
//		boolean nasaoMenRest = false;
//		MenadzerSistemaDTO msDTO = new MenadzerSistemaDTO();
//		MenadzerDTO mrDTO = new MenadzerDTO();
//		Korisnik korisnikKojiSeLoguje = null;
//		System.out.println("loguje se: " + email + " " + password);
//
//		System.out.println();
//		Iterable<Korisnik> listaKorisnika = korisnikService.getAllKorisnici();
//
//		for (Korisnik korisnik : listaKorisnika) {
//			if (korisnik.getEmail().equals(email)) {
//				if (korisnik.getPassword().equals(password)) {
//					System.out.println("imaga");
//					postoji = true;
//					korisnikKojiSeLoguje = korisnik;
//				}
//			}
//		}
//
//		// ovdee menjam
//		// Uzimam tip korisnika koji se loguje i pitam da li je Gost.
//		// Ako jeste gost onda proveravam da li je aktivirao profil preko mejla,
//		// ako jeste onda se loguje uspesno, ako nije onda se ne moze ulogovati
//
//		if (korisnikKojiSeLoguje != null && postoji) {
//			TipKorisnika tip = korisnikKojiSeLoguje.getTipKorisnika();
//			System.out.println("Tip koji se loguje: "+ tip);
//			
//			switch (tip) {
//			case GOST:
//				Iterable<Gost> sviGosti = gostService.getAllGosti();
//				for (Gost item : sviGosti) {
//					if (item.isActivated()) {
//						aktiviran = true;
//					} else {
//						aktiviran = false;
//					}
//				}
//				break;
//			case MENADZERSISTEMA:
//				Iterable<MenadzerSistema> sviMenadzeri = menadzerSistemaService.getAll();
//				for(MenadzerSistema m : sviMenadzeri){
//					if(m.getEmail().equals(korisnikKojiSeLoguje.getEmail()) && 
//							m.getPassword().equals(korisnikKojiSeLoguje.getPassword())){
//						nasaoMenSistema = true;
//						msDTO = new MenadzerSistemaDTO(m);
//					}
//				}
//				break;
//			
//			case MENADZERRESTORANA:
//				Iterable<MenadzerRestorana> sviMenadzeriRest = menadzerService.findAll();
//				for(MenadzerRestorana m : sviMenadzeriRest){
//					if(m.getEmail().equals(korisnikKojiSeLoguje.getEmail()) &&
//							m.getPassword().equals(korisnikKojiSeLoguje.getPassword())){
//						nasaoMenRest = true;
//						mrDTO = new MenadzerDTO(m);
//					}
//				}
//				break;
//				
//			default:
//				break;
//			}
//		}
//
//		if (aktiviran) {
//			response.sendRedirect("/home.html");
//			
//		} else if (nasaoMenSistema){
//			if(msDTO.getIme().equals("admin")){
//				response.sendRedirect("/adminPage.html");
//			}else if(!msDTO.isPromenioLozinku()){
//				response.sendRedirect("/changePassword.html");
//			}else {
//				response.sendRedirect("/adminPage.html");
//			}
//			
//		}else if (nasaoMenRest){
//			if(!mrDTO.isPromenioLozinku()){
//				response.sendRedirect("/changePassword.html");
//			}else {
//				response.sendRedirect("/menadzerPage.html");
//			}
//			
//		} else {
//			response.sendRedirect("/index.html");
//		}
//	}
	
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public void login(){}
	
	public String determineRole(Korisnik korisnik){
		if (korisnik.getTipKorisnika().equals(TipKorisnika.GOST)){
			return "GOST";
		}else if (korisnik.getTipKorisnika().equals(TipKorisnika.MENADZERSISTEMA)){
			return "MENADZERSISTEMA";
		}else if (korisnik.getTipKorisnika().equals(TipKorisnika.MENADZERRESTORANA)){
			return "MENADZERRESTORANA";
		}else if (korisnik.getTipKorisnika().equals(TipKorisnika.KUVAR)){
			return "KUVAR";
		}else if (korisnik.getTipKorisnika().equals(TipKorisnika.KONOBAR)){
			return "KONOBAR";
		}else if (korisnik.getTipKorisnika().equals(TipKorisnika.SANKER)){
			return "SANKER";
		}else if (korisnik.getTipKorisnika().equals(TipKorisnika.PONUDJAC)){
			return "PONUDJAC";
		}
		
		return "";
	}
	
	public void setAuthorAuthen(Korisnik korisnik){
		final java.util.Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(determineRole(korisnik)));
        final Authentication authentication = new PreAuthenticatedAuthenticationToken(korisnik, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        
	}
	
	@RequestMapping(value = "/register", method = { RequestMethod.POST })
	public synchronized void register(HttpServletResponse httpServletResponse,
			@ModelAttribute("nameRegister") String name, @ModelAttribute("lastnameRegister") String lastname,
			@ModelAttribute("emailRegister") String email, @ModelAttribute("passwordRegister") String password,
			@ModelAttribute("password1Register") String passwordRepeat) throws IOException {

		boolean uspesno = false;
		System.out.println(name + lastname + email + password + passwordRepeat);

		// Serverska provera da li su polja prazna
		if (!(name.isEmpty() || lastname.isEmpty() || email.isEmpty() || password.isEmpty()
				|| passwordRepeat.isEmpty())) {
			uspesno = true;

			// Iscitavanje svih korisnika iz baze
			Iterable<Korisnik> listaKorisnika = korisnikService.getAllKorisnici();
			ArrayList<Korisnik> list = new ArrayList<Korisnik>();
			for (Korisnik item : listaKorisnika) {
				list.add(item);
			}

			// Serverska provera da li je korisnik uneo isti pasword
			if (!password.equals(passwordRepeat)) {
				uspesno = false;
			}

			// Provera da li postoji takav email, ako postoji korisnik ne moze
			// da se registruje sa 2 ista emaila
			for (Korisnik korisnik : list) {
				if (korisnik.getEmail().equals(email)) {
					uspesno = false;
				}
			}
		}

		// Ako je prosao sve provere, unesi korisnika u bazu (registrovan je),
		// ako nije prosao sve provere povratak na pocetnu stranu (index)
		if (uspesno) {

			SecureRandom random = new SecureRandom();
			String hashCode = new BigInteger(130, random).toString(32);
					
			Gost gost = new Gost(name, lastname, email, password, TipKorisnika.GOST);
			gost.setHashCode(hashCode);
			
			File fi = new File("src/main/resources/static/html/profilePic.jpg");
			byte[] fileContent = Files.readAllBytes(fi.toPath());
			gost.setSlika(fileContent);
			
			GostDTO gostDTO = new GostDTO(gost);
			sendEmailToNewUser(hashCode, TypeEmail.ACTIVATION, gostDTO);
			
			gostService.saveGost(gost);

			httpServletResponse.sendRedirect("/index.html");
			System.out.println("registrovao");

		} else {
			httpServletResponse.sendRedirect("/index.html");
			System.out.println("skipovao");
		}

	}

	@RequestMapping(value = "/active/{code}", method = RequestMethod.GET)
	public synchronized void activateAccount(HttpServletResponse httpServletResponse, @PathVariable("code") String kod)
			throws IOException {
		
		System.out.println("adjadadsadjasdnwajdnwajd");
		Iterable<Gost> listaGostiju = gostService.getAllGosti();
		Iterable<Korisnik> listakorisnika = korisnikService.getAllKorisnici();
		ArrayList<Gost> list = new ArrayList<Gost>();
		for (Gost item : listaGostiju) {
			list.add(item);
		}
		

		for (Gost gost : list) {
			if (gost.getHashCode() != null) {
				if (gost.getHashCode().equals(kod)) {
					gost.setActivated(true);
					gostService.saveGost(gost);
					for(Korisnik k : listakorisnika){
						if (k.getEmail().equals(gost.getEmail()))
							k.setPromenioLozinku(true);
							korisnikService.saveKorisnik(k);
					}
				}
			}
		}

		httpServletResponse.sendRedirect("/index.html");
	}

	@RequestMapping(value = "/recover", method = RequestMethod.GET)
	public synchronized void recoverPassword(HttpServletResponse httpServletResponse,
			@ModelAttribute("email") String mejl) throws IOException {

		System.out.println(mejl);

		Iterable<Korisnik> listaKorisnika = korisnikService.getAllKorisnici();
		Korisnik zaRecover = new Korisnik();
		for (Korisnik item : listaKorisnika) {
			if (item.getEmail().equals(mejl)) {
				zaRecover = item;
				System.out.println("uso");
			}
		}
		TipKorisnika tipKorisnika = zaRecover.getTipKorisnika();
		switch (tipKorisnika) {
		case GOST:
				Gost g = new Gost(zaRecover.getIme(), zaRecover.getPrezime(), zaRecover.getEmail(), 
										zaRecover.getPassword(), TipKorisnika.GOST);
				GostDTO gostDTO = new GostDTO(g);
				sendEmailToNewUser("", TypeEmail.FORGOT_PASSWORD, gostDTO);
			break;
			
		case MENADZERRESTORANA:
				MenadzerRestorana m = new MenadzerRestorana(zaRecover.getIme(), zaRecover.getPrezime(), zaRecover.getEmail(), 
										zaRecover.getPassword(), TipKorisnika.MENADZERRESTORANA);
				MenadzerDTO menDTO = new MenadzerDTO(m);
				sendEmailToNewUser("", TypeEmail.FORGOT_PASSWORD, menDTO);
			break;
		
		case MENADZERSISTEMA:
				MenadzerSistema ms = new MenadzerSistema(zaRecover.getIme(), zaRecover.getPrezime(), zaRecover.getEmail(), 
										zaRecover.getPassword(), TipKorisnika.MENADZERSISTEMA);
				MenadzerSistemaDTO msDTO = new MenadzerSistemaDTO(ms);
				sendEmailToNewUser("", TypeEmail.FORGOT_PASSWORD, msDTO);
				break;
		default:
			break;
		}
		
		httpServletResponse.sendRedirect("/index.html");
	}
	
	@RequestMapping(value="/registerAdmin", method= RequestMethod.POST, consumes="application/json", produces="application/json")
	public boolean registracijaNovogMenadzeraSistema(@RequestBody @Valid MenadzerSistemaDTO admin) throws IOException{
		boolean ovakavPostoji = false;
		Iterable<Korisnik> sviKorisnici = korisnikService.getAllKorisnici();
		
		for(Korisnik k : sviKorisnici){
			if(k.getEmail().equals(admin.getEmail())){
				ovakavPostoji = true;
			}
		}		
		if(!ovakavPostoji){
			sendEmailToNewUser("", TypeEmail.CHANGE_PASSWORD, admin);
			
			
			menadzerSistemaService.create(admin);
			
			Iterable<Korisnik> sviKorisnici2 = korisnikService.getAllKorisnici();
			for(Korisnik kor : sviKorisnici2){
				if(kor.getEmail().equals(admin.getEmail())){
					File fi = new File("src/main/resources/static/html/admin.jpg");
					byte[] fileContent = Files.readAllBytes(fi.toPath());
					kor.setSlika(fileContent);
					korisnikService.saveKorisnik(kor);
					break;
				}
			}	
			return true;
		}else {
			return false;
		}
	}
	
	@RequestMapping(value="/registerMenadzer", method= RequestMethod.POST, consumes="application/json", produces="application/json")
	public boolean registracijaMenadzera(@RequestBody @Valid MenadzerDTO menadzer) throws IOException{
		KorisnikDTO k = korisnikService.findByEmail(menadzer.email);
		boolean ovakavPostoji = false;
		if(k != null){
			ovakavPostoji = true;
		}
		if(!ovakavPostoji){
			sendEmailToNewUser("", TypeEmail.CHANGE_PASSWORD, menadzer);
			menadzerService.create(menadzer);
			RestoranDTO restoran = restoranService.findById(menadzer.radi_u);
			restoran.getMenadzeri().add(menadzer);
			restoranService.updateRestoran(restoran, menadzer.email);
			
			Iterable<Korisnik> sviKorisnici2 = korisnikService.getAllKorisnici();
			for(Korisnik kor : sviKorisnici2){
				if(kor.getEmail().equals(menadzer.getEmail())){
					File fi = new File("src/main/resources/static/html/menadzer.jpg");
					byte[] fileContent = Files.readAllBytes(fi.toPath());
					kor.setSlika(fileContent);
					korisnikService.saveKorisnik(kor);
					break;
				}
			}
			return true;
		}else {
			return false;
		}
	}
	
	@RequestMapping(value="/registerKonobar", method=RequestMethod.POST, consumes="application/json", produces="application/json")
	public boolean registracijaKonobara(@RequestBody @Valid KonobarDTO konobar) throws IOException{
		KorisnikDTO k = korisnikService.findByEmail(konobar.email);
		boolean postoji = false;
		if(k != null){
			postoji = true;
		}
		if(!postoji){
			sendEmailToNewUser("", TypeEmail.CHANGE_PASSWORD, konobar);
			konobarService.create(konobar);
			Iterable<Korisnik> sviKorisnici2 = korisnikService.getAllKorisnici();
			for(Korisnik kor : sviKorisnici2){
				if(kor.getEmail().equals(konobar.getEmail())){
					File fi = new File("src/main/resources/static/html/konobar.jpg");
					byte[] fileContent = Files.readAllBytes(fi.toPath());
					kor.setSlika(fileContent);
					korisnikService.saveKorisnik(kor);
					break;
				}
			}
			return true;
		}else {
			return false;
		}
	}
	
	@RequestMapping(value="/registerKuvar", method=RequestMethod.POST, consumes="application/json", produces="application/json")
	public boolean registracijaKuvara(@RequestBody @Valid KuvarDTO kuvar) throws IOException{
		KorisnikDTO k = korisnikService.findByEmail(kuvar.email);
		boolean postoji = false;
		if(k != null){
			postoji = true;
		}
		if(!postoji){
			sendEmailToNewUser("", TypeEmail.CHANGE_PASSWORD, kuvar);
			kuvarService.create(kuvar);
			Iterable<Korisnik> sviKorisnici2 = korisnikService.getAllKorisnici();
			for(Korisnik kor : sviKorisnici2){
				if(kor.getEmail().equals(kuvar.getEmail())){
					File fi = new File("src/main/resources/static/html/kuvar.jpg");
					byte[] fileContent = Files.readAllBytes(fi.toPath());
					kor.setSlika(fileContent);
					korisnikService.saveKorisnik(kor);
					break;
				}
			}
			return true;
		}else{
			return false;
		}
	}
	
	@RequestMapping(value="/registerSanker", method=RequestMethod.POST, consumes="application/json", produces="application/json")
	public boolean registracijaKuvara(@RequestBody @Valid SankerDTO sanker) throws IOException{
		KorisnikDTO k = korisnikService.findByEmail(sanker.email);
		boolean postoji = false;
		if(k != null){
			postoji = true;
		}
		if(!postoji){
			sendEmailToNewUser("", TypeEmail.CHANGE_PASSWORD, sanker);
			sankerService.create(sanker);
			Iterable<Korisnik> sviKorisnici2 = korisnikService.getAllKorisnici();
			for(Korisnik kor : sviKorisnici2){
				if(kor.getEmail().equals(sanker.getEmail())){
					File fi = new File("src/main/resources/static/html/konobar.jpg");
					byte[] fileContent = Files.readAllBytes(fi.toPath());
					kor.setSlika(fileContent);
					korisnikService.saveKorisnik(kor);
					break;
				}
			}
			return true;
		}else{
			return false;
		}
	}
	
	
	@RequestMapping(value="/changeFirstPass", method= RequestMethod.POST, consumes="application/json", produces="application/json")
	public String changeFirstPassword(@RequestBody @Valid ChangePasswordDTO noviPass) throws JsonProcessingException{
		KorisnikDTO korisnik = korisnikService.findByEmail(noviPass.getEmail());
		TipKorisnika tipKorisnikaKojiMenja = korisnik.getTip();
		boolean promenioPre = korisnik.isPromenioLozinku();
		if(korisnik != null && !promenioPre){
			KorisnikDTO updateKorisnik = korisnikService.updatePassword(korisnik.getEmail(), noviPass.getStara(), noviPass.getNova());
			updateKorisnik.setTip(tipKorisnikaKojiMenja);
			return objectMapper.writeValueAsString(updateKorisnik);
		}
		return "";	
	}
	
	public void sendEmailToNewUser(String hashcode, TypeEmail emailType, KorisnikDTO korisnik){
		String userEmail = korisnik.getEmail();
		String userName = korisnik.getIme();
		String userPassword = korisnik.getPassword();
		
		String posiljalac = "teamdev70@gmail.com";
		String passwordPosiljaoca = "restoran94";
		String imePosiljaoca = "Restoran";
		String host = "smtp.gmail.com";
		String port = "465";
		String subject = "";
		String text = "";
		
		switch(emailType) {
		
		case ACTIVATION:
			subject = "Activate restaurant account";
			text = "Hi " + userName + ",\t\n\t\nThank you for registering on our website.\t\n"
					+ "To activate your account please go on link: http://localhost:8080/korisnik/active/"
					+ hashcode + "\t\n\t\nBest Regards,\t\nYour Restaurant.";
			
			Properties props = new Properties();
			props.put("mail.smtp.user", posiljalac);
			props.put("mail.smtp.host", host);
			props.put("mail.smtp.port", port);
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.debug", "true");
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.socketFactory.port", port);
			props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			props.put("mail.smtp.socketFactory.fallback", "false");
			Authenticator auth = new SMTPAuthenticator();
			Session session = Session.getInstance(props, auth);
			session.setDebug(true);

			MimeMessage msg = new MimeMessage(session);
			try {
				msg.setSubject(subject);
				msg.setText(text);
				msg.setFrom(new InternetAddress(posiljalac));
				msg.addRecipient(Message.RecipientType.TO, new InternetAddress(userEmail));

				Transport transport = session.getTransport("smtps");
				transport.connect(host, Integer.valueOf(port), imePosiljaoca, passwordPosiljaoca);
				transport.sendMessage(msg, msg.getAllRecipients());
				transport.close();

			} catch (AddressException e) {
				e.printStackTrace();
				return;
			} catch (MessagingException e) {
				e.printStackTrace();
				return;
			}
			break;

		case CHANGE_PASSWORD:
			subject = "Change password";
			text = "Hi " + userName + ",\t\n\t\nYour password is: " + userPassword + ".\t\n"
					+ "To change your password please go to link: http://localhost:8080/changePassword.html "+
					"\t\n\t\nBest Regards,\t\nYour Restaurant.";
			Properties props2 = new Properties();
			props2.put("mail.smtp.user", posiljalac);
			props2.put("mail.smtp.host", host);
			props2.put("mail.smtp.port", port);
			props2.put("mail.smtp.starttls.enable", "true");
			props2.put("mail.smtp.debug", "true");
			props2.put("mail.smtp.auth", "true");
			props2.put("mail.smtp.socketFactory.port", port);
			props2.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			props2.put("mail.smtp.socketFactory.fallback", "false");
			Authenticator auth2 = new SMTPAuthenticator();
			Session session2 = Session.getInstance(props2, auth2);
			session2.setDebug(true);

			MimeMessage msg2 = new MimeMessage(session2);
			try {
				msg2.setSubject(subject);
				msg2.setText(text);
				msg2.setFrom(new InternetAddress(posiljalac));
				msg2.addRecipient(Message.RecipientType.TO, new InternetAddress(userEmail));

				Transport transport = session2.getTransport("smtps");
				transport.connect(host, Integer.valueOf(port), imePosiljaoca, passwordPosiljaoca);
				transport.sendMessage(msg2, msg2.getAllRecipients());
				transport.close();

			} catch (AddressException e) {
				e.printStackTrace();
				return;
			} catch (MessagingException e) {
				e.printStackTrace();
				return;
			}
			
			break;
			
		case FORGOT_PASSWORD:
			subject = "Restaurant Password Recovery";
			text = "Hi " + userName + ",\t\n\t\nYour password is: " + userPassword
					+ "\t\n\t\nBest Regards,\t\nYour Restaurant.";
			Properties props1 = new Properties();
			props1.put("mail.smtp.user", posiljalac);
			props1.put("mail.smtp.host", host);
			props1.put("mail.smtp.port", port);
			props1.put("mail.smtp.starttls.enable", "true");
			props1.put("mail.smtp.debug", "true");
			props1.put("mail.smtp.auth", "true");
			props1.put("mail.smtp.socketFactory.port", port);
			props1.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			props1.put("mail.smtp.socketFactory.fallback", "false");
			Authenticator auth1 = new SMTPAuthenticator();
			Session session1 = Session.getInstance(props1, auth1);
			session1.setDebug(true);

			MimeMessage msg1 = new MimeMessage(session1);
			try {
				msg1.setSubject(subject);
				msg1.setText(text);
				msg1.setFrom(new InternetAddress(posiljalac));
				msg1.addRecipient(Message.RecipientType.TO, new InternetAddress(userEmail));

				Transport transport = session1.getTransport("smtps");
				transport.connect(host, Integer.valueOf(port), imePosiljaoca, passwordPosiljaoca);
				transport.sendMessage(msg1, msg1.getAllRecipients());
				transport.close();

			} catch (AddressException e) {
				e.printStackTrace();
				return;
			} catch (MessagingException e) {
				e.printStackTrace();
				return;
			}
			break;
		}
	}	
}
