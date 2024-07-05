package com.example.LP2_Proyecto.etiquetas;

import com.example.LP2_Proyecto.entity.UsuarioEntity;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;

@Component
public class MenuUtil {

    public String generarMenu(UsuarioEntity usuarioEntity, Integer tipo, HttpSession sesion) {
        StringBuilder menu = new StringBuilder();

        //Traigo el tipo de usuario para solo manejar Integer
        Integer tipoUsuario = (Integer) sesion.getAttribute("tipo");

        //Valido el usuario en consola

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

        return menu.toString();
    }
}
