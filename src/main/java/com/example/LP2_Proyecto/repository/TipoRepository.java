package com.example.LP2_Proyecto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.LP2_Proyecto.entity.TipoEntity;

@Repository
public interface TipoRepository  extends JpaRepository<TipoEntity, Integer>{

}
