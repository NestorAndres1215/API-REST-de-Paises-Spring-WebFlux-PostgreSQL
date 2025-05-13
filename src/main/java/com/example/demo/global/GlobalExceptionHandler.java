package com.example.demo.global;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

// Importaciones necesarias para manejar excepciones globales en la aplicación
@RestControllerAdvice
// La anotación @RestControllerAdvice se utiliza para manejar excepciones
// en controladores REST de manera global
public class GlobalExceptionHandler {
    // Manejo de excepciones global para la aplicación
    // Este método maneja las excepciones de tipo IllegalArgumentException
    // que pueden ocurrir en cualquier parte de la aplicación
    // y devuelve una respuesta personalizada con un mensaje de error
    @ExceptionHandler(IllegalArgumentException.class)
    // Se utiliza la anotación @ExceptionHandler para indicar que este método
    public ResponseEntity<Map<String, Object>> handleIllegalArgument(IllegalArgumentException ex) {
        Map<String, Object> response = new HashMap<>();
        // Se crea un mapa para almacenar la información de la respuesta
        // Se agrega la información de la respuesta al mapa
        // Se agrega la fecha y hora actual al mapa
        response.put("timestamp", LocalDateTime.now());
        // Se agrega el mensaje de error al mapa
        response.put("error", "Error de validación");
        // Se agrega el mensaje de error específico al mapa
        // Se agrega el estado HTTP al mapa
        response.put("message", ex.getMessage());
        response.put("status", HttpStatus.BAD_REQUEST.value());
        // Se agrega el estado HTTP al mapa
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    // Puedes agregar más handlers si quieres manejar otros tipos de errores
}