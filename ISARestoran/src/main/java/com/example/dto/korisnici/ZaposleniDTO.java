package com.example.dto.korisnici;





import java.util.Date;

import com.example.beans.korisnici.Zaposleni;
import com.example.enums.TipKorisnika;

public abstract class ZaposleniDTO extends KorisnikDTO{

	private int radi_u;
	
	private Date datum_rodjenja;
	
	private String konfekcijski_broj;
	
	private String velicina_obuce;
	
	
	public ZaposleniDTO(){
		super();
	}
	
	
	public ZaposleniDTO(Zaposleni k) {
		super(k);
		this.datum_rodjenja = k.getDatum_rodjenja();
		this.radi_u = k.getRadi_u().getId();
		this.konfekcijski_broj = k.getKonfekcijski_broj();
		this.velicina_obuce = k.getVelicina_obuce();
	}

	public ZaposleniDTO(String ime, String prezime, String email, String password, TipKorisnika tip, String konfBr, String velOb, Date datumRodj, int radiU) {
		super(ime, prezime, email, password, tip);
		this.konfekcijski_broj = konfBr;
		this.velicina_obuce = velOb;
		this.datum_rodjenja = datumRodj;
		this.radi_u = radiU;
	}


	public int getRadi_u() {
		return radi_u;
	}


	public void setRadi_u(int radi_u) {
		this.radi_u = radi_u;
	}


	public Date getDatum_rodjenja() {
		return datum_rodjenja;
	}


	public void setDatum_rodjenja(Date datum_rodjenja) {
		this.datum_rodjenja = datum_rodjenja;
	}


	public String getKonfekcijski_broj() {
		return konfekcijski_broj;
	}


	public void setKonfekcijski_broj(String konfekcijski_broj) {
		this.konfekcijski_broj = konfekcijski_broj;
	}


	public String getVelicina_obuce() {
		return velicina_obuce;
	}


	public void setVelicina_obuce(String velicina_obuce) {
		this.velicina_obuce = velicina_obuce;
	}
	
	
}
