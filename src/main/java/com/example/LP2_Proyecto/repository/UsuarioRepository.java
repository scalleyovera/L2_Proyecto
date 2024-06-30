package com.example.LP2_Proyecto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.LP2_Proyecto.entity.UsuarioEntity;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Integer> {
	UsuarioEntity findByCodigo(Integer codigo);
	
	UsuarioEntity findByUsuarioAndClave(String usuario, String clave);
}
