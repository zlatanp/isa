package com.example.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

import java.security.SecureRandom;
import java.math.BigInteger;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.*;
import java.io.*;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import com.example.beans.korisnici.Gost;
import com.example.beans.korisnici.Korisnik;
import com.example.enums.TipKorisnika;
import com.example.service.GostService;
import com.example.service.KorisnikService;



/*
 * prepravi da umesto guestcontroller bude usercontroller, svi mogu da se loguju, da ne bilo
 * redudantnog koda
 * 
 * */



@RestController
@RequestMapping("/gost")
public class GuestController {
	
	@Autowired
	private KorisnikService korisnikService;

	@Autowired
	private GostService gostService;
    
	
	@RequestMapping(value = "/login", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_VALUE)
	public synchronized void login(HttpServletResponse httpServletResponse, @ModelAttribute("email") String email, @ModelAttribute("password") String password) throws IOException{
		boolean postoji = false;
		System.out.println("loguje se: " + email + " " + password);
		
		Iterable<Korisnik> listaKorisnika = korisnikService.getAllKorisnici();
		ArrayList<Korisnik> list = new ArrayList<Korisnik>();
		for (Korisnik item : listaKorisnika){
	        list.add(item);
	    }
	    
	    for(int i=0;i<list.size();i++){
	    	if(list.get(i).getEmail().equals(email)){
	    		if(list.get(i).getPassword().equals(password)){
	    			 System.out.println("imaga");
	    			 postoji = true;
	    		}
	    	}
	    }
	    
	    if(postoji){
	    	httpServletResponse.sendRedirect("/home.html");
	    }else{
	    	httpServletResponse.sendRedirect("/index.html");
	    }
		}
	
	//public synchronized void login(@RequestParam("email") String email, @RequestParam("password") String password) throws IOException{

	
	
	@RequestMapping(value = "/register",method = {RequestMethod.POST})
	public synchronized void register(HttpServletResponse httpServletResponse, @ModelAttribute("nameRegister") String name, @ModelAttribute("lastnameRegister") String lastname,
			@ModelAttribute("emailRegister") String email,  @ModelAttribute("passwordRegister") String password, @ModelAttribute("password1Register") String passwordRepeat) throws IOException{
		boolean uspesno = false;
		
		System.out.println(name + lastname + email + password + passwordRepeat);
		
		
		//Serverska provera da li su polja prazna
		if (!(name.isEmpty() || lastname.isEmpty() || email.isEmpty() || password.isEmpty() || passwordRepeat.isEmpty())) {	
			uspesno = true;
			
			//Iscitavanje svih korisnika iz baze
			Iterable<Korisnik> listaKorisnika = korisnikService.getAllKorisnici();
			ArrayList<Korisnik> list = new ArrayList<Korisnik>();
			for (Korisnik item : listaKorisnika){
		        list.add(item);
		    }
			
			//Serverska provera da li je korisnik uneo isti pasword
			if(!password.equals(passwordRepeat)){
				uspesno = false;
			}
			
			//Provera da li postoji takav email, ako postoji korisnik ne moze da se registruje sa 2 ista emaila
			for (Korisnik korisnik : list) {
				if(korisnik.getEmail().equals(email)){
					uspesno = false;
				}
			}
		}
		
		//Ako je prosao sve provere, unesi korisnika u bazu (registrovan je),
		//ako nije prosao sve provere povratak na pocetnu stranu (index)
		if(uspesno){
			
			
//			this.templateMessage = new SimpleMailMessage();
//			
//			SimpleMailMessage msg = new SimpleMailMessage(this.templateMessage);
//	        msg.setTo(email);
//	        msg.setText(
//	            "Dear " + name
//	                + lastname
//	                + ", thank you for register. Your confirm link is http://localhost:8080/verify/"+ email);
//	        try{
//	            this.mailSender.send(msg);
//	        }
//	        catch (MailException ex) {
//	            // simply log it and go on...
//	            System.err.println(ex.getMessage());
//	        }
			 SecureRandom random = new SecureRandom();
			 String hashCode =  new BigInteger(130, random).toString(32);
			
			String  d_email = "zlatanprecanica@gmail.com",
		            d_uname = "Zlatan",
		            d_password = "********",
		            d_host = "smtp.gmail.com",
		            d_port  = "465",
		            m_to = email,
		            m_subject = "Verify Restoran Account",
		            m_text = name + " Thank you for registering to the Restoran website." +
		            "To active your account please go on link: http://localhost:8080/active/"+ hashCode;
		    Properties props = new Properties();
		    props.put("mail.smtp.user", d_email);
		    props.put("mail.smtp.host", d_host);
		    props.put("mail.smtp.port", d_port);
		    props.put("mail.smtp.starttls.enable","true");
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
		
		    
			
			

			//Korisnik noviKorisnik = new Korisnik(name, lastname, email, password, TipKorisnika.GOST);
			//korisnikService.saveKorisnik(noviKorisnik);
			
			Gost gost = new Gost(name,lastname,email,password,TipKorisnika.GOST);
			gost.setHashCode(hashCode);
			gostService.saveGost(gost);
			
			httpServletResponse.sendRedirect("/index.html");
			System.out.println("registrovao");
		    
		}else{
			httpServletResponse.sendRedirect("/index.html");
			System.out.println("skipovao");
		}
				
		

	}
	
	@RequestMapping(value = "/obrisi")
	public synchronized String obrisi(){
		korisnikService.deleteKorisnik(2);
		return "obrisao";
	}
}
