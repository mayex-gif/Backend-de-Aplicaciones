package net.AlcaIT;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import net.AlcaIT.Entity.*;
import net.AlcaIT.Repository.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        String rutaCsv = "D:\\PROGRAMACION\\BackEnd\\simulacro-02-10\\src\\main\\resources\\books.csv";
        System.out.println("Leyendo archivo: " + rutaCsv);

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("MiUnidad");
        EntityManager em = emf.createEntityManager();

        AutorRepository autorRepo = new AutorRepository(em);
        GeneroRepository generoRepo = new GeneroRepository(em);
        LibroRepository libroRepo = new LibroRepository(em);

        try {
            em.getTransaction().begin();

            try (BufferedReader br = new BufferedReader(new FileReader(rutaCsv))) {
                String linea;
                boolean primera = true;
                while ((linea = br.readLine()) != null) {
                    if (primera) { // salteamos encabezado
                        primera = false;
                        continue;
                    }
                    if (linea.trim().isEmpty()) continue;

                    String[] campos = linea.split(",");

                    String titulo = campos[0].trim();
                    String nombreAutor = campos[1].trim();
                    String[] generosCsv = campos[2].split(";");
                    String estadoStr = campos[3].trim();

                    // --- Autor ---
                    Autor autor = autorRepo.getOrCreate(nombreAutor);

                    // --- Géneros ---
                    List<Genero> generos = new ArrayList<>();
                    for (String g : generosCsv) {
                        generos.add(generoRepo.getOrCreate(g.trim()));
                    }

                    // --- Estado ---
                    Libro.Estado estado = Libro.Estado.valueOf(estadoStr);

                    // --- Libro ---
                    Libro libro = libroRepo.getOrCreate(titulo, autor, generos, estado);

                    em.persist(libro); // merge/persist según tu diseño
                }
            }

            em.getTransaction().commit();

        } catch (Exception e) {
            e.printStackTrace();
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        } finally {
            // Listar Libros
            List<Libro> libros = libroRepo.findAll();
            System.out.printf("%n==================== LIBROS ====================%n");
            for (Libro l : libros) {
                System.out.printf("%s - %s [%s] (%s)%n",
                        l.getTitulo(),
                        l.getAutor().getNombre(),
                        l.getGeneros().stream().map(Genero::getNombre).reduce((a, b) -> a + "; " + b).orElse(""),
                        l.getEstado()
                );
            }

            // Listar Autores
            List<Autor> autores = autorRepo.findAll();
            System.out.printf("%n==================== AUTORES ====================%n");
            for (Autor a : autores) {
                System.out.printf("%s%n",
                        a.getNombre()
                );
            }

            // Listar Generos
            List<Genero> generos = generoRepo.findAll();
            System.out.printf("%n==================== GENEROS ====================%n");
            for (Genero g : generos) {
                System.out.printf("%s%n",
                        g.getNombre()
                );
            }

            // Filtrar libros por autor (por ejemplo, "Jorge Luis Borges")
            List<Libro> librosPorAutor = libroRepo.findByAutorNombre("Jorge Luis Borges");
            System.out.printf("%n%nLibros por autor 'Jorge Luis Borges':%n");
            librosPorAutor.forEach(libro -> System.out.println(libro.getTitulo()));

            // Filtrar libros por género (por ejemplo, "Ficción")
            List<Libro> librosPorGenero = libroRepo.findByGeneroNombre("Ficción");
            System.out.printf("%n%nLibros por género 'Ficción':%n");
            librosPorGenero.forEach(libro -> System.out.println(libro.getTitulo()));

            em.close();
            emf.close();
        }
    }
}
