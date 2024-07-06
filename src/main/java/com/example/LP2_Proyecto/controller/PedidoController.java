package com.example.LP2_Proyecto.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.LP2_Proyecto.repository.*;
import com.example.LP2_Proyecto.service.TortaService;
import com.example.LP2_Proyecto.service.UsuarioService;
import com.example.LP2_Proyecto.service.impl.PdfService;
import com.example.LP2_Proyecto.entity.BoletaEntity;
import com.example.LP2_Proyecto.entity.PedidoEntity;
import com.example.LP2_Proyecto.entity.TortaEntity;
import com.example.LP2_Proyecto.entity.UsuarioEntity;
import com.example.LP2_Proyecto.etiquetas.MenuUtil;
import com.example.LP2_Proyecto.model.Pedido;

import jakarta.servlet.http.HttpSession;

@Controller
public class PedidoController {

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private TortaService tortaService;

	@Autowired
	private PdfService pdfService;

	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private MenuUtil menuUtil;

	@PostMapping("/venta")
	public String procesarVenta(HttpSession session, @RequestParam("cantidad") String cantidad,
			@RequestParam("idTorta") String idTorta) {
		List<Pedido> p = null;
		if (session.getAttribute("carrito") == null) {
			p = new ArrayList<Pedido>();
		} else {
			p = (List<Pedido>) session.getAttribute("carrito");
		}

		Integer cantidadComprada = Integer.parseInt(cantidad);
		Integer idTortaInt = Integer.parseInt(idTorta);
		Pedido pedido = new Pedido(cantidadComprada, idTortaInt);
		p.add(pedido);
		session.setAttribute("carrito", p);

		return "redirect:/menu";
	}

	@GetMapping("/tienda")
	public String showtienda(HttpSession session, Model model) {
		if (session.getAttribute("usuario") == null) {
			return "redirect:/";
		}

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

		// ver carrito con datos
		List<BoletaEntity> boletaEntityList = new ArrayList<BoletaEntity>();
		Double total = 0.0;

		for (Pedido pedido : pedidoSession) {
			BoletaEntity boletaEntity = new BoletaEntity();
			TortaEntity tortaEntity = tortaService.buscarTortaPorId(pedido.getProductoId());
			boletaEntity.setTortaEntity(tortaEntity);
			boletaEntity.setCantidad(pedido.getCantidad());
			boletaEntityList.add(boletaEntity);
			total += pedido.getCantidad() * tortaEntity.getPrecio();
		}

		model.addAttribute("carrito", boletaEntityList);
		model.addAttribute("total", total);

		boolean usuario = usuarioService.validarUsuario(usuarioEntity, session);
		// Revisando si el usuario pasa
		Integer cod = (Integer) session.getAttribute("usuario");
		System.out.println("El codigo de usuario es " + cod);

		// Guardo el Tipo de usuario en sesion para llevarlo a MenuHtml
		Integer tipo = (Integer) session.getAttribute("tipo");

		if (usuario = true) {
			String tiendaHtml = menuUtil.generarMenu(usuarioEntity, tipo, session);
			model.addAttribute("tiendaHtml", tiendaHtml);
		} else {
			System.out.println("No se encuentra usuario true  " + cod);
		}

		return "tienda";
	}


	@GetMapping("/generar_pdf")
	public ResponseEntity<InputStreamResource> generarPdf(HttpSession session) throws IOException {
		List<Pedido> pedidoSession = null;
		if (session.getAttribute("carrito") == null) {
			pedidoSession = new ArrayList<Pedido>();
		} else {
			pedidoSession = (List<Pedido>) session.getAttribute("carrito");
		}
		List<BoletaEntity> boletaEntityList = new ArrayList<BoletaEntity>();
		Double total = 0.0;

		for (Pedido pedido : pedidoSession) {
			BoletaEntity boletaEntity = new BoletaEntity();
			TortaEntity tortaEntity = tortaService.buscarTortaPorId(pedido.getProductoId());
			boletaEntity.setTortaEntity(tortaEntity);
			boletaEntity.setCantidad(pedido.getCantidad());
			boletaEntityList.add(boletaEntity);
			total += pedido.getCantidad() * tortaEntity.getPrecio();
		}
		Map<String, Object> datosPdf = new HashMap<String, Object>();
		datosPdf.put("factura", boletaEntityList);
		datosPdf.put("precio_total", total);

		ByteArrayInputStream pdfBytes = pdfService.generarPdfDeHtml("template_pdf", datosPdf);

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Content-Disposition", "inline; filename=productos.pdf");
		return ResponseEntity.ok().headers(httpHeaders).contentType(MediaType.APPLICATION_PDF)
				.body(new InputStreamResource(pdfBytes));
	}

	@PostMapping("/eliminar")
	public String eliminarDelCarrito(@RequestParam("idTorta") Integer idTorta, HttpSession session) {
		if (idTorta == null) {
			System.out.println("idTorta es null");
			return "redirect:/menu";
		}

		System.out.println("idTorta: " + idTorta);

		List<Pedido> carrito = (List<Pedido>) session.getAttribute("carrito");
		if (carrito != null) {
			carrito.removeIf(ped -> ped.getProductoId().equals(idTorta));
			session.setAttribute("carrito", carrito);
		}

		return "redirect:/tienda";
	}

	@PostMapping("/pagar")
	public String guardarFactura(HttpSession session) {
		String idUsuarioString = session.getAttribute("usuario").toString();
		int idUsuario = Integer.parseInt(idUsuarioString);

		UsuarioEntity usuarioEntity = usuarioService.buscarUsuarioPorCodigo(idUsuario);
		usuarioEntity.setUsuario(idUsuarioString);

		PedidoEntity pedidoEntity = new PedidoEntity();
		pedidoEntity.setFechaCompra(LocalDate.now());
		pedidoEntity.setUsuarioEntity(usuarioEntity);

		List<BoletaEntity> boletaEntityList = new ArrayList<BoletaEntity>();
		Double total = 0.0;

		List<Pedido> pedidoSession = null;
		if (session.getAttribute("carrito") == null) {
			pedidoSession = new ArrayList<Pedido>();
		} else {
			pedidoSession = (List<Pedido>) session.getAttribute("carrito");
		}

		for (Pedido pedido : pedidoSession) {
			BoletaEntity boletaEntity = new BoletaEntity();
			TortaEntity tortaEntity = new TortaEntity();
			tortaEntity.setIdtorta(pedido.getProductoId().longValue());

			boletaEntity.setTortaEntity(tortaEntity);
			boletaEntity.setCantidad(pedido.getCantidad());
			boletaEntity.setPedidoEntity(pedidoEntity);
			boletaEntityList.add(boletaEntity);
		}
		pedidoEntity.setBoleta(boletaEntityList);
		pedidoRepository.save(pedidoEntity);

		session.removeAttribute("carrito");
		return "redirect:/tienda";
	}
}
