package com.example.service.korisniciImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.beans.korisnici.Kuvar;
import com.example.beans.restoran.Restoran;

import com.example.dto.korisnici.KuvarDTO;
import com.example.repository.KuvarRepository;
import com.example.repository.restoran.RestoranRepository;

@Service
public class KuvarService {

	@Autowired
	private KuvarRepository kuvarRepository;

	@Autowired
	private RestoranRepository restoranRepository;

	
	
	public Kuvar create(KuvarDTO kuvarDTO) {
		Restoran restoran = restoranRepository.findOne(kuvarDTO.getRadi_u());
		if (restoran == null) {
			return null;
		}
		Kuvar noviKuvar = new Kuvar(kuvarDTO.getIme(), kuvarDTO.getPrezime(), kuvarDTO.getEmail(),
				kuvarDTO.getPassword(), kuvarDTO.getTip(), kuvarDTO.getDatum_rodjenja(),
				kuvarDTO.getKonfekcijski_broj(), kuvarDTO.getVelicina_obuce(), restoran);

		noviKuvar = kuvarRepository.save(noviKuvar);
		return noviKuvar;
	}

	public KuvarDTO findByEmail(String email) {
		KuvarDTO k = new KuvarDTO(kuvarRepository.findByEmail(email));
		return k;
	}

	public KuvarDTO findByEmailAndPassword(String email, String password) {
		KuvarDTO k = new KuvarDTO(kuvarRepository.findByEmailAndPassword(email, password));
		return k;
	}

	public List<KuvarDTO> findAll() {
		List<Kuvar> listaKuvara = kuvarRepository.findAll();
		List<KuvarDTO> kuvariDTO = new ArrayList<KuvarDTO>();
		if (listaKuvara == null)
			return null;
		for (Kuvar k : listaKuvara) {
			kuvariDTO.add(new KuvarDTO(k));
		}

		return kuvariDTO;
	}

}
