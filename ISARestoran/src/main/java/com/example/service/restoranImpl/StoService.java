package com.example.service.restoranImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.beans.korisnici.MenadzerRestorana;
import com.example.beans.restoran.Restoran;
import com.example.beans.restoran.Segment;
import com.example.beans.restoran.Sto;
import com.example.dto.restoran.StoDTO;
import com.example.repository.MenadzerRepository;
import com.example.repository.restoran.RestoranRepository;
import com.example.repository.restoran.SegmentRepository;
import com.example.repository.restoran.StoRepository;

@Service
public class StoService {
	
	@Autowired
	private StoRepository stoRepository;
	
	@Autowired
	private RestoranRepository restoranRepository;
	
	@Autowired
	private SegmentRepository segmentRepository;
	
	@Autowired
	private MenadzerRepository menadzerRepository;
	
	
	public StoDTO findById(int id){
		Sto sto = stoRepository.findById(id);
		if(sto == null)
			return null;
		return new StoDTO(sto);
	}
	
	public StoDTO findByName(String s){
		Sto sto = stoRepository.findByNaziv(s);
		if(sto == null)
			return null;
		return new StoDTO(sto);
	}
	
	public void create(StoDTO sto, String email){
		MenadzerRestorana menadzer = menadzerRepository.findByEmail(email);
		if(menadzer == null)
			return;
		Restoran restoran = menadzer.getRadi_u();
		Sto zaBazu = stoRepository.findByNaziv(sto.naziv);
		
		if(zaBazu == null){		
			Sto stoNovi = new Sto(sto.naziv, sto.brojStolica, sto.tipSegmenta);
			Segment seg = segmentRepository.findByTip(stoNovi.getTipSegmenta());
			
			if(seg == null){	
				Segment segmentNovi = new Segment(restoran, stoNovi.getTipSegmenta(), stoNovi);
				stoNovi.setSegment(segmentNovi);
				segmentRepository.save(segmentNovi);
				stoRepository.save(stoNovi);
			
			}else{
				seg.getStolovi().add(stoNovi);
				stoNovi.setSegment(seg);
				stoRepository.save(stoNovi);
			}
		
		}else {
			zaBazu.getSegment().getStolovi().remove(zaBazu);
			zaBazu.setNaziv(sto.naziv);
			zaBazu.setBrojStolica(sto.brojStolica);
			zaBazu.setTipSegmenta(sto.tipSegmenta);
			Segment segm = segmentRepository.findByTip(sto.tipSegmenta);
			
			if(segm == null){
				Segment noviSegment = new Segment(restoran, zaBazu.getTipSegmenta(), zaBazu);
				zaBazu.setSegment(noviSegment);
				segmentRepository.save(noviSegment);
				stoRepository.save(zaBazu);
			}else {
				segm.getStolovi().add(zaBazu);
				zaBazu.setSegment(segm);
				stoRepository.save(zaBazu);
			}
		}
	}
	
	public String getNazivStola(int id){
		Sto sto = stoRepository.findById(id);
		if(sto == null)
			return null;
		return sto.getNaziv();
	}
}
