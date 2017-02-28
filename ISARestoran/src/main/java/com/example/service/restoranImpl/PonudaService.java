package com.example.service.restoranImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.repository.PonudjacRepository;
import com.example.repository.restoran.PonudaRepository;

@Service
public class PonudaService {

	@Autowired
	private PonudjacRepository ponudjacRepository;
	
	@Autowired
	private PonudaRepository ponudaRepository;
	
	
}
