package com.example.service;

import com.example.beans.restoran.Restoran;

public interface RestoranMyService {
	 Iterable<Restoran> getAllRestoran();
	 Restoran getRestoranById(Integer id);
	 Restoran saveRestoran(Restoran contact);
	 void deleteRestoran(Integer id);
}
