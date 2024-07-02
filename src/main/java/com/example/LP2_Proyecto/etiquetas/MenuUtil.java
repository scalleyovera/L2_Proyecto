package com.example.LP2_Proyecto.etiquetas;

import com.example.LP2_Proyecto.entity.UsuarioEntity;
import org.springframework.stereotype.Component;

@Component
public class MenuUtil {

    public String generarMenu(UsuarioEntity usuarioEntity) {
        StringBuilder menu = new StringBuilder();

        // Ejemplo de menú común para todos los usuarios
        menu.append("<li><a href='/menu'>Inicio</a></li>");

        // Menú específico para trabajadores
        if (usuarioEntity.getTipo().getDescripcion().equalsIgnoreCase("Trabajador")) {
            menu.append("<li><a href='/trabajador/tareas'>Mis Tareas</a></li>");
            menu.append("<li><a href='/trabajador/reportes'>Reportes</a></li>");
        }

        // Menú específico para clientes
        else if (usuarioEntity.getTipo().getDescripcion().equalsIgnoreCase("Cliente")) {
            menu.append("<li><a href='/cliente/ordenes'>Mis Órdenes</a></li>");
            menu.append("<li><a href='/cliente/perfil'>Mi Perfil</a></li>");
        }

        return menu.toString();
    }
}
