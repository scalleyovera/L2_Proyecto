package com.example.LP2_Proyecto.controller;

import java.util.List;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.LP2_Proyecto.entity.TortaEntity;
import com.example.LP2_Proyecto.entity.UsuarioEntity;
import com.example.LP2_Proyecto.service.UsuarioService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

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
	
	@GetMapping("/agregar_usuario")
    public String mostrarFormularioRegistro(Model model) {
        List<UsuarioEntity> usua = usuarioService.buscarTodosUsuarios();
        model.addAttribute("usua", usua);
        
        UsuarioEntity usu = new UsuarioEntity();
        model.addAttribute("usu", usu);
        
        return "usua";
    }
	
	@PostMapping("/registrar_usuario")
    public String registrartrabajador(UsuarioEntity usuarioEntity, Model model, @RequestParam("foto") MultipartFile foto) {
		usuarioService.crearUsuario(usuarioEntity, model, foto);
		model.addAttribute("registroExitoso", "Usuario registrado exitosamente");
        return "redirect:/agregar_usuario";
    }
	
	@GetMapping("/usuarios/eliminar/{id}")
    public String eliminarUsuario(@PathVariable("id") Integer id) {
        usuarioService.eliminarUsuario(id);
        return "redirect:/agregar_usuario";
    }

    @GetMapping("/usuarios/editar/{id}")
    public String mostrarFormularioEdicion(HttpSession session, @PathVariable("id") Integer id, Model model) {
        UsuarioEntity usu = usuarioService.buscarUsuarioPorCodigo(id);
        if (usu == null) {
            return "redirect:/agregar_usuario";
        }
        model.addAttribute("usu", usu);
        return "usua";
    }
    @PostMapping("/usuarios/actualizar")
    public String actualizarUsuario(@ModelAttribute("usuario") @Valid UsuarioEntity usuario, Model model, BindingResult result, @RequestParam("foto") MultipartFile foto) {
        if (result.hasErrors()) {
            return "usua";
        }

        UsuarioEntity usuarioExistente = usuarioService.buscarUsuarioPorCodigo(usuario.getCodigo());
        if (usuarioExistente == null) {
            return "redirect:/agregar_usuario";
        }

        usuarioExistente.setNombre(usuario.getNombre());
        usuarioExistente.setApellido(usuario.getApellido());
        usuarioExistente.setEdad(usuario.getEdad());
        usuarioExistente.setTipo(usuario.getTipo());
        usuarioExistente.setUsuario(usuario.getUsuario());
        usuarioExistente.setClave(usuario.getClave());

        usuarioService.guardarTrabajador(usuarioExistente,model,foto);

        return "redirect:/agregar_usuario";
    }
}
