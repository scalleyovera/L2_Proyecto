package com.example.LP2_Proyecto.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import com.example.LP2_Proyecto.entity.UsuarioEntity;
import com.example.LP2_Proyecto.repository.UsuarioRepository;
import com.example.LP2_Proyecto.service.UsuarioService;

import jakarta.servlet.http.HttpSession;

@Service
public class UsuarioServiceImlp implements UsuarioService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;

	@Override
	public void crearUsuario(UsuarioService usuarioService, Model model, MultipartFile foto) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean validarUsuario(UsuarioEntity usuarioEntity, HttpSession session) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public UsuarioEntity buscarUsuarioPorCodigo(Integer codigo) {
		// TODO Auto-generated method stub
		return null;
	}

}
