package com.example.service.restoranImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.beans.restoran.Segment;
import com.example.repository.restoran.SegmentRepository;

@Service
public class SegmentService {

	@Autowired
	private SegmentRepository segmentRepository;
	
	public void create(Segment segment){
		segmentRepository.save(segment);
	}
}

