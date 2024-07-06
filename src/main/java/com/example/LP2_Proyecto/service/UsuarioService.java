package com.example.LP2_Proyecto.service;

import java.util.List;

import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import com.example.LP2_Proyecto.entity.UsuarioEntity;

import jakarta.servlet.http.HttpSession;

public interface UsuarioService {

	void crearUsuario(UsuarioEntity usuarioEntity, Model model, MultipartFile foto);

	boolean validarUsuario(UsuarioEntity usuarioEntity, HttpSession session);

	UsuarioEntity buscarUsuarioPorCodigo(Integer codigo);
	
	List<UsuarioEntity>buscarTodosUsuarios();
	public void guardarTrabajador(UsuarioEntity usu, Model model, MultipartFile foto);
	void eliminarUsuario(Integer id);
}