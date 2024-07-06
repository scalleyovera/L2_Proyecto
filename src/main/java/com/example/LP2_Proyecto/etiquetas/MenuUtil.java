package com.example.LP2_Proyecto.etiquetas;

import com.example.LP2_Proyecto.entity.UsuarioEntity;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Component;

@Component
public class MenuUtil {

    public String generarMenu(UsuarioEntity usuarioEntity, Integer tipoUsuario, HttpSession sesion) {
        StringBuilder menu = new StringBuilder();

        menu.append("<nav class='navbar navbar-expand-lg navbar-light bg-light'>")
            .append("<button class='navbar-toggler' type='button' data-toggle='collapse' data-target='#navbarNav' aria-controls='navbarNav' aria-expanded='false' aria-label='Toggle navigation'>")
            .append("<span class='navbar-toggler-icon'></span>")
            .append("</button>")
            .append("<div class='collapse navbar-collapse' id='navbarNav'>")
            .append("<ul class='navbar-nav'>");

        if (tipoUsuario == 1) {
            menu.append("<li class='nav-item'><a class='nav-link' href='/agregar_bocadito'>Mantenimiento Bocadito</a></li>")
                .append("<li class='nav-item'><a class='nav-link' href='/agregar_torta'>Mantenimiento Torta</a></li>")
                .append("<li class='nav-item'><a class='nav-link' href='/agregar_usuario'>Mantenimiento Usuarios</a></li>");
        } else if (tipoUsuario == 2) {
            menu.append("<li class='nav-item'><a class='nav-link' href='/menuHtml'>Inicio</a></li>")
                .append("<li class='nav-item'><a class='nav-link' href='/menu'>Menu</a></li>")
                .append("<li class='nav-item'><a class='nav-link' href='/tienda'>Tienda</a></li>")
                .append("<li class='nav-item'><a class='nav-link' href='/nosotroHtml'>Nosotros</a></li>");
        }

        menu.append("</ul>")
            .append("</div>")
            .append("</nav>");

        return menu.toString();
    }
}

