package com.example.dto.restoran;

import java.util.Date;

import com.example.beans.restoran.Pice;

public class PiceDTO {

	public int id;
	public String naziv;
	public String opis;
	public double cena;
	public String slika;
	public double ocena;
	public int brojOcena;
	public boolean obrisano;
	public int restoran; //id
	
	public PiceDTO(){}
	
	public PiceDTO(int idRest, String naziv, String opis, double cena){
		this.restoran = idRest;
		this.naziv = naziv;
		this.opis = opis;
		this.cena = cena;
	}
	
	public PiceDTO(Pice p, Date datum){
		this.id = p.getId();
		this.naziv = p.getNaziv();
		this.opis = p.getOpis();
		this.slika = p.getSlika();
		this.cena = p.getOcena();
		if(datum == null){
			this.cena = p.trenutnaCena().getIznos();
		}else {
			this.cena = p.cenaNaDatum(datum).getIznos();
		}
		this.brojOcena = p.getBroj_ocena();
		this.ocena = p.getOcena();
		this.obrisano = p.isObrisano();
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
	public String getOpis() {
		return opis;
	}
	public void setOpis(String opis) {
		this.opis = opis;
	}
	public double getCena() {
		return cena;
	}
	public void setCena(double cena) {
		this.cena = cena;
	}
	public String getSlika() {
		return slika;
	}
	public void setSlika(String slika) {
		this.slika = slika;
	}
	public double getOcena() {
		return ocena;
	}
	public void setOcena(double ocena) {
		this.ocena = ocena;
	}
	public int getBrojOcena() {
		return brojOcena;
	}
	public void setBrojOcena(int brojOcena) {
		this.brojOcena = brojOcena;
	}
	public boolean isObrisano() {
		return obrisano;
	}
	public void setObrisano(boolean obrisano) {
		this.obrisano = obrisano;
	}
	public int getRestoran() {
		return restoran;
	}
	public void setRestoran(int restoran) {
		this.restoran = restoran;
	}
}
