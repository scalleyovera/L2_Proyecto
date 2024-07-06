package com.example.LP2_Proyecto.etiquetas;

import com.example.LP2_Proyecto.entity.UsuarioEntity;
<<<<<<< HEAD
import jakarta.servlet.http.HttpSession;
=======

import jakarta.servlet.http.HttpSession;

>>>>>>> pedro
import org.springframework.stereotype.Component;

@Component
public class MenuUtil {

<<<<<<< HEAD
    public String generarMenu(UsuarioEntity usuarioEntity, Integer tipo, HttpSession sesion) {
        StringBuilder menu = new StringBuilder();

        //Traigo el tipo de usuario para solo manejar Integer
        Integer tipoUsuario = (Integer) sesion.getAttribute("tipo");

        //Valido si el Tipo de usuario de UsuarioController llego al método generarMenu
        System.out.println("(Para el menu) -> tipo de usuario es " + tipoUsuario);

        // Ejemplo de menú común para todos los usuarios
        menu.append("<li><a href='/menu'>Inicio</a></li>");

        // Menú específico para trabajadores
        if (tipoUsuario == 1) {
            System.out.println("Se crea menu para trabajador");
            menu.append("<li><a href='/trabajador/tareas'>Mis Tareas</a></li>");
            menu.append("<li><a href='/trabajador/reportes'>Reportes</a></li>");
        }

        // Menú específico para clientes
        else if (tipoUsuario == 2) {
            System.out.println("Se crea menu para clientes");
            menu.append("<li><a href='/cliente/ordenes'>Mis Órdenes</a></li>");
            menu.append("<li><a href='/cliente/perfil'>Mi Perfil</a></li>");
        }
=======
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
>>>>>>> pedro

        return menu.toString();
    }
}

