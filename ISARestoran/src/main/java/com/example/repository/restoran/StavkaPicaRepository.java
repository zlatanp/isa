package com.example.repository.restoran;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.beans.restoran.StavkaPica;


public interface StavkaPicaRepository extends JpaRepository<StavkaPica, Integer>{

	@Modifying
	@Transactional
	@Query(value="delete from stavka_pica where narudzbina_stavke_pica = :id", nativeQuery=true)
	void deleteByNarudzbina(@Param("id") int id);
	
	@Query(value = "select * from stavka_pica sp where sp.Spremio = :id and sp.Spremljeno is FALSE", nativeQuery = true)
	List<StavkaPica> findAllByKonobar(@Param("id") int id);
}
