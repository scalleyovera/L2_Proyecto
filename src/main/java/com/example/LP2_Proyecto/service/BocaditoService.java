package com.example.LP2_Proyecto.service;

import java.util.List;

import com.example.LP2_Proyecto.entity.BocaditoEntity;

public interface BocaditoService {
    List<BocaditoEntity> buscarTodosBocaditos();
    BocaditoEntity buscarBocaditoPorId(Long id);
    BocaditoEntity actualizarStock(Long id, Integer cantidadComprada);
    BocaditoEntity guardarBocadito(BocaditoEntity bocadito);
    void eliminarBocadito(Long id);
}
