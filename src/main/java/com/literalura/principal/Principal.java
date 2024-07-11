package com.literalura.principal;

import com.literalura.entity.Autor;
import com.literalura.entity.DatosLibros;
import com.literalura.entity.Libro;
import com.literalura.repository.AutorRepository;
import com.literalura.repository.LibroRepository;
import com.literalura.service.ConvierteDatos;
import com.literalura.service.CosumoAPILibros;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Principal {

    private Scanner teclado = new Scanner(System.in);
    private static final String URL = "https://gutendex.com/books/";
    CosumoAPILibros consumoAPILibros = new CosumoAPILibros();
    ConvierteDatos conversor = new ConvierteDatos();
    private LibroRepository repositorio;
    private AutorRepository autorRepository;
    private Optional<Libro> libroBuscado;
    private List<Libro> libros;
    private List<Autor> autores;




    public Principal(LibroRepository repositorio, AutorRepository autorRepository) {
        this.repositorio = repositorio;
        this.autorRepository = autorRepository;
    }

    public void ejecutar() {

        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    1 - Buscar libro por titulo
                    2 - Listar libros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos en un determinado año
                    5 - Listar libros por idioma
                                  
                    0 - Salir
                    """;
            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1:
                    guardarLibros();
                    break;
                case 2:
                    mostrarLibrosGuardados();
                    break;
                case 3:
                    mostrarAutoresGuardados();
                    break;
                case 4:
                    buscarAutorPorAnio();
                    break;
                case 5:
                    buscarLibrosPorIdioma();
                    break;
                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }
    }

    private DatosLibros getDatosLibro() {
        System.out.println("Escribe el nombre del libro que deseas buscar");
        var nombreLibro = teclado.nextLine();
        var json = consumoAPILibros.obtenerDatos(URL);
        List<DatosLibros> datos = conversor.obtenerListaDatos(json, DatosLibros.class);

        Optional<DatosLibros> libro = datos.stream()
                .filter(d -> d.titulo().toUpperCase().contains(nombreLibro.toUpperCase()))
                .findFirst();
        return libro.orElse(null);
    }

    private void guardarLibros() {
        try{
            DatosLibros datos = getDatosLibro();
            Libro libro = new Libro(datos);
            repositorio.save(libro);
            System.out.println(datos);
        } catch (DataIntegrityViolationException e) {
            System.out.println("El libro ya se encuentra guardado");
        }
    }

    private void mostrarLibrosGuardados() {
        libros = repositorio.findAll();

        libros.stream()
                .sorted(Comparator.comparing(Libro::getTitulo))
                .forEach(System.out::println);
    }


    private void mostrarAutoresGuardados() {
        autores = autorRepository.findAll();

        autores.stream()
                .sorted(Comparator.comparing(Autor::getNombre))
                .forEach(System.out::println);
    }


    private void buscarAutorPorAnio() {
        System.out.println("Escribe el año vivo del autor que deseas buscar");
        var anio = teclado.nextInt();
        autores = autorRepository.findByAnioVivo(anio);

        autores.stream()
                .distinct()
                .sorted(Comparator.comparing(Autor::getNombre))
                .forEach(System.out::println);
    }


    private void buscarLibrosPorIdioma() {
        var opcion = -1;
        String idioma = "";
        while (opcion != 0) {
            var menu = """
                1 - Español
                2 - Ingles
                              
                0 - Salir
                """;
            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1:
                    idioma = "es";
                    break;
                case 2:
                    idioma = "en";
                    break;
                case 0:
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Opción inválida. Por favor, elija una opción correcta.");
                    continue;

            }

            if (!idioma.isEmpty()) {
                libros = repositorio.findByIdiomaIs(idioma);

                if (!libros.isEmpty()) {
                    libros.forEach(System.out::println);
                } else {
                    System.out.println("No se encontraron libros en el idioma seleccionado.");
                }
            }
        }
    }
}
