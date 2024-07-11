package com.literalura.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "autores")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String nombre;
    private Integer anioNacimiento;
    private Integer anioFallecimiento;
    @OneToOne(mappedBy = "autor")
    private Libro libro;

    public Autor(DatosAutores datosAutores) {
        this.nombre = datosAutores.nombre();
        this.anioNacimiento = datosAutores.anioNacimiento();
        this.anioFallecimiento = datosAutores.anioFallecimiento();
    }

    @Override
    public String toString() {
        return "Autor: " + nombre + "\n" +
                "Año Nacimiento: " + anioNacimiento + "\n" +
                "Año Fallecimiento: " + anioFallecimiento + "\n" +
                "Libro: " + (libro != null ? libro.getTitulo() : "Sin libro") + "\n";
    }
}
