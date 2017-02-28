package com.example.dto.korisnici;



import java.sql.Date;

import com.example.beans.korisnici.Sanker;
import com.example.enums.TipKorisnika;

public class SankerDTO extends ZaposleniDTO{

	public SankerDTO() {
		super();
	}

	public SankerDTO(String ime, String prezime, String email, String password, TipKorisnika tip, String konfBr,
			String velOb, Date datumRodj, int radiU) {
		super(ime, prezime, email, password, tip, konfBr, velOb, datumRodj, radiU);
	}

	public SankerDTO(Sanker k) {
		super(k);
	}

	
}
