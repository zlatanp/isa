package com.example.dto.restoran;

import java.util.Date;

import com.example.beans.restoran.Ocena;
import com.example.dto.korisnici.GostDTO;


public class OcenaDTO {

	public int id;
	public int ocenaNarucenog;
	public int ocenaRestorana;
	public int ocenaUsluge; // za konobara
	public GostDTO recenzent; // email gosta
	public int restoran;
	public String konobar; // email konobara
	public int poseta;
	public Date vreme;
	public String text;
	
	public OcenaDTO() {
		
	}
	
	public OcenaDTO(Ocena o){
		this.id = o.getId();
		this.ocenaRestorana = o.getOcenaRestorana();
		this.ocenaNarucenog = o.getOcenaNarucenogJela();
		this.ocenaUsluge = o.getOcenaUsluge();
		this.recenzent = new GostDTO(o.getGost());
		this.restoran = o.getRestoran().getId();
		this.konobar = o.getKonobar().getEmail();
		this.poseta = o.getPoseta().getId();
		this.vreme = o.getVreme();
		this.text = o.getOpis();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getOcenaNarucenog() {
		return ocenaNarucenog;
	}

	public void setOcenaNarucenog(int ocenaNarucenog) {
		this.ocenaNarucenog = ocenaNarucenog;
	}

	public int getOcenaRestorana() {
		return ocenaRestorana;
	}

	public void setOcenaRestorana(int ocenaRestorana) {
		this.ocenaRestorana = ocenaRestorana;
	}

	public int getOcenaUsluge() {
		return ocenaUsluge;
	}

	public void setOcenaUsluge(int ocenaUsluge) {
		this.ocenaUsluge = ocenaUsluge;
	}

	public GostDTO getRecenzent() {
		return recenzent;
	}

	public void setRecenzent(GostDTO recenzent) {
		this.recenzent = recenzent;
	}

	public int getRestoran() {
		return restoran;
	}

	public void setRestoran(int restoran) {
		this.restoran = restoran;
	}

	public String getKonobar() {
		return konobar;
	}

	public void setKonobar(String konobar) {
		this.konobar = konobar;
	}

	public int getPoseta() {
		return poseta;
	}

	public void setPoseta(int poseta) {
		this.poseta = poseta;
	}

	public Date getVreme() {
		return vreme;
	}

	public void setVreme(Date vreme) {
		this.vreme = vreme;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
