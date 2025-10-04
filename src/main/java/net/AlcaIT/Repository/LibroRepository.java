package net.AlcaIT.Repository;

import jakarta.persistence.EntityManager;
import net.AlcaIT.Entity.Autor;


import net.AlcaIT.Entity.Genero;
import net.AlcaIT.Entity.Libro;
import java.util.ArrayList;


import java.util.List;

public class LibroRepository {
    private EntityManager em;

    public LibroRepository(EntityManager em) {
        this.em = em;
        AutorRepository autorRepository1 = new AutorRepository(em);
        GeneroRepository generoRepository1 = new GeneroRepository(em);
    }

    public Libro findById(int id){
        return em.find(Libro.class, id);
    }

    public List<Libro> findAll(){
        return em.createQuery("SELECT l FROM Libro l", Libro.class)
                .getResultList();
    }

    public Libro getOrCreate(String titulo, Autor autor, List<Genero> generos, Libro.Estado estado) {
        List<Libro> resultados = em.createQuery(
                        "SELECT l FROM Libro l WHERE l.titulo = :titulo AND l.autor = :autor", Libro.class)
                .setParameter("titulo", titulo)
                .setParameter("autor", autor)
                .getResultList();

        if (!resultados.isEmpty()) {
            return resultados.get(0);
        }

        Libro nuevo = new Libro();
        nuevo.setTitulo(titulo);
        nuevo.setAutor(autor);
        nuevo.setEstado(estado);
        nuevo.setGeneros(generos);

        em.persist(nuevo);
        return nuevo;
    }

    public List<Libro> findByGeneroNombre(String nombreGenero) {
        return em.createQuery(
                        "SELECT DISTINCT l FROM Libro l JOIN l.generos g WHERE g.nombre = :nombreGenero", Libro.class)
                .setParameter("nombreGenero", nombreGenero)
                .getResultList();
    }

    public List<Libro> findByAutorNombre(String nombreAutor) {
        return em.createQuery(
                        "SELECT DISTINCT l FROM Libro l WHERE l.autor.nombre = :nombreAutor", Libro.class)
                .setParameter("nombreAutor", nombreAutor)
                .getResultList();
    }

}
