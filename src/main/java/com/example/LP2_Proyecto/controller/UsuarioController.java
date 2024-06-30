package com.example.LP2_Proyecto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.LP2_Proyecto.entity.UsuarioEntity;
import com.example.LP2_Proyecto.service.UsuarioService;

import jakarta.servlet.http.HttpSession;

@Controller
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;

	@GetMapping("/registrar")
	public String showRegistrarUsuario(Model model) {

		model.addAttribute("usuario", new UsuarioEntity());
		return "registrar_usuario";
	}

	@PostMapping("/registrar")
	public String registrarUsuario(UsuarioEntity usuarioEntity, Model model, @RequestParam("foto") MultipartFile foto) {
		usuarioService.crearUsuario(usuarioEntity, model, foto);
		model.addAttribute("registroExitoso", "Usuario registrado exitosamente");
		return "registrar_usuario";
	}

	@GetMapping("/")
	public String index(Model model) {
		model.addAttribute("usuario", new UsuarioEntity());
		return "login";
	}

	@PostMapping("/login")
	public String login(UsuarioEntity usuarioEntity, Model model, HttpSession session) {
		boolean usuarioValido = usuarioService.validarUsuario(usuarioEntity, session);
		if (usuarioValido) {
			return "redirect:/menu";
		}
		model.addAttribute("loginInvalido", "Usuario No Existe");
		model.addAttribute("usuario", new UsuarioEntity());
		return "login";
	}

	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}

}
