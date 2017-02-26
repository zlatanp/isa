package com.example.beans.restoran;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.example.enums.TipSegmenta;

@Entity
@Table(name="Sto")
public class Sto implements Serializable{

	private static final long serialVersionUID = 7298882238013663201L;

	@Id
	@GeneratedValue
	@Column(name="id", nullable=false, unique=true)
	protected int id;
	
	@Column(name="naziv", nullable=false)
	protected String naziv;
	
	@Column(name="brojStolica", nullable=false)
	protected int brojStolica;
	
	@ManyToOne
	@JoinColumn(name="segment",referencedColumnName="id", nullable=true)
	protected Segment segment;
	
	@Column(name="tipSegmenta", nullable=false)
	protected TipSegmenta tipSegmenta;
	
	public Sto(){}

	
	public Sto(String naziv, int brojStolica, Segment segment, TipSegmenta tipSegmenta) {
		super();
		this.naziv = naziv;
		this.brojStolica = brojStolica;
		this.segment = segment;
		this.tipSegmenta = tipSegmenta;
	}
	

	public Sto(String naziv, int brojStolica, TipSegmenta tipSegmenta) {
		super();
		this.naziv = naziv;
		this.brojStolica = brojStolica;
		this.tipSegmenta = tipSegmenta;
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

	public int getBrojStolica() {
		return brojStolica;
	}

	public void setBrojStolica(int brojStolica) {
		this.brojStolica = brojStolica;
	}

	public Segment getSegment() {
		return segment;
	}

	public void setSegment(Segment segment) {
		this.segment = segment;
	}

	public TipSegmenta getTipSegmenta() {
		return tipSegmenta;
	}

	public void setTipSegmenta(TipSegmenta tipSegmenta) {
		this.tipSegmenta = tipSegmenta;
	}
}
