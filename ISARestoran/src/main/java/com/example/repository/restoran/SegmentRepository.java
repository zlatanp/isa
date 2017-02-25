package com.example.repository.restoran;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.beans.restoran.Segment;
import com.example.enums.TipSegmenta;

public interface SegmentRepository extends JpaRepository<Segment, Integer>{

	Segment findById(int id);
	Segment findByTip(TipSegmenta tip);
}
