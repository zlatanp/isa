package com.example.dto.restoran;

import java.util.Date;

import com.example.beans.restoran.Jelo;
import com.example.enums.KlasaJela;
import com.example.enums.TipJela;

public class JeloDTO {

	public int id;
	public int restoran; //id-restorana
	public String naziv;
	public String opis;
	public double cena;
	public KlasaJela klasaJela;
	public TipJela tipJela;
	public String slika;
	public double ocena;
	public int brojOcena;
	public boolean obrisano;
	
	public JeloDTO(){}
	
	
	public JeloDTO(Jelo jelo, Date datum){
		this.id = jelo.getId();
		this.restoran = jelo.getRestoran().getId();
		this.naziv = jelo.getNaziv();
		this.opis = jelo.getOpis();
		if(datum == null){
			this.cena = jelo.trenutnaCena().getIznos();
		}else{
			this.cena = jelo.cenaNaDatum(datum).getIznos();
		}
		this.klasaJela = jelo.getKlasa();
		this.tipJela = jelo.getTip();
		this.slika = jelo.getSlika();
		this.ocena = jelo.getOcena();
		this.brojOcena = jelo.getBroj_ocena();
		this.obrisano = jelo.isObrisano();
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getRestoran() {
		return restoran;
	}
	public void setRestoran(int restoran) {
		this.restoran = restoran;
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
	public KlasaJela getKlasaJela() {
		return klasaJela;
	}
	public void setKlasaJela(KlasaJela klasaJela) {
		this.klasaJela = klasaJela;
	}
	public TipJela getTipJela() {
		return tipJela;
	}
	public void setTipJela(TipJela tipJela) {
		this.tipJela = tipJela;
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
	
}
