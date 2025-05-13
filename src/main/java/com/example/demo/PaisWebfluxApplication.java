package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// Webflux es un framework reactivo de Spring que permite construir aplicaciones web
// de manera asíncrona y no bloqueante
// Spring Boot es un framework que simplifica la creación de aplicaciones Java
// al proporcionar configuraciones automáticas y convenciones sobre configuraciones
// Spring Data es un proyecto de Spring que proporciona una forma sencilla de acceder
// a bases de datos y realizar operaciones CRUD (Crear, Leer, Actualizar, Eliminar)
// Spring WebFlux es un módulo de Spring que permite construir aplicaciones web
// reactivas y no bloqueantes utilizando el modelo de programación reactiva

@SpringBootApplication
public class PaisWebfluxApplication {

	public static void main(String[] args) {
		SpringApplication.run(PaisWebfluxApplication.class, args);
	}

}
