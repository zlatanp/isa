package com.example.dto.korisnici;



import java.sql.Date;

import com.example.beans.korisnici.Kuvar;
import com.example.enums.TipKorisnika;

public class KuvarDTO extends ZaposleniDTO {

	public KuvarDTO() {
		super();
	}

	public KuvarDTO(String ime, String prezime, String email, String password, TipKorisnika tip, String konfBr,
			String velOb, Date datumRodj, int radiU) {
		super(ime, prezime, email, password, tip, konfBr, velOb, datumRodj, radiU);
	}

	public KuvarDTO(Kuvar k) {
		super(k);
	}
}
