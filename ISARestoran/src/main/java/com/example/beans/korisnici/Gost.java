package com.example.beans.korisnici;


import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.example.enums.TipKorisnika;



@Entity
@Table(name= "gost")
public class Gost extends Korisnik{

	private static final long serialVersionUID = -7179926090706377976L;

	private boolean activated = false;
	private String hashCode;
	
	@OneToMany(mappedBy="ja", fetch=FetchType.LAZY)
	protected Set<Prijateljstvo> prijatelj_sa = new HashSet<Prijateljstvo>();
	
	public Gost() {
		this.activated = false;
		this.hashCode = "";
	}
	
	public Gost(String ime, String prezime, String email, String password, TipKorisnika tipKorisnika) {
		super(ime, prezime, email, password, tipKorisnika);
		this.activated = false;
		this.hashCode = "";
	}

	public boolean isActivated() {
		return activated;
	}

	public void setActivated(boolean activated) {
		this.activated = activated;
	}

	public String getHashCode() {
		return hashCode;
	}

	public void setHashCode(String hashCode) {
		this.hashCode = hashCode;
	}

	

}
