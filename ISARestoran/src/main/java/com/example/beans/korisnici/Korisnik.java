package com.example.beans.korisnici;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import com.example.enums.TipKorisnika;

@Entity
@Table(name = "korisnici")
@Inheritance(strategy=InheritanceType.JOINED)
public class Korisnik implements Serializable {

	private static final long serialVersionUID = -8122091907599278379L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id", nullable = false, unique = true)
	protected int id;
	
	@Column(name = "ime")
	protected String ime;
	
	@Column(name = "prezime")
	protected String prezime;
	
	@Column(name = "email", unique = true, nullable = false)
	protected String email;
	
	@Column(name = "password", nullable = false)
	protected String password;
	
	@Column(name = "tip", nullable = false)
	protected TipKorisnika tipKorisnika;

	@Column(name = "slika", nullable = true, columnDefinition = "LONGBLOB")
	protected byte[] slika;
			
	public Korisnik() {
		
	}
	
	public Korisnik(String ime, String prezime, String email, String password, TipKorisnika tipKorisnika) {
		super();
		this.ime = ime;
		this.prezime = prezime;
		this.email = email;
		this.password = password;
		this.tipKorisnika = tipKorisnika;
	}



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getIme() {
		return ime;
	}

	public void setIme(String ime) {
		this.ime = ime;
	}

	public String getPrezime() {
		return prezime;
	}

	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public TipKorisnika getTipKorisnika() {
		return tipKorisnika;
	}

	public void setTipKorisnika(TipKorisnika tipKorisnika) {
		this.tipKorisnika = tipKorisnika;
	}

	public byte[] getSlika() {
		return slika;
	}

	public void setSlika(byte[] slika) {
		this.slika = slika;
	}
}
