package net.AlcaIT.Repository;


import jakarta.persistence.EntityManager;
import net.AlcaIT.Entity.Autor;

import java.util.List;

public class AutorRepository {
    private EntityManager em;

    public AutorRepository(EntityManager em) {
        this.em = em;
    }

    public Autor findById(int id){
        return em.find(Autor.class, id);
    }

    public List<Autor> findAll(){
        return em.createQuery("SELECT a FROM Autor a", Autor.class)
                .getResultList();
    }

    public Autor getOrCreate(String nombre) {
        // Buscar autor por nombre
        List<Autor> resultados = em.createQuery(
                        "SELECT a FROM Autor a WHERE a.nombre = :nombre", Autor.class)
                .setParameter("nombre", nombre)
                .getResultList();

        // Si existe, devolverlo
        if (!resultados.isEmpty()) {
            return resultados.get(0);
        }

        // Si no existe, crearlo y guardarlo
        Autor nuevo = new Autor(nombre);
        em.persist(nuevo);
        return nuevo;
    }
}
