package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Paises;
import com.example.demo.repository.PaisesRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

// Importaciones necesarias para crear un controlador REST
//RestController es una anotación que se utiliza para indicar que esta clase es un controlador REST
// y que se debe mapear a una URL específica
//RequestMapping es una anotación que se utiliza para mapear una URL a un controlador
// y se utiliza para definir la ruta base para todas las solicitudes que maneja este controlador
@RestController
@RequestMapping("/paises")
public class PaisesController {
    // Autowired es una anotación que se utiliza para inyectar dependencias en una
    // clase
    // En este caso, se inyecta el repositorio de países
    // Traemos el repositorio de países
    @Autowired
    private PaisesRepository paisRepository;

    // Constructor para inyectar el repositorio de países
    public PaisesController(PaisesRepository paisRepository) {
        this.paisRepository = paisRepository;
    }

    // Constructor vacío para la inyección de dependencias
    public PaisesController() {
        // Constructor vacío
    }

    // Método para listar todos los países
    @GetMapping
    public Flux<Paises> listarPaises() {
        return paisRepository.findAll();
    }

    // Método para buscar un país por su nombre
    @GetMapping("/nombre/{nombre}")
    public Mono<Paises> buscarPorNombre(@PathVariable String nombre) {
        return paisRepository.findByNombre(nombre);
    }

    // Método para buscar un país por su continente
    @GetMapping("/continente/{continente}")
    public Mono<Paises> buscarPorContinente(@PathVariable String continente) {
        return paisRepository.findByContinente(continente);
    }

    // Método para buscar un país por su idioma
    @GetMapping("/idioma/{idioma}")
    public Mono<Paises> buscarPorIdioma(@PathVariable String idioma) {
        return paisRepository.findByIdioma(idioma);
    }

    // Metodo para bsucar por codigo
    @GetMapping("/codigo/{codigo}")
    public Mono<Paises> buscarPorCodigo(@PathVariable String codigo) {
        return paisRepository.findByCodigo(codigo);
    }

    // Método para crear un nuevo país
    // @Requets Body es una anotación que se utiliza para indicar que el parámetro
    // de un método debe ser enlazado al cuerpo de la solicitud HTTP
    @PostMapping
    public Flux<Paises> crear(@RequestBody Flux<Paises> paises) {
        // Se utiliza para recibir una secuencia de países en el cuerpo de la solicitud
        System.out.println("Intentando crear países...");
        // Se imprime un mensaje en la consola para indicar que se está intentando crear
        // países
        return paises
                .flatMap(pais -> validarPais(pais)
                        .flatMap(validado -> paisRepository.save(validado)
                                .doOnNext(saved -> System.out.println("País guardado: " + saved.getNombre()))))
                .doOnError(e -> System.out.println("Error al guardar un país: " + e.getMessage()));
    }

    // Método para validar un país
    private Mono<Paises> validarPais(Paises pais) {

        // Validar que el país no esté vacío
        if (pais.getNombre() == null || pais.getNombre().isBlank()) {
            return Mono.error(new IllegalArgumentException("El nombre del país no puede estar vacío"));
        }
        // Validar que la codigo no esté vacía
        if (pais.getCodigo() == null || pais.getCodigo().isBlank()) {
            return Mono.error(new IllegalArgumentException("El código del país no puede estar vacío"));
        }
        // Validar que el continente no esté vacío
        if (pais.getCapital() == null || pais.getCapital().isBlank()) {
            return Mono.error(new IllegalArgumentException("La capital del país no puede estar vacía"));
        }

        // Validar que no esté duplicado en nombre o código
        return Mono.zip(
                // Verifica si el nombre y codigo ya existe
                paisRepository.existsByNombre(pais.getNombre()),
                paisRepository.existsByCodigo(pais.getCodigo()))
                .flatMap(tuple -> {
                    // Se utiliza Mono.zip para combinar los resultados de las dos consultas
                    // tuple.getT1() devuelve un booleano que indica si el nombre ya existe
                    boolean nombreExiste = tuple.getT1();
                    boolean codigoExiste = tuple.getT2();
                    // Si el nombre o el código ya existen, se lanza una excepción
                    // IllegalArgumentException es una excepción que se lanza cuando un método
                    // recibe un
                    // argumento ilegal o inapropiado
                    // En este caso, se lanza si el nombre o el código ya existen en la base de
                    // datos
                    if (nombreExiste) {
                        return Mono.error(
                                new IllegalArgumentException("Ya existe un país con el nombre: " + pais.getNombre()));
                    }
                    if (codigoExiste) {
                        return Mono.error(
                                new IllegalArgumentException("Ya existe un país con el código: " + pais.getCodigo()));
                    }

                    // Todo bien
                    // Al ser False no existe, se devuelve el país
                    // Se utiliza Mono.just para devolver el país sin modificarlo
                    return Mono.just(pais);
                });
    }

    // Metodo Actualizar
    @PutMapping("/actualizar/{id}")
    public Mono<Paises> actualizarPais(@PathVariable Integer id, @RequestBody Paises pais) {
        // Validar campos vacíos antes de buscar el país existente
        if (pais.getNombre() == null || pais.getNombre().isBlank()) {
            return Mono.error(new IllegalArgumentException("El nombre del país no puede estar vacío"));
        }
        if (pais.getCodigo() == null || pais.getCodigo().isBlank()) {
            return Mono.error(new IllegalArgumentException("El código del país no puede estar vacío"));
        }
        if (pais.getCapital() == null || pais.getCapital().isBlank()) {
            return Mono.error(new IllegalArgumentException("La capital del país no puede estar vacía"));
        }

        return paisRepository.findById(id)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("País no encontrado con id: " + id)))
                .flatMap(paisExistente -> {
                    // Validar duplicados solo si el nombre o código cambiaron
                    Mono<Boolean> nombreDuplicado = pais.getNombre().equals(paisExistente.getNombre())
                            ? Mono.just(false)
                            : paisRepository.existsByNombre(pais.getNombre());
                    Mono<Boolean> codigoDuplicado = pais.getCodigo().equals(paisExistente.getCodigo())
                            ? Mono.just(false)
                            : paisRepository.existsByCodigo(pais.getCodigo());

                    return Mono.zip(nombreDuplicado, codigoDuplicado)
                            .flatMap(tuple -> {
                                if (tuple.getT1()) {
                                    return Mono.error(new IllegalArgumentException(
                                            "Ya existe un país con el nombre: " + pais.getNombre()));
                                }
                                if (tuple.getT2()) {
                                    return Mono.error(new IllegalArgumentException(
                                            "Ya existe un país con el código: " + pais.getCodigo()));
                                }
                                paisExistente.setNombre(pais.getNombre());
                                paisExistente.setCodigo(pais.getCodigo());
                                paisExistente.setCapital(pais.getCapital());
                                return paisRepository.save(paisExistente);
                            });
                });
    }


    //Metodo para eliminar un pais
    @GetMapping("/eliminar/{id}")
    public Mono<Void> eliminarPais(@PathVariable Integer id) {
        return paisRepository.existsById(id)
                .flatMap(exists -> {
                    if (!exists) {
                        return Mono.error(new IllegalArgumentException("País no encontrado con id: " + id));
                    }
                    return paisRepository.deleteById(id);
                });
    }
    // Metodo para eliminar por nombre
    @GetMapping("/eliminar/nombre/{nombre}")
    public Mono<Void> eliminarPaisPorNombre(@PathVariable String nombre) {
        return paisRepository.existsByNombre(nombre)
                .flatMap(exists -> {
                    if (!exists) {
                        return Mono.error(new IllegalArgumentException("País no encontrado con nombre: " + nombre));
                    }
                    return paisRepository.deleteByNombre(nombre);
                });
    }
    //Metyodo para eliminar Litsa de paises por continente
    @GetMapping("/eliminar/continente/{continente}")
    public Mono<Void> eliminarPaisesPorContinente(@PathVariable String continente) {
        return paisRepository.findByContinente(continente)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("No se encontraron países con el continente: " + continente)))
                .flatMap(pais -> paisRepository.deleteById(pais.getId()))
                .then();
    }
}
