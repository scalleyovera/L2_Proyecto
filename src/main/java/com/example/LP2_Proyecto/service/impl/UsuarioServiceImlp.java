package com.example.LP2_Proyecto.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import com.example.LP2_Proyecto.entity.TipoEntity;
import com.example.LP2_Proyecto.entity.UsuarioEntity;
import com.example.LP2_Proyecto.repository.TipoRepository;
import com.example.LP2_Proyecto.repository.UsuarioRepository;
import com.example.LP2_Proyecto.service.UsuarioService;
import com.example.LP2_Proyecto.utils.Utilitarios;

import jakarta.servlet.http.HttpSession;

@Service
public class UsuarioServiceImlp implements UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private TipoRepository tipoRepository;

	@Override
	public void crearUsuario(UsuarioEntity usuarioEntity, Model model, MultipartFile foto) {
		// Obtener el tipo de usuario predeterminado
		TipoEntity tipoPredeterminado = tipoRepository.findById(2)
				.orElseThrow(() -> new RuntimeException("Tipo de usuario por defecto no encontrado"));

		// Asignar el tipo de usuario predeterminado al usuario
		usuarioEntity.setTipo(tipoPredeterminado);

		// Guardar foto
		String nombreFoto = Utilitarios.guardarImagen(foto);

		usuarioEntity.setUrlImagen(nombreFoto);

		// Hash password
		String passwordHash = Utilitarios.extraerHash(usuarioEntity.getClave());
		usuarioEntity.setClave(passwordHash);

		// Guardar usuario
		usuarioRepository.save(usuarioEntity);

		// Responder a la vista
		model.addAttribute("registroCorrecto", "Registro Exitoso");
		model.addAttribute("usuario", new UsuarioEntity());

	}

	@Override
	public boolean validarUsuario(UsuarioEntity usuarioEntity, HttpSession session) {
	    UsuarioEntity usuarioEncontrado = usuarioRepository.findByUsuario(usuarioEntity.getUsuario());
	    System.out.println("Usuario encontrado: " + usuarioEncontrado);
	    if (usuarioEncontrado == null) {
	        return false;
	    }
	    
	    // Validar si el pássword hace match con el password de base de datos
	    if (!Utilitarios.checkPassword(usuarioEntity.getClave(), usuarioEncontrado.getClave())) {
	        return false;
	    }
	    
	    session.setAttribute("usuario", usuarioEncontrado.getCodigo());
	    return true;
	}

	@Override
	public UsuarioEntity buscarUsuarioPorCodigo(Integer codigo) {
		// TODO Auto-generated method stub
		return usuarioRepository.findByCodigo(codigo);
	}

}
