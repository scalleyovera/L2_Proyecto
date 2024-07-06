package com.example.LP2_Proyecto.service;

import java.util.List;

import com.example.LP2_Proyecto.entity.TortaEntity;

public interface TortaService {
	List<TortaEntity>buscarTodasTortas();
	TortaEntity buscarTortaPorId(long id);
	TortaEntity actualizarStock (Integer id, Integer cantidadComprada);
	TortaEntity guardarTorta(TortaEntity torta);
	void eliminarTorta(long id);
}
