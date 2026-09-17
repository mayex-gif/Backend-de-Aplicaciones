package net.AlcaIT.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import net.AlcaIT.Entity.Auto;

import java.util.List;

public class AutoRepositoryImpl implements AutoRepository{
    private final EntityManager em;

    public AutoRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public Auto findById(int id) {
        TypedQuery<Auto> query = em.createQuery(
                "SELECT a FROM Auto a WHERE a.id = :id", Auto.class);
        query.setParameter("id", id);

        List<Auto> resultados = query.getResultList();
        return resultados.isEmpty() ? null : resultados.get(0);
    }

    @Override
    public void save(Auto auto) {
        em.persist(auto);
    }

    @Override
    public List<Auto> findAll(){
        return em.createQuery("SELECT a FROM Auto a", Auto.class).getResultList();
    }
}
