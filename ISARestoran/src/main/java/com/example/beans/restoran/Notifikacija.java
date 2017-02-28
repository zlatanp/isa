package com.example.beans.restoran;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.example.beans.korisnici.Gost;
import com.example.beans.korisnici.Korisnik;
import com.example.enums.TypeNotification;


@Entity
@Table(name="notifikacija")
public class Notifikacija implements Serializable{
	
	private static final long serialVersionUID = 5392387690158147884L;

	@Id
	@GeneratedValue
	@Column(name="id",nullable=false,unique=true)
	protected int id;
	
	@ManyToOne
	@JoinColumn(name="korisnik", nullable=false)
	protected Korisnik korisnik;
	
	@Column(name="text_notif",nullable=false)
	protected String text;
	
	@ManyToOne
	@JoinColumn(name="vezana_za_gosta",referencedColumnName="id", nullable=true)
	protected Gost vezana_za_gosta;
	
	@ManyToOne
	@JoinColumn(name="vezana_za_rezervaciju", referencedColumnName="id", nullable=true)
	protected Rezervacija vezana_za_rezervaciju;
	
	@ManyToOne
	@JoinColumn(name="vezana_za_ponudu", referencedColumnName="id", nullable=true)
	protected Ponuda vezana_za_ponudu;
	
	@ManyToOne
	@JoinColumn(name="vezana_za_restoran",referencedColumnName="id", nullable=true)
	protected Restoran vezana_za_restoran;
	
	@Column(name="pregledana",nullable=false)
	protected boolean pregledana;
	
	@Column(name="tip", nullable=false)
	protected TypeNotification tip;

	public Notifikacija() {
	}

	public Notifikacija(Korisnik korisnik, String text, Gost vezana_za_gosta, Rezervacija vezana_za_rezervaciju,
			Ponuda vezana_za_ponudu, Restoran vezana_za_restoran, boolean pregledana, TypeNotification tip) {
		super();
		this.korisnik = korisnik;
		this.text = text;
		this.vezana_za_gosta = vezana_za_gosta;
		this.vezana_za_rezervaciju = vezana_za_rezervaciju;
		this.vezana_za_ponudu = vezana_za_ponudu;
		this.vezana_za_restoran = vezana_za_restoran;
		this.pregledana = pregledana;
		this.tip = tip;
		this.pregledana=false;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Korisnik getKorisnik() {
		return korisnik;
	}

	public void setKorisnik(Korisnik korisnik) {
		this.korisnik = korisnik;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Gost getVezana_za_gosta() {
		return vezana_za_gosta;
	}

	public void setVezana_za_gosta(Gost vezana_za_gosta) {
		this.vezana_za_gosta = vezana_za_gosta;
	}

	public Rezervacija getVezana_za_rezervaciju() {
		return vezana_za_rezervaciju;
	}

	public void setVezana_za_rezervaciju(Rezervacija vezana_za_rezervaciju) {
		this.vezana_za_rezervaciju = vezana_za_rezervaciju;
	}

	public Ponuda getVezana_za_ponudu() {
		return vezana_za_ponudu;
	}

	public void setVezana_za_ponudu(Ponuda vezana_za_ponudu) {
		this.vezana_za_ponudu = vezana_za_ponudu;
	}

	public Restoran getVezana_za_restoran() {
		return vezana_za_restoran;
	}

	public void setVezana_za_restoran(Restoran vezana_za_restoran) {
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
