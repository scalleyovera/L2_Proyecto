package com.example.LP2_Proyecto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.LP2_Proyecto.entity.TortaEntity;

@Repository
public interface TortaRepository extends JpaRepository<TortaEntity, Long> {

}
