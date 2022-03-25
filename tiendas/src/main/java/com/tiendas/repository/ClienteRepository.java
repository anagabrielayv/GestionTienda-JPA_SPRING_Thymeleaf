package com.tiendas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tiendas.entity.Cliente;
import com.tiendas.entity.Usuario;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente,Integer>{
	List<Cliente> findAll();
	Cliente findById(int id);
	Cliente findByUsuario(Usuario usuario);
}
