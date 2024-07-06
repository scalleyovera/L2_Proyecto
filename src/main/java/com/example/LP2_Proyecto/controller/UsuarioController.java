package com.example.LP2_Proyecto.controller;

import java.util.ArrayList;
import java.util.List;

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

import com.example.LP2_Proyecto.entity.TipoEntity;
import com.example.LP2_Proyecto.entity.UsuarioEntity;
import com.example.LP2_Proyecto.etiquetas.MenuUtil;
import com.example.LP2_Proyecto.model.Pedido;
import com.example.LP2_Proyecto.service.TipoService;
import com.example.LP2_Proyecto.service.UsuarioService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private TipoService tipoService;

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

	@Autowired
	private MenuUtil menuUtil;

	@GetMapping("/menuHtml")
	public String menuHtml(UsuarioEntity usuarioEntity, Model model, HttpSession session) {
		// Supongamos que el usuario autenticado se guarda en la sesión
		boolean usuario = usuarioService.validarUsuario(usuarioEntity, session);

		// Revisando si el usuario pasa
		Integer cod = (Integer) session.getAttribute("usuario");
		System.out.println("El codigo de usuario es " + cod);

		// Guardo el Tipo de usuario en sesion para llevarlo a MenuHtml
		Integer tipo = (Integer) session.getAttribute("tipo");

		if (usuario = true) {
			String menuHtml = menuUtil.generarMenu(usuarioEntity, tipo, session);
			model.addAttribute("menuHtml", menuHtml);
		} else {
			System.out.println("No se encuentra usuario true  " + cod);
		}

		UsuarioEntity usu = usuarioService.buscarUsuarioPorCodigo(cod);
		model.addAttribute("foto", usu.getUrlImagen());

		// numero pedido
		List<Pedido> pedidoSession = null;
		if (session.getAttribute("carrito") == null) {
			pedidoSession = new ArrayList<Pedido>();
		} else {
			pedidoSession = (List<Pedido>) session.getAttribute("carrito");
		}
		model.addAttribute("cant_carrito", pedidoSession.size());

		return "inicio";
	}

	@GetMapping("/nosotroHtml")
	public String nosotroHtml(UsuarioEntity usuarioEntity, Model model, HttpSession session) {
		// Supongamos que el usuario autenticado se guarda en la sesión
		boolean usuario = usuarioService.validarUsuario(usuarioEntity, session);

		// Revisando si el usuario pasa
		Integer cod = (Integer) session.getAttribute("usuario");
		System.out.println("El codigo de usuario es " + cod);

		// Guardo el Tipo de usuario en sesion para llevarlo a MenuHtml
		Integer tipo = (Integer) session.getAttribute("tipo");

		if (usuario = true) {
			String nosotroHtml = menuUtil.generarMenu(usuarioEntity, tipo, session);
			model.addAttribute("nosotroHtml", nosotroHtml);
		} else {
			System.out.println("No se encuentra usuario true  " + cod);
		}

		UsuarioEntity usu = usuarioService.buscarUsuarioPorCodigo(cod);
		model.addAttribute("foto", usu.getUrlImagen());

		// numero pedido
		List<Pedido> pedidoSession = null;
		if (session.getAttribute("carrito") == null) {
			pedidoSession = new ArrayList<Pedido>();
		} else {
			pedidoSession = (List<Pedido>) session.getAttribute("carrito");
		}
		model.addAttribute("cant_carrito", pedidoSession.size());

		return "nosotros";
	}

	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}

	@GetMapping("/agregar_usuario")
	public String mostrarFormularioRegistro(UsuarioEntity usuarioEntity, Model model, HttpSession session) {

		// Supongamos que el usuario autenticado se guarda en la sesión
		boolean usuario = usuarioService.validarUsuario(usuarioEntity, session);

		// Revisando si el usuario pasa
		Integer cod = (Integer) session.getAttribute("usuario");
		System.out.println("El codigo de usuario es " + cod);

		// Guardo el Tipo de usuario en sesion para llevarlo a MenuHtml
		Integer tipo = (Integer) session.getAttribute("tipo");

		if (usuario = true) {
			String usuHtml = menuUtil.generarMenu(usuarioEntity, tipo, session);
			model.addAttribute("usuHtml", usuHtml);
		} else {
			System.out.println("No se encuentra usuario true  " + cod);
		}

		UsuarioEntity usuari = usuarioService.buscarUsuarioPorCodigo(cod);
		model.addAttribute("foto", usuari.getUrlImagen());

		List<UsuarioEntity> usua = usuarioService.buscarTodosUsuarios();
		model.addAttribute("usua", usua);
		
		List<TipoEntity> tipos = tipoService.findAllTipos();
		model.addAttribute("tipos", tipos);

		UsuarioEntity usu = new UsuarioEntity();
		model.addAttribute("usu", usu);

		return "usua";
	}

	@PostMapping("/registrar_usuario")
	public String registrartrabajador(UsuarioEntity usuarioEntity, Model model,
			@RequestParam("foto") MultipartFile foto) {
		TipoEntity tipo = tipoService.findTipoById(usuarioEntity.getTipo().getIdtipo());
		usuarioEntity.setTipo(tipo);
		
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
		List<TipoEntity> tipos = tipoService.findAllTipos();
		model.addAttribute("tipos", tipos);
		
		model.addAttribute("usu", usu);
		return "redirect:/agregar_usuario";
	}

	@PostMapping("/usuarios/actualizar")
	public String actualizarUsuario(@ModelAttribute("usuario") @Valid UsuarioEntity usuario, Model model,
			BindingResult result, @RequestParam("foto") MultipartFile foto) {
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
		
		TipoEntity tipo = tipoService.findTipoById(usuario.getTipo().getIdtipo());
		usuario.setTipo(tipo);

		usuarioService.guardarTrabajador(usuarioExistente, model, foto);

		return "redirect:/agregar_usuario";
	}
}