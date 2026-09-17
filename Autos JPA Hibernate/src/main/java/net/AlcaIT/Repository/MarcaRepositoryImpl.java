package net.AlcaIT.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import net.AlcaIT.Entity.Marca;

import java.util.List;

public class MarcaRepositoryImpl implements MarcaRepository{
    private final EntityManager em;

    public MarcaRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public Marca findByNombre(String nombre) {
        TypedQuery<Marca> query = em.createQuery(
                "SELECT m FROM Marca m WHERE m.marca = :nombre", Marca.class);
        query.setParameter("nombre", nombre);

        List<Marca> resultados = query.getResultList();
        return resultados.isEmpty() ? null : resultados.get(0);
    }

    @Override
    public void save(Marca marca) {
        em.persist(marca);
    }

    @Override
    public List<Marca> findAll() {
        return em.createQuery("SELECT m FROM Marca m", Marca.class).getResultList();
    }
}
