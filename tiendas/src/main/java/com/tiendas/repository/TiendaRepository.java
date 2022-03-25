package com.tiendas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import com.tiendas.entity.Tienda;

@Repository
public interface TiendaRepository extends JpaRepository<Tienda,Integer>{
	List<Tienda> findAll();
	Tienda findById(int id);
	List<Tienda> findByDistrito(String distrito);

}
