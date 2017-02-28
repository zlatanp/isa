package com.example.beans.restoran;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;

import com.example.beans.korisnici.Kuvar;

@Entity
@Table(name="stavka_jela")
public class StavkaJela implements Serializable {

	
	private static final long serialVersionUID = 5867734233489601820L;
	
	@Id
	@GeneratedValue
	@Column(name="id", nullable=false, unique=true)
	protected int id;
	
	@ManyToOne
	@JoinColumn(name="jelo", nullable=false)
	protected Jelo jelo;
	
	@Column(name="kolicina", nullable=false)
	protected int kolicina;
	
	@ManyToOne
	@JoinColumn(name="skuvao")
	protected Kuvar skuvao;
	
	@Column(name="spremljeno", nullable=false)
	protected boolean spremljeno = false;
	
	@ManyToOne
	@JoinColumn(name="narudzbinaStavkeJela", referencedColumnName="id")
	protected Narudzbina narudzbinaStavkeJela;
	
	@Version
	private Integer version;
	
	public StavkaJela() {}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Jelo getJelo() {
		return jelo;
	}

	public void setJelo(Jelo jelo) {
		this.jelo = jelo;
	}

	public int getKolicina() {
		return kolicina;
	}

	public void setKolicina(int kolicina) {
		this.kolicina = kolicina;
	}

	public Kuvar getSkuvao() {
		return skuvao;
	}

	public void setSkuvao(Kuvar skuvao) {
		this.skuvao = skuvao;
	}

	public boolean isSpremljeno() {
		return spremljeno;
	}

	public void setSpremljeno(boolean spremljeno) {
		this.spremljeno = spremljeno;
	}

	public Narudzbina getNarudzbinaStavkeJela() {
		return narudzbinaStavkeJela;
	}

	public void setNarudzbinaStavkeJela(Narudzbina narudzbinaStavkeJela) {
		this.narudzbinaStavkeJela = narudzbinaStavkeJela;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}
}
