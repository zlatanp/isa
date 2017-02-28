package com.example.dto.restoran;

import com.example.beans.restoran.Notifikacija;
import com.example.enums.TypeNotification;

public class NotifikacijaDTO {

	public int id;
	public String korisnik; //email korisnika
	public String text;
	public String vezana_za_gosta; // email gosta
	public int vezana_za_rezervaciju; // id rez
	public int vezana_za_ponudu; // id rez
	public int vezana_za_restoran; // id rez
	public boolean pregledana; 
	public TypeNotification tip;
	
	public NotifikacijaDTO() {}
	
	public NotifikacijaDTO(Notifikacija n){
		this.id = n.getId();
		this.text = n.getText();
		this.tip = n.getTip();
		this.pregledana = n.isPregledana();
		if(n.getKorisnik() != null){
			this.korisnik = n.getKorisnik().getEmail();
		}else {
			this.korisnik = null;
		}
		if(n.getVezana_za_gosta() != null){
			this.vezana_za_gosta = n.getVezana_za_gosta().getEmail();
		}else {
			this.vezana_za_gosta = null;
		}
		if(n.getVezana_za_ponudu() != null){
			this.vezana_za_ponudu = n.getVezana_za_ponudu().getId();
		}else {
			this.vezana_za_ponudu = -1;
		}
		if(n.getVezana_za_restoran() != null){
			this.vezana_za_restoran = n.getVezana_za_restoran().getId();
		}else {
			this.vezana_za_restoran = -1;
		}
		if(n.getVezana_za_rezervaciju() != null){
			this.vezana_za_rezervaciju = n.getVezana_za_rezervaciju().getId();
		}else {
			this.vezana_za_rezervaciju = -1;
		}
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getKorisnik() {
		return korisnik;
	}

	public void setKorisnik(String korisnik) {
		this.korisnik = korisnik;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getVezana_za_gosta() {
		return vezana_za_gosta;
	}

	public void setVezana_za_gosta(String vezana_za_gosta) {
		this.vezana_za_gosta = vezana_za_gosta;
	}

	public int getVezana_za_rezervaciju() {
		return vezana_za_rezervaciju;
	}

	public void setVezana_za_rezervaciju(int vezana_za_rezervaciju) {
		this.vezana_za_rezervaciju = vezana_za_rezervaciju;
	}

	public int getVezana_za_ponudu() {
		return vezana_za_ponudu;
	}

	public void setVezana_za_ponudu(int vezana_za_ponudu) {
		this.vezana_za_ponudu = vezana_za_ponudu;
	}

	public int getVezana_za_restoran() {
		return vezana_za_restoran;
	}

	public void setVezana_za_restoran(int vezana_za_restoran) {
		this.vezana_za_restoran = vezana_za_restoran;
	}

	public boolean isPregledana() {
		return pregledana;
	}

	public void setPregledana(boolean pregledana) {
		this.pregledana = pregledana;
	}

	public TypeNotification getTip() {
		return tip;
	}

	public void setTip(TypeNotification tip) {
		this.tip = tip;
	}
}
