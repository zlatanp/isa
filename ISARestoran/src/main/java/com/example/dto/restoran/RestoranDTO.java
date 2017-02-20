package com.example.dto.restoran;

import java.util.ArrayList;
import java.util.List;

import com.example.beans.korisnici.MenadzerRestorana;
import com.example.beans.restoran.Restoran;
import com.example.dto.korisnici.MenadzerDTO;
import com.example.enums.TipRestorana;
import com.example.enums.TipValute;


public class RestoranDTO {

	public int id;
	public String naziv;
	public String adresa;
	public String grad;
	public String telefon;
	public String email;
	public String opis;
	public String vremeOD;
	public String vremeDO;
	public TipRestorana tip;
	public TipValute valuta;	
	public List<MenadzerDTO> menadzeri;
	
	public RestoranDTO() {
		
	
	}
	
	public RestoranDTO(int id, String naziv, String adresa, String grad, String telefon, String email, String opis,
			String vremeOD, String vremeDO, TipRestorana tip, TipValute val) {
		super();
		this.id = id;
		this.naziv = naziv;
		this.adresa = adresa;
		this.grad = grad;
		this.telefon = telefon;
		this.email = email;
		this.opis = opis;
		this.vremeOD = vremeOD;
		this.vremeDO = vremeDO;
		this.tip = tip;
		this.valuta = val;
	}
	
	
	public RestoranDTO(Restoran r){
		this.id = r.getId();
		this.naziv = r.getNaziv();
		this.adresa = r.getAdresa();
		this.grad = r.getGrad();
		this.telefon = r.getTelefon();
		this.email = r.getEmail();
		this.opis = r.getOpis();
		this.vremeOD = r.getRadnoVremeOd();
		this.vremeDO = r.getRadnoVremeDo();
		this.tip = r.getRestaurantType();
		this.valuta = r.getValuta();
		this.menadzeri = new ArrayList<>();
		for(MenadzerRestorana m : r.getMenadzeri()){
			this.menadzeri.add(new MenadzerDTO(m));
		}
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public String getAdresa() {
		return adresa;
	}

	public void setAdresa(String adresa) {
		this.adresa = adresa;
	}

	public String getGrad() {
		return grad;
	}

	public void setGrad(String grad) {
		this.grad = grad;
	}

	public String getTelefon() {
		return telefon;
	}

	public void setTelefon(String telefon) {
		this.telefon = telefon;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getOpis() {
		return opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}

	public String getVremeOD() {
		return vremeOD;
	}

	public void setVremeOD(String vremeOD) {
		this.vremeOD = vremeOD;
	}

	public String getVremeDO() {
		return vremeDO;
	}

	public void setVremeDO(String vremeDO) {
		this.vremeDO = vremeDO;
	}

	public TipRestorana getTip() {
		return tip;
	}

	public void setTip(TipRestorana tip) {
		this.tip = tip;
	}

	public TipValute getValuta() {
		return valuta;
	}

	public void setValuta(TipValute valuta) {
		this.valuta = valuta;
	}

	public List<MenadzerDTO> getMenadzeri() {
		return menadzeri;
	}

	public void setMenadzeri(List<MenadzerDTO> menadzeri) {
		this.menadzeri = menadzeri;
	}

}
