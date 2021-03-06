package com.example.beans.restoran;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.example.enums.TipRestorana;
import com.example.enums.TipValute;

@Entity
@Table(name = "restoran")
public class Restoran implements Serializable {

	private static final long serialVersionUID = 3991673638215578988L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	protected int id;

	@Column(name = "naziv", nullable = false)
	protected String naziv;
	
	@Column(name = "tip", nullable = false)
	protected TipRestorana tipRestorana;

	@Column(name = "adresa", nullable = false)
	protected String adresa;
	
	@Column(name = "grad", nullable = false)
	protected String grad;
	
	@Column(name = "opis", nullable = false)
	protected String opis;
	
	@Column(name = "telefon", nullable = false)
	protected String telefon;
	
	@Column(name = "email", nullable = false)
	protected String email;
	
	@Column(name = "radno_vreme_od", nullable = false)
	protected String radnoVremeOd;
	
	@Column(name = "radno_vreme_do", nullable = false)
	protected String radnoVremeDo;
	
	@Column(name = "valuta", nullable = false)
	protected TipValute valuta;
	
	
	public Restoran() {
	}
	
	


	public Restoran(String naziv, TipRestorana restaurantType, String adresa, String grad, String opis, String telefon,
			String email, String radnoVremeOd, String radnoVremeDo) {
		super();
		this.naziv = naziv;
		this.tipRestorana = restaurantType;
		this.adresa = adresa;
		this.grad = grad;
		this.opis = opis;
		this.telefon = telefon;
		this.email = email;
		this.radnoVremeOd = radnoVremeOd;
		this.radnoVremeDo = radnoVremeDo;
		this.valuta = TipValute.DINAR;
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


	public TipRestorana getRestaurantType() {
		return tipRestorana;
	}


	public void setRestaurantType(TipRestorana restaurantType) {
		this.tipRestorana = restaurantType;
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


	public String getOpis() {
		return opis;
	}


	public void setOpis(String opis) {
		this.opis = opis;
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


	public String getRadnoVremeOd() {
		return radnoVremeOd;
	}


	public void setRadnoVremeOd(String radnoVremeOd) {
		this.radnoVremeOd = radnoVremeOd;
	}


	public String getRadnoVremeDo() {
		return radnoVremeDo;
	}


	public void setRadnoVremeDo(String radnoVremeDo) {
		this.radnoVremeDo = radnoVremeDo;
	}


	public TipValute getValuta() {
		return valuta;
	}


	public void setValuta(TipValute valuta) {
		this.valuta = valuta;
	}
	
	
	
}