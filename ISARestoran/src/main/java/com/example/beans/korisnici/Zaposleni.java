package com.example.beans.korisnici;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.example.beans.restoran.Restoran;
import com.example.enums.TipKorisnika;

@Entity
@Table(name = "zaposleni")
public abstract class Zaposleni extends Korisnik {

	private static final long serialVersionUID = -8867521753937558381L;

	@ManyToOne
	@JoinColumn(name="radi_u", referencedColumnName = "id")
	protected Restoran radi_u;
	
	@JoinColumn(name="datum_rodjenja")
	protected Date datum_rodjenja;
	
	@JoinColumn(name="konfekcijski_broj")
	protected String konfekcijski_broj;
	
	@JoinColumn(name="velicina_obuce")
	protected String velicina_obuce;

	
	public Zaposleni() {
		
	}	
	
	public Zaposleni(String ime, String prezime, String email, String password, TipKorisnika tipKorisnika) {
		super(ime, prezime, email, password, tipKorisnika);
	}
	
	public Zaposleni(String ime, String prezime, String email, String password, TipKorisnika tipKorisnika,
			Date datumRodjenja, String konfBr, String velObuce, Restoran radiU) {
		super(ime, prezime, email, password, tipKorisnika);
		this.datum_rodjenja = datumRodjenja;
		this.konfekcijski_broj = konfBr;
		this.velicina_obuce = velObuce;
		this.radi_u = radiU;
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

	public Restoran getRadi_u() {
		return radi_u;
	}

	public void setRadi_u(Restoran radi_u) {
		this.radi_u = radi_u;
	}
}
