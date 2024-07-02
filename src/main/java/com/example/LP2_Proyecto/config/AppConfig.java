package com.example.LP2_Proyecto.config;


import org.springframework.context.annotation.Configuration;

import com.example.LP2_Proyecto.etiquetas.MenuUtil;

@Configuration
public class AppConfig {
    public MenuUtil menuUtil() {
        return new MenuUtil();
    }
}
