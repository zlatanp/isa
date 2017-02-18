package com.example.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

import java.security.SecureRandom;
import java.math.BigInteger;

import javax.mail.*;
import javax.mail.internet.*;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.beans.korisnici.Gost;
import com.example.beans.korisnici.Korisnik;
import com.example.beans.korisnici.MenadzerSistema;
import com.example.dto.korisnici.KorisnikDTO;
import com.example.dto.korisnici.MenadzerDTO;
import com.example.dto.korisnici.MenadzerSistemaDTO;
import com.example.enums.TipKorisnika;
import com.example.service.GostService;
import com.example.service.KorisnikService;
import com.example.service.korisniciImpl.MenadzerService;
import com.example.service.korisniciImpl.MenadzerSistemaService;

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

	@RequestMapping(value = "/login", method = { RequestMethod.POST }, produces = MediaType.APPLICATION_JSON_VALUE)
	public synchronized void login(HttpServletResponse response, @ModelAttribute("email") String email,
			@ModelAttribute("password") String password) throws IOException {
		boolean postoji = false;
		boolean aktiviran = false;
		boolean nasaoMenSistema = false;
		Korisnik korisnikKojiSeLoguje = null;
		System.out.println("loguje se: " + email + " " + password);

		System.out.println();
		Iterable<Korisnik> listaKorisnika = korisnikService.getAllKorisnici();

		for (Korisnik korisnik : listaKorisnika) {
			if (korisnik.getEmail().equals(email)) {
				if (korisnik.getPassword().equals(password)) {
					System.out.println("imaga");
					postoji = true;
					korisnikKojiSeLoguje = korisnik;
				}
			}
		}

		// Uzimam tip korisnika koji se loguje i pitam da li je Gost.
		// Ako jeste gost onda proveravam da li je aktivirao profil preko mejla,
		// ako jeste onda se loguje uspesno, ako nije onda se ne moze ulogovati

		if (korisnikKojiSeLoguje != null && postoji) {
			TipKorisnika tip = korisnikKojiSeLoguje.getTipKorisnika();
			System.out.println("Tip koji se loguje: "+ tip);
			switch (tip) {
			case GOST:
				Iterable<Gost> sviGosti = gostService.getAllGosti();
				for (Gost item : sviGosti) {
					if (item.isActivated()) {
						aktiviran = true;
					} else {
						aktiviran = false;
					}
				}
				break;
			case MENADZERSISTEMA:
				Iterable<MenadzerSistema> sviMenadzeri = menadzerSistemaService.getAll();
				for(MenadzerSistema m : sviMenadzeri){
					if(m.getEmail().equals(korisnikKojiSeLoguje.getEmail()) && 
							m.getPassword().equals(korisnikKojiSeLoguje.getPassword())){
						nasaoMenSistema = true;
					}
				}
			default:
				break;

			}
		}

		if (aktiviran) {
			response.sendRedirect("/home.html");
		} else if (nasaoMenSistema){
			response.sendRedirect("/adminPage.html");
		} else {
			response.sendRedirect("/index.html");
		}
	}

	// public synchronized void login(@RequestParam("email") String email,
	// @RequestParam("password") String password) throws IOException{

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

			// this.templateMessage = new SimpleMailMessage();
			//
			// SimpleMailMessage msg = new
			// SimpleMailMessage(this.templateMessage);
			// msg.setTo(email);
			// msg.setText(
			// "Dear " + name
			// + lastname
			// + ", thank you for register. Your confirm link is
			// http://localhost:8080/verify/"+ email);
			// try{
			// this.mailSender.send(msg);
			// }
			// catch (MailException ex) {
			// // simply log it and go on...
			// System.err.println(ex.getMessage());
			// }
			SecureRandom random = new SecureRandom();
			String hashCode = new BigInteger(130, random).toString(32);

			String d_email = "zlatanprecanica@gmail.com", d_uname = "Zlatan", d_password = "********",
					d_host = "smtp.gmail.com", d_port = "465", m_to = email, m_subject = "Verify Restaurant Account",
					m_text = "Hi " + name + ",\t\n\t\nThank you for registering on our website.\t\n"
							+ "To activate your account please go on link: http://localhost:8080/gost/active/"
							+ hashCode + "\t\n\t\nBest Regards,\t\nYour Restaurant.";
			Properties props = new Properties();
			props.put("mail.smtp.user", d_email);
			props.put("mail.smtp.host", d_host);
			props.put("mail.smtp.port", d_port);
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.debug", "true");
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.socketFactory.port", d_port);
			props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			props.put("mail.smtp.socketFactory.fallback", "false");

			Authenticator auth = new SMTPAuthenticator();
			Session session = Session.getInstance(props, auth);
			session.setDebug(true);

			MimeMessage msg = new MimeMessage(session);
			try {
				msg.setSubject(m_subject);
				msg.setText(m_text);
				msg.setFrom(new InternetAddress(d_email));
				msg.addRecipient(Message.RecipientType.TO, new InternetAddress(m_to));

				Transport transport = session.getTransport("smtps");
				transport.connect(d_host, Integer.valueOf(d_port), d_uname, d_password);
				transport.sendMessage(msg, msg.getAllRecipients());
				transport.close();

			} catch (AddressException e) {
				e.printStackTrace();
				return;
			} catch (MessagingException e) {
				e.printStackTrace();
				return;
			}

			Gost gost = new Gost(name, lastname, email, password, TipKorisnika.GOST);
			gost.setHashCode(hashCode);
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
		Iterable<Gost> listaGostiju = gostService.getAllGosti();
		ArrayList<Gost> list = new ArrayList<Gost>();
		for (Gost item : listaGostiju) {
			list.add(item);
		}

		for (Gost gost : list) {
			if (gost.getHashCode() != null) {
				if (gost.getHashCode().equals(kod)) {
					gost.setActivated(true);
					gostService.saveGost(gost);
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
		String password = "";
		String name = "";
		for (Korisnik item : listaKorisnika) {
			if (item.getEmail().equals(mejl)) {
				password = item.getPassword();
				name = item.getIme();
				System.out.println("uso");
			}
		}

		String d_email = "zlatanprecanica@gmail.com", d_uname = "Zlatan", d_password = "********",
				d_host = "smtp.gmail.com", d_port = "465", m_to = mejl, m_subject = "Restaurant Password Recovery",
				m_text = "Hi " + name + ",\t\n\t\nYour password is: " + password
						+ "\t\n\t\nBest Regards,\t\nYour Restaurant.";
		Properties props = new Properties();
		props.put("mail.smtp.user", d_email);
		props.put("mail.smtp.host", d_host);
		props.put("mail.smtp.port", d_port);
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.debug", "true");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.socketFactory.port", d_port);
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.socketFactory.fallback", "false");

		Authenticator auth = new SMTPAuthenticator();
		Session session = Session.getInstance(props, auth);
		session.setDebug(true);

		MimeMessage msg = new MimeMessage(session);
		try {
			msg.setSubject(m_subject);
			msg.setText(m_text);
			msg.setFrom(new InternetAddress(d_email));
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(m_to));

			Transport transport = session.getTransport("smtps");
			transport.connect(d_host, Integer.valueOf(d_port), d_uname, d_password);
			transport.sendMessage(msg, msg.getAllRecipients());
			transport.close();

		} catch (AddressException e) {
			e.printStackTrace();
			return;
		} catch (MessagingException e) {
			e.printStackTrace();
			return;
		}

		httpServletResponse.sendRedirect("/index.html");
	}
	
	@RequestMapping(value="/registerAdmin", method= RequestMethod.POST, consumes="application/json", produces="application/json")
	public boolean registracijaNovogMenadzeraSistema(@RequestBody @Valid MenadzerSistemaDTO admin){
		boolean ovakavPostoji = false;
		Iterable<Korisnik> sviKorisnici = korisnikService.getAllKorisnici();
		for(Korisnik k : sviKorisnici){
			if(k.getEmail().equals(admin.getEmail())){
				ovakavPostoji = true;
			}
		}		
		if(!ovakavPostoji){
			menadzerSistemaService.create(admin);
			return true;
		}else {
			return false;
		}
	}
	
	@RequestMapping(value="/registerMenadzer", method= RequestMethod.POST, consumes="application/json", produces="application/json")
	public boolean registracijaMenadzera(@RequestBody @Valid MenadzerDTO menadzer){
		boolean ovakavPostoji = false;
		Iterable<Korisnik> sviKorisnici = korisnikService.getAllKorisnici();
		for(Korisnik k : sviKorisnici){
			if(k.getEmail().equals(menadzer.getEmail())){
				ovakavPostoji = true;
			}
		}
		if(!ovakavPostoji){
			menadzerService.create(menadzer);
			return true;
		}else {
			return false;
		}
	}
}
