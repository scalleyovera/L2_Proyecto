package com.example.LP2_Proyecto.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.LP2_Proyecto.entity.TortaEntity;
import com.example.LP2_Proyecto.repository.TortaRepository;
import com.example.LP2_Proyecto.service.TortaService;

import jakarta.transaction.Transactional;

@Service
public class TortaServiceImpl implements TortaService{
	
	@Autowired
	private TortaRepository tortaRepository; 
	
	@Override
	public List<TortaEntity> buscarTodasTortas() {
		return tortaRepository.findAll();
	}

	@Override
	public TortaEntity buscarTortaPorId(long id) {
		return tortaRepository.findById(id).get();
	}

	@Transactional
	public TortaEntity actualizarStock(Integer id, Integer cantidadComprada) {
		// Lógica para actualizar el stock del producto en la base de datos
        TortaEntity torta = buscarTortaPorId(id);
        int nuevoStock = torta.getStock() - cantidadComprada;
        torta.setStock(nuevoStock);
        tortaRepository.save(torta);
		return torta;
	}

	@Override
	public TortaEntity guardarTorta(TortaEntity torta) {
		// TODO Auto-generated method stub
		return tortaRepository.save(torta);
	}

	@Override
	public void eliminarTorta(long id) {
		tortaRepository.deleteById(id);
		
	}

}
