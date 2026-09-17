package net.AlcaIT.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import net.AlcaIT.Entity.Marca;
import net.AlcaIT.Entity.Modelo;

import java.util.List;

public class ModeloRepositoryImpl implements ModeloRepository{
    private final EntityManager em;

    public ModeloRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public Modelo findByNombre(String nombre) {
        TypedQuery<Modelo> query = em.createQuery(
                "SELECT m FROM Modelo m WHERE m.modelo = :nombre", Modelo.class);
        query.setParameter("nombre", nombre);

        List<Modelo> resultados = query.getResultList();
        return resultados.isEmpty() ? null : resultados.get(0);
    }

    @Override
    public void save(Modelo modelo) {
        em.persist(modelo);
    }

    @Override
    public List<Modelo> findAll() {
        return em.createQuery("SELECT m FROM Modelo m", Modelo.class).getResultList();
    }
}
