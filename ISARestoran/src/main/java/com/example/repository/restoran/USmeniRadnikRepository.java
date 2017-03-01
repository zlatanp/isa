package com.example.repository.restoran;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.beans.restoran.USmeniRadnik;


public interface USmeniRadnikRepository extends JpaRepository<USmeniRadnik, Integer>{

	USmeniRadnik findById(int id);
	
	//@Query(value = "select r from RadiUSmeni r left join r.zaposlen z where ( z.id= :id and extract(month from r.dan) = :m)")
	//List<USmeniRadnik> findByMonthAndUser(@Param("m") int month, @Param("id") int id);
}
