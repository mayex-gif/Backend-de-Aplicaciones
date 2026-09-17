package net.AlcaIT.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import net.AlcaIT.Entity.Marca;
import net.AlcaIT.Entity.Tipo;

import java.util.List;

public class TipoRepositoryImpl implements TipoRepository {
    private final EntityManager em;

    public TipoRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public Tipo findByNombre(String nombre) {
        TypedQuery<Tipo> query = em.createQuery(
                "SELECT t FROM Tipo t WHERE t.tipo = :nombre", Tipo.class);
        query.setParameter("nombre", nombre);

        List<Tipo> resultados = query.getResultList();
        return resultados.isEmpty() ? null : resultados.get(0);
    }

    @Override
    public void save(Tipo tipo) {
        em.persist(tipo);
    }

    @Override
    public List<Tipo> findAll() {
        return em.createQuery("SELECT t FROM Tipo t", Tipo.class).getResultList();
    }
}
