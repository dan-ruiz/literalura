package com.literalura.entity;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosLibros(
        @JsonAlias("title") String titulo,
        @JsonProperty("authors") List<DatosAutores> autores,
        @JsonAlias("languages") List<String> idiomas,
        @JsonAlias("download_count")Integer cantidadDeDescargas
) {

    @Override
    public String toString() {
        return "----------- Libro -----------\n" +
                "Titulo: '" + titulo + "\n" +
                "Autor/es: " + autores + "\n" +
                "Idioma: '" + idiomas + "\n" +
                "Cantidad de Descargas: " + cantidadDeDescargas + "\n" +
                "------------------------------\n";
    }
}
