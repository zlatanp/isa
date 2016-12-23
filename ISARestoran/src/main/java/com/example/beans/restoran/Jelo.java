package com.example.beans.restoran;


import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.example.enums.KlasaJela;
import com.example.enums.TipJela;

@Entity
@Table(name="jelo")
public class Jelo implements Serializable {

	
	private static final long serialVersionUID = -9115550763163503279L;

	@Id
	@GeneratedValue
	@Column(name="id", nullable=false, unique=true)
	protected int id;
	
	@ManyToOne
	@JoinColumn(name="restoran", nullable=false, referencedColumnName="id")
	protected Restoran restoran;
	
	@Column(name="naziv", nullable=false)
	protected String naziv;
	
	@Column(name="opis", nullable=false)
	protected String opis;
	
	@OneToMany(cascade={CascadeType.ALL}, fetch=FetchType.LAZY)
	@JoinColumn(name="cena_jelo")
	protected Set<Cena> cene = new HashSet<Cena>();
	
	@Column(name="tip_jela", nullable=false)
	protected TipJela tip;
	
	@Enumerated(EnumType.STRING)
	@Column(name="klasa_jela", nullable=false)
	protected KlasaJela klasa;
	
	@Column(name="slika", nullable=true)
	protected String slika;
	
	@Column(name="ocena", nullable=false)
	protected double ocena;
	
	@Column(name="broj_ocena", nullable=false)
	protected int broj_ocena;
	
	@Column(name="obrisano", nullable=false)
	protected boolean obrisano;
	
	
	public Jelo() {
		ocena = 0;
		broj_ocena = 0;
	}


	public Jelo(Restoran restoran, String naziv, String opis, Cena cena, TipJela tip, KlasaJela klasa) {
		super();
		this.restoran = restoran;
		this.naziv = naziv;
		this.opis = opis;
		this.cene.add(cena);
		this.tip = tip;
		this.klasa = klasa;
		this.ocena = 0;
		this.broj_ocena = 0;
		this.obrisano = false;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public Restoran getRestoran() {
		return restoran;
	}


	public void setRestoran(Restoran restoran) {
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


	public Set<Cena> getCene() {
		return cene;
	}


	public void setCene(Set<Cena> cene) {
		this.cene = cene;
	}


	public TipJela getTip() {
		return tip;
	}


	public void setTip(TipJela tip) {
		this.tip = tip;
	}


	public KlasaJela getKlasa() {
		return klasa;
	}


	public void setKlasa(KlasaJela klasa) {
		this.klasa = klasa;
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


	public int getBroj_ocena() {
		return broj_ocena;
	}


	public void setBroj_ocena(int broj_ocena) {
		this.broj_ocena = broj_ocena;
	}


	public boolean isObrisano() {
		return obrisano;
	}


	public void setObrisano(boolean obrisano) {
		this.obrisano = obrisano;
	}


	@Override
	public boolean equals(Object obj) {
		if(this == obj){
			return true;
		}
		if(obj == null){
			return false;
		}
		if(!(obj instanceof Jelo)){
			return false;
		}
		Jelo cast = (Jelo)obj;
		if(this.id==cast.id){
			return true;
		}
		return false;
	}
	
	public Cena trenutnaCena(){
		for(Cena c: cene){
			if(c.datumDo == null){
				return c;
			}
		}
		return null;
	}
	
	public Cena cenaNaDatum(Date datum){
		for(Cena c : cene){
			if(c.datumDo== null){
				return c;
			}
			if(c.datumOd.compareTo(datum) <= 0 && c.datumDo.compareTo(datum) >=0){
				return c;
			}
		}
		return null;
	}
	
	public void izracunajNovuOcenu(int ocena){
		double pom = this.ocena;
		this.broj_ocena++;
		pom += ocena;
		this.ocena = pom / this.broj_ocena;
	}
	
	
}
