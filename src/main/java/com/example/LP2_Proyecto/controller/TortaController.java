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

import com.example.LP2_Proyecto.entity.BocaditoEntity;
import com.example.LP2_Proyecto.entity.BoletaEntity;

import com.example.LP2_Proyecto.entity.TortaEntity;
import com.example.LP2_Proyecto.entity.UsuarioEntity;
import com.example.LP2_Proyecto.etiquetas.MenuUtil;
import com.example.LP2_Proyecto.service.BocaditoService;
import com.example.LP2_Proyecto.service.TortaService;
import com.example.LP2_Proyecto.service.UsuarioService;
import com.example.LP2_Proyecto.model.Pedido;

import jakarta.validation.Valid;

import jakarta.servlet.http.HttpSession;

@Controller
public class TortaController {

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private TortaService tortaService;
	
	@Autowired
    private BocaditoService bocaditoService;
	
	@Autowired
	private MenuUtil menuUtil;

	@GetMapping("/menu")
	public String showMenu(UsuarioEntity usuarioEntity, HttpSession session, Model model) {
	    if (session.getAttribute("usuario") == null) {
	        return "redirect:/";
	    }

	    // Validar y obtener el código de usuario desde la sesión
	    Integer cod = (Integer) session.getAttribute("usuario");
	    System.out.println("El código de usuario es " + cod);

	    // Obtener y guardar el tipo de usuario en sesión para llevarlo a menu.html
	    Integer tipo = (Integer) session.getAttribute("tipo");

	    // Generar el menú según el tipo de usuario y guardar en el modelo
	    String menuH = menuUtil.generarMenu(usuarioEntity, tipo, session);
	    model.addAttribute("menuH", menuH);

	    // Obtener y guardar la foto del usuario en el modelo
	    UsuarioEntity usu = usuarioService.buscarUsuarioPorCodigo(cod);
	    model.addAttribute("foto", usu.getUrlImagen());

	    // Obtener y guardar todas las tortas disponibles
	    List<TortaEntity> tortas = tortaService.buscarTodasTortas();
	    model.addAttribute("tortas", tortas);

	    // Obtener y guardar todos los bocaditos disponibles
	    List<BocaditoEntity> bocaditos = bocaditoService.buscarTodosBocaditos();
	    model.addAttribute("bocaditos", bocaditos);

	    // Obtener y guardar la cantidad de productos en el carrito
	    List<Pedido> pedidoSession = (List<Pedido>) session.getAttribute("carrito");
	    int cant_carrito = pedidoSession != null ? pedidoSession.size() : 0;
	    model.addAttribute("cant_carrito", cant_carrito);

	    // Preparar la lista de boletas para mostrar en el carrito con datos
	    List<BoletaEntity> boletaEntityList = new ArrayList<>();
	    Double total = 0.0;

	    if (pedidoSession != null) {
	        for (Pedido pedido : pedidoSession) {
	            BoletaEntity boletaEntity = new BoletaEntity();
	            TortaEntity tortaEntity = tortaService.buscarTortaPorId(pedido.getProductoId());
	            boletaEntity.setTortaEntity(tortaEntity);
	            boletaEntity.setCantidad(pedido.getCantidad());
	            boletaEntityList.add(boletaEntity);
	            total += pedido.getCantidad() * tortaEntity.getPrecio();
	        }
	    }

	    // Guardar la lista de boletas y el total en el modelo
	    model.addAttribute("carrito", boletaEntityList);
	    model.addAttribute("total", total);

	    return "menu";
	}

	@GetMapping("/obtener")
	public String mostrarDetallesProducto(HttpSession session, @RequestParam("cod") Integer idTorta, Model model) {

		String idUsuarioString = session.getAttribute("usuario").toString();
		int idUsuario = Integer.parseInt(idUsuarioString);

		UsuarioEntity usuarioEntity = usuarioService.buscarUsuarioPorCodigo(idUsuario);
		model.addAttribute("foto", usuarioEntity.getUrlImagen());

		// numero pedido
		List<Pedido> pedidoSession = null;
		if (session.getAttribute("carrito") == null) {
			pedidoSession = new ArrayList<Pedido>();
		} else {
			pedidoSession = (List<Pedido>) session.getAttribute("carrito");
		}
		model.addAttribute("cant_carrito", pedidoSession.size());

		// Obtener detalles del producto utilizando el servicio
		TortaEntity torta = tortaService.buscarTortaPorId(idTorta);
		model.addAttribute("torta", torta);
		return "detalles";
	}
	@GetMapping("/agregar_torta")
    public String mostrarFormularioRegistro(UsuarioEntity usuarioEntity,HttpSession session, Model model) {
		// Supongamos que el usuario autenticado se guarda en la sesión
    	boolean usuario = usuarioService.validarUsuario(usuarioEntity, session);

		//Revisando si el usuario pasa
		Integer cod = (Integer) session.getAttribute("usuario");
		System.out.println("El codigo de usuario es " + cod);

		//Guardo el Tipo de usuario en sesion para llevarlo a MenuHtml
		Integer tipo = (Integer) session.getAttribute("tipo");


        if (usuario = true) {
            String menuAgre = menuUtil.generarMenu(usuarioEntity, tipo, session);
            model.addAttribute("menuAgre", menuAgre);
        } else {
			System.out.println("No se encuentra usuario true  " + cod);
		}

		UsuarioEntity usu = usuarioService.buscarUsuarioPorCodigo(cod);
		model.addAttribute("foto", usu.getUrlImagen());
		
        List<TortaEntity> tortas = tortaService.buscarTodasTortas();
        model.addAttribute("tortas", tortas);
        
        TortaEntity torta = new TortaEntity();
        model.addAttribute("torta", torta);
        
        return "tortas";
    }
    
    @PostMapping("/registrar_torta")
    public String registrarTorta(@ModelAttribute("torta") @Valid TortaEntity torta, BindingResult result) {
        if (result.hasErrors()) {
            return "tortas";
        }
        tortaService.guardarTorta(torta);
        return "redirect:/agregar_torta";
    }
    
    @GetMapping("/delete/{id}")
    public String mostrarDelete(HttpSession session, @PathVariable("id") Integer id, Model model) {
        TortaEntity torta = tortaService.buscarTortaPorId(id);
        if (torta == null) {
            return "redirect:/agregar_torta";
        }
        tortaService.eliminarTorta(id);
        return "redirect:/agregar_torta";
    }
    

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEdicion(HttpSession session, @PathVariable("id") Integer id, Model model) {
        TortaEntity torta = tortaService.buscarTortaPorId(id);
        if (torta == null) {
            return "redirect:/agregar_torta";
        }
        model.addAttribute("torta", torta);
        return "tortas";
    }
    
    @PostMapping("/actualizar")
    public String actualizarProducto(@ModelAttribute("torta") @Valid TortaEntity torta, BindingResult result) {
        if (result.hasErrors()) {
            return "tortas";
        }

        TortaEntity tortaExistente = tortaService.buscarTortaPorId(torta.getIdtorta());
        if (tortaExistente == null) {
            return "redirect:/agregar_torta";
        }

        tortaExistente.setNombre(torta.getNombre());
        tortaExistente.setPrecio(torta.getPrecio());
        tortaExistente.setStock(torta.getStock());
        tortaExistente.setUrlImagen(torta.getUrlImagen());

        tortaService.guardarTorta(tortaExistente);

        return "redirect:/agregar_torta";
    }
	
	

}