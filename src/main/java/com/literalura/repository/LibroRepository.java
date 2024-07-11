package com.literalura.repository;

import com.literalura.entity.Libro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LibroRepository extends JpaRepository<Libro, Long> {

    Optional<Libro> findByTituloContainsIgnoreCase(String nombreLibro);
    List<Libro> findByIdiomaIs(String idioma);

}
