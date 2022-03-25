package com.tiendas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tiendas.entity.Colaborador;

@Repository
public interface ColaboradorRepository extends JpaRepository<Colaborador,Integer>{
	List<Colaborador> findAll();
	Colaborador findByDni(String dni);
	List<Colaborador> findByApellidoPaterno(String apellidoPaterno);
	Colaborador findById(int id);
	
}
