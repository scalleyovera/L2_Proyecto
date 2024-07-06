package com.example.LP2_Proyecto.service;

import java.util.List;

import com.example.LP2_Proyecto.entity.TipoEntity;

public interface TipoService {
	List<TipoEntity> findAllTipos();
	TipoEntity findTipoById(Integer id);

}