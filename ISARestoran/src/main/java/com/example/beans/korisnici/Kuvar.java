package com.example.beans.korisnici;



import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.example.beans.restoran.Restoran;
import com.example.enums.TipJela;
import com.example.enums.TipKorisnika;

@Entity
@Table(name="kuvar")
public class Kuvar extends Zaposleni {

	private static final long serialVersionUID = -8927778679890541483L;

	@Column(name="kuvar_za", nullable= true)
	protected TipJela kuvar_za;
	
	
	public Kuvar() {
		
	}

	public Kuvar(String ime, String prezime, String email, String password, TipKorisnika tipKorisnika,
			Date datumRodjenja, String konfBr, String velObuce, Restoran radiU) {
		super(ime, prezime, email, password, tipKorisnika, datumRodjenja, konfBr, velObuce, radiU);		
	}

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}


	public TipJela getKuvar_za() {
		return kuvar_za;
	}


	public void setKuvar_za(TipJela kuvar_za) {
		this.kuvar_za = kuvar_za;
	}
		
}
