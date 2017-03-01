package com.example.service;

import com.example.beans.restoran.Sto;

public interface StoMyService {
	Iterable<Sto> getAllStolovi();
	Sto getRestoranById(Integer id);
	Sto saveRestoran(Sto contact);
	void deleteSto(Integer id);
}
