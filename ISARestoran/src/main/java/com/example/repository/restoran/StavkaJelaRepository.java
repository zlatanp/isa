package com.example.repository.restoran;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.beans.restoran.StavkaJela;


public interface StavkaJelaRepository extends JpaRepository<StavkaJela, Integer>{

	@Modifying
	@Transactional
	@Query(value="delete from stavka_jelo where narudzbina_stavke_jela = :id", nativeQuery=true)
	void deleteByNarudzbina(@Param("id") int id);
	
	@Query(value = "select * from stavka_jelo sj where sj.Skuvao = :id and sj.Spremljeno is FALSE", nativeQuery = true)
	List<StavkaJela> findAllByKonobar(@Param("id") int id);
}
