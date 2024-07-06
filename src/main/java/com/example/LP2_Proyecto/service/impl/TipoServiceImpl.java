package com.example.LP2_Proyecto.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.LP2_Proyecto.entity.TipoEntity;
import com.example.LP2_Proyecto.repository.TipoRepository;
import com.example.LP2_Proyecto.service.TipoService;

@Service
public class TipoServiceImpl implements TipoService{
	
	@Autowired
	private TipoRepository tipoRepository;
	
	@Override
	public List<TipoEntity> findAllTipos() {
		// TODO Auto-generated method stub
		return tipoRepository.findAll();
	}

	
}
