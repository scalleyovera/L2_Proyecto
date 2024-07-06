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
import com.example.LP2_Proyecto.entity.UsuarioEntity;
import com.example.LP2_Proyecto.etiquetas.MenuUtil;
import com.example.LP2_Proyecto.service.BocaditoService;
import com.example.LP2_Proyecto.service.UsuarioService;

import jakarta.validation.Valid;
import jakarta.servlet.http.HttpSession;

@Controller
public class BocaditoController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private BocaditoService bocaditoService;
    
    @Autowired
    private MenuUtil menuUtil;

    @GetMapping("/bocaditos")
    public String showBocaditos(HttpSession session, Model model) {
        if (session.getAttribute("usuario") == null) {
            return "redirect:/";
        }

        Integer cod = (Integer) session.getAttribute("usuario");
        UsuarioEntity usuarioEntity = usuarioService.buscarUsuarioPorCodigo(cod);

        if (usuarioEntity == null) {
            return "redirect:/";
        }

        Integer tipo = (Integer) session.getAttribute("tipo");

        String menuH = menuUtil.generarMenu(usuarioEntity, tipo, session);
        model.addAttribute("menuH", menuH);
        model.addAttribute("foto", usuarioEntity.getUrlImagen());

        List<BocaditoEntity> bocaditos = bocaditoService.buscarTodosBocaditos();
        model.addAttribute("bocaditos", bocaditos);

        return "bocaditos";
    }

    @GetMapping("/obtenerBocadito")
    public String mostrarDetallesBocadito(HttpSession session, @RequestParam("cod") Long id, Model model) {
        if (session.getAttribute("usuario") == null) {
            return "redirect:/";
        }

        String idUsuarioString = session.getAttribute("usuario").toString();
        int idUsuario = Integer.parseInt(idUsuarioString);

        UsuarioEntity usuarioEntity = usuarioService.buscarUsuarioPorCodigo(idUsuario);
        model.addAttribute("foto", usuarioEntity.getUrlImagen());

        BocaditoEntity bocadito = bocaditoService.buscarBocaditoPorId(id);
        model.addAttribute("bocadito", bocadito);

        return "detalles";
    }

    @GetMapping("/agregar_bocadito")
    public String mostrarFormularioRegistro(HttpSession session, Model model) {
        if (session.getAttribute("usuario") == null) {
            return "redirect:/";
        }

        Integer cod = (Integer) session.getAttribute("usuario");
        UsuarioEntity usuarioEntity = usuarioService.buscarUsuarioPorCodigo(cod);

        if (usuarioEntity == null) {
            return "redirect:/";
        }

        Integer tipo = (Integer) session.getAttribute("tipo");

        String menuAgre = menuUtil.generarMenu(usuarioEntity, tipo, session);
        model.addAttribute("menuAgre", menuAgre);
        model.addAttribute("foto", usuarioEntity.getUrlImagen());

        List<BocaditoEntity> bocaditos = bocaditoService.buscarTodosBocaditos();
        model.addAttribute("bocaditos", bocaditos);

        BocaditoEntity bocadito = new BocaditoEntity();
        model.addAttribute("bocadito", bocadito);

        return "bocaditos";
    }

    @PostMapping("/registrar_bocadito")
    public String registrarBocadito(@ModelAttribute("bocadito") @Valid BocaditoEntity bocadito, BindingResult result) {
        if (result.hasErrors()) {
            return "bocaditos";
        }
        // Setear la URL de la imagen si es necesario
        bocaditoService.guardarBocadito(bocadito);
        return "redirect:/agregar_bocadito";
    }


    @GetMapping("/delete_bocadito/{id}")
    public String mostrarDelete(HttpSession session, @PathVariable("id") Long id) {
        if (session.getAttribute("usuario") == null) {
            return "redirect:/";
        }

        BocaditoEntity bocadito = bocaditoService.buscarBocaditoPorId(id);
        if (bocadito != null) {
            bocaditoService.eliminarBocadito(id);
        }
        return "redirect:/agregar_bocadito";
    }


    @GetMapping("/editar_bocadito/{id}")
    public String mostrarFormularioEdicion(HttpSession session, @PathVariable("id") Long id, Model model) {
        if (session.getAttribute("usuario") == null) {
            return "redirect:/";
        }

        BocaditoEntity bocadito = bocaditoService.buscarBocaditoPorId(id);
        if (bocadito == null) {
            return "redirect:/agregar_bocadito";
        }
        model.addAttribute("bocadito", bocadito);
        return "bocaditos";
    }

    @PostMapping("/actualizar_bocadito")
    public String actualizarBocadito(@ModelAttribute("bocadito") @Valid BocaditoEntity bocadito, BindingResult result) {
        if (result.hasErrors()) {
            return "bocaditos";
        }

        BocaditoEntity bocaditoExistente = bocaditoService.buscarBocaditoPorId(bocadito.getIdboca());
        if (bocaditoExistente == null) {
            return "redirect:/agregar_bocadito";
        }

        // Actualizar los campos incluyendo la URL de la imagen si es necesario
        bocaditoExistente.setNombre_boca(bocadito.getNombre_boca());
        bocaditoExistente.setPrecio_boca(bocadito.getPrecio_boca());
        bocaditoExistente.setStock_boca(bocadito.getStock_boca());
        bocaditoExistente.setDescripcion_boca(bocadito.getDescripcion_boca());
        bocaditoExistente.setUrlImagen(bocadito.getUrlImagen());

        bocaditoService.guardarBocadito(bocaditoExistente);

        return "redirect:/agregar_bocadito";
    }}