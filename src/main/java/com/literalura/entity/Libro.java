package com.literalura.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "libros")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @Column(unique = true)
    private String titulo;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "autor_id", referencedColumnName = "id")
    private Autor autor;
    private String idioma;
    private Integer cantidadDeDescargas;

    public Libro(DatosLibros datosLibros){
        this.titulo = datosLibros.titulo();
        if (!datosLibros.autores().isEmpty()) {
            Autor autor = new Autor(datosLibros.autores().get(0));
            this.setAutor(autor);
        } else {
            this.autor = null;
        }
        this.idioma = datosLibros.idiomas().isEmpty() ? null : datosLibros.idiomas().get(0);
        this.cantidadDeDescargas = datosLibros.cantidadDeDescargas();
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
        autor.setLibro(this);
    }

    @Override
    public String toString() {
        return "----------- Libro -----------\n" +
                "Titulo: '" + titulo + "\n" +
                "Autor: " + autor.getNombre() + "\n" +
                "Idioma: '" + idioma + "\n" +
                "Cantidad de Descargas: " + cantidadDeDescargas + "\n" +
                "------------------------------\n";
    }

}
