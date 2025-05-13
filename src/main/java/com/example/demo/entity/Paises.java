package com.example.demo.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

// Table es una anotación que se utiliza para mapear una clase a una tabla de base de datos
// La anotación @Table se utiliza para indicar que esta clase es una entidad
// y que se debe mapear a una tabla de base de datos
@Table(name = "paises")
public class Paises {
    //Id es una anotación que se utiliza para indicar que este campo es la clave primaria
    @Id
    private Integer id;
    private String nombre;
    private String capital;
    private String continente;
    private String idioma;
    private String codigo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public String getContinente() {
        return continente;
    }

    public void setContinente(String continente) {
        this.continente = continente;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

}
