package com.example.LP2_Proyecto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.LP2_Proyecto.entity.UsuarioEntity;
import com.example.LP2_Proyecto.etiquetas.MenuUtil;
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
			return "redirect:/menuHtml";
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
	
	 @Autowired
	    private MenuUtil menuUtil;

	    @GetMapping("/menuHtml")
	    public String menuHtml(UsuarioEntity usuarioEntity,Model model, HttpSession session) {
	        // Supongamos que el usuario autenticado se guarda en la sesión
	    	boolean usuario = usuarioService.validarUsuario(usuarioEntity, session);

			//Revisando si el usuario pasa
			Integer cod = (Integer) session.getAttribute("usuario");
			System.out.println("El codigo de usuario es " + cod);

			//Guardo el Tipo de usuario en sesion para llevarlo a MenuHtml
			Integer tipo = (Integer) session.getAttribute("tipo");


	        if (usuario = true) {
	            String menuH = menuUtil.generarMenu(usuarioEntity, tipo, session);
	            model.addAttribute("menuH", menuH);
	        } else {
				System.out.println("No se encuentra usuario true  " + cod);
			}

	        return "menu";
	    }
}
