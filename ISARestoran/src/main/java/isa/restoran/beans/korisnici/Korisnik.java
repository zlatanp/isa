package isa.restoran.beans.korisnici;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Korisnik {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id", nullable= false, unique = true)
	private int id;
	

	@Column(name="email", nullable= false, unique = true)
	private String email;
	
	@Column(name="password", nullable= false)
	private String password;
	
	@Column(name="ime")
	private String ime;
	
	@Column(name="prezime")
	private String prezime;

	public Korisnik() {
		
	}
	
	public Korisnik(int id, String email, String password, String ime, String prezime) {
		super();
		this.id = id;
		this.email = email;
		this.password = password;
		this.ime = ime;
		this.prezime = prezime;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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
	
}
