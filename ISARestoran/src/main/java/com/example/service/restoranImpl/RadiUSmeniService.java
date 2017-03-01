package com.example.service.restoranImpl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.beans.korisnici.MenadzerRestorana;
import com.example.beans.restoran.Restoran;
import com.example.beans.restoran.Smena;
import com.example.beans.restoran.USmeniRadnik;
import com.example.dto.hellpers.RadniciSmena;
import com.example.dto.restoran.StoDTO;
import com.example.repository.MenadzerRepository;
import com.example.repository.restoran.RestoranRepository;
import com.example.repository.restoran.StoRepository;
import com.example.repository.restoran.USmeniRadnikRepository;

@Service
public class RadiUSmeniService {

	@Autowired
	private USmeniRadnikRepository usmeniradiRepository;

	@Autowired
	private StoRepository stoRepository;

	@Autowired
	private RestoranRepository restoranRepository;

	@Autowired
	private MenadzerRepository menadzerRepository;

	public List<RadniciSmena> getByMonth(String month, int idRest) {
		List<RadniciSmena> retVal = new ArrayList<RadniciSmena>();
		Restoran r = restoranRepository.findOne(idRest);
		if (r == null) {
			return null;
		}
		Set<Smena> smeneRestorana = r.getSmene();
		Calendar c = Calendar.getInstance();
		for (Smena s : smeneRestorana) {
			Set<USmeniRadnik> radeUSmeni = s.getRade_u_smeni();
			for (USmeniRadnik usmr : radeUSmeni) {
				c.setTime(usmr.getDan());
				String god = Integer.toString(c.get(Calendar.YEAR));
				String mes = Integer.toString(c.get(Calendar.MONTH) + 1);
				String dan = Integer.toString(c.get(Calendar.DAY_OF_MONTH));
				if (mes.equals(month)) { // ako je mesec koji prosledjujem
											// jednak mesecu kod radnika
					RadniciSmena radniciSmena = new RadniciSmena();
					radniciSmena.setRadnikIme(usmr.getZaposlen().getIme());
					radniciSmena.setRadnikPrezime(usmr.getZaposlen().getPrezime());
					radniciSmena.setSmenaID(usmr.getSmena().getId());
					radniciSmena.setRadiUSmeniID(usmr.getId());
					radniciSmena
							.setDatumOD(god + "-" + srediDatum(mes) + "-" + dan + " " + usmr.getSmena().getOd_vreme());
					radniciSmena
							.setDatumOD(god + "-" + srediDatum(mes) + "-" + dan + " " + usmr.getSmena().getDo_vreme());
					retVal.add(radniciSmena);
				}
			}

		}
		
		return retVal;
	}

	public String srediDatum(String mesec) {
		String noviMesec = "";
		int mesecInd = Integer.parseInt(mesec);
		if (mesecInd < 10) {
			noviMesec = "0" + mesec;
		}
		return noviMesec;
	}
	
//	public List<RadniciSmena> findByMonthAndUser(int id, int mesec){
//		List<RadniciSmena> smene = new ArrayList<RadniciSmena>();
//		List<USmeniRadnik> radniciUSmeni = usmeniradiRepository.findByMonthAndUser(id, mesec);
//		Calendar c = Calendar.getInstance();
//		for(USmeniRadnik r : radniciUSmeni){
//			c.setTime(r.getDan());
//			String god = Integer.toString(c.get(Calendar.YEAR));
//			String mes = Integer.toString(c.get(Calendar.MONTH) + 1);
//			String dan = Integer.toString(c.get(Calendar.DAY_OF_MONTH));
//			RadniciSmena rs = new RadniciSmena();
//			rs.setRadnikIme(r.getZaposlen().getIme());
//			rs.setRadnikPrezime(r.getZaposlen().getPrezime());
//			rs.setSmenaID(r.getSmena().getId());
//			rs.setRadiUSmeniID(r.getId());
//			rs.setDatumOD(god + "-" + srediDatum(mes) + "-" + dan + " " + r.getSmena().getOd_vreme());
//			rs.setDatumOD(god + "-" + srediDatum(mes) + "-" + dan + " " + r.getSmena().getDo_vreme());
//			smene.add(rs);
//		}
//		return smene;
//	}
	
	public void update(List<StoDTO> stolovi, String email){  // odeci zadnja slova
//		boolean ret = false;
//		MenadzerRestorana m = menadzerRepository.findByEmail(email);
//		if(m == null){
//			ret = false;
//		}
//		Restoran r = m.getRadi_u();
//		if(r == null){
//			ret = false;
//		}
//		String naziv = stolovi.get(stolovi.size()-1).naziv;
//		int id = Integer.parseInt(naziv);	
		return;
	}
	
	public boolean smenaJeKonobarova(int idKon){
		USmeniRadnik r = usmeniradiRepository.findById(idKon);
		if(r != null){
			if(r.getZaposlen().getTipKorisnika().equals("KONOBAR")){
				return true;
			}else {
				return false;
			}
		}else {
			return false;
		}
	}
}
