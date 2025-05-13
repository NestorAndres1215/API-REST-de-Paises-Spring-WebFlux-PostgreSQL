package com.example.demo.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Paises;

import reactor.core.publisher.Mono;

//Esto hace que el código sea más legible y fácil de mantener
//ReactiveCrudRepository es una interfaz que proporciona métodos para realizar operaciones CRUD reactivas
// Esta interfaz es parte de Spring Data y permite interactuar con la base de datos de manera reactiva
//Esto nos ayuda a evitar la creación de métodos repetitivos para operaciones comunes como guardar, encontrar, actualizar y eliminar entidades
@Repository
public interface PaisesRepository extends ReactiveCrudRepository<Paises, Integer> {
    // Mono es una clase de Spring que representa un valor que puede estar
    // disponible en el futuro

    // Método para encontrar un país por su nombre
    Mono<Paises> findByNombre(String nombre);

    // Método para encontrar un país por su capital
    Mono<Paises> findByCapital(String capital);

    // Método para encontrar un país por su continente
    Mono<Paises> findByContinente(String continente);

    // Método para encontrar un país por su idioma
    Mono<Paises> findByIdioma(String idioma);

    // Método para encontrar un país por su código
    Mono<Paises> findByCodigo(String codigo);

    // Método para encontrar un país por su id
    Mono<Paises> findById(Integer id);

    // Método para eliminar un país por su id
    Mono<Void> deleteById(Integer id);

    // Método para eliminar un país por su nombre
    Mono<Void> deleteByNombre(String nombre);

    // Metodo para ver si existe nombre
    Mono<Boolean> existsByNombre(String nombre);

    // Metodo para ver si existe capital
    Mono<Boolean> existsByCapital(String capital);

    // Metodo para ver si existe codigo
    Mono<Boolean> existsByCodigo(String codigo);

}
