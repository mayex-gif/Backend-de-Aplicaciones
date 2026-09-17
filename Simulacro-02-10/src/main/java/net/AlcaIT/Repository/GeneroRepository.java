package net.AlcaIT.Repository;

import jakarta.persistence.EntityManager;
import net.AlcaIT.Entity.Genero;

import java.util.List;

public class GeneroRepository {
    private EntityManager em;

    public GeneroRepository(EntityManager em) {
        this.em = em;
    }

    public Genero findById(int id){
        return em.find(Genero.class, id);
    }

    public List<Genero> findAll(){
        return em.createQuery("SELECT g FROM Genero g", Genero.class)
                .getResultList();
    }

    public Genero getOrCreate(String nombre) {
        // Buscar genero por nombre
        List<Genero> resultados = em.createQuery(
                        "SELECT g FROM Genero g WHERE g.nombre = :nombre", Genero.class)
                .setParameter("nombre", nombre)
                .getResultList();

        // Si existe, devolverlo
        if (!resultados.isEmpty()) {
            return resultados.get(0);
        }

        // Si no existe, crearlo y guardarlo
        Genero nuevo = new Genero(nombre);
        em.persist(nuevo);
        return nuevo;
    }
}
