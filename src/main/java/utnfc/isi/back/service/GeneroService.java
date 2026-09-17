package utnfc.isi.back.service;

import jakarta.persistence.EntityManager;
import utnfc.isi.back.entity.Album;
import utnfc.isi.back.entity.Genero;
import utnfc.isi.back.entity.MediaType;
import utnfc.isi.back.repository.GeneroRepository;

import java.util.List;

public class GeneroService implements GeneroRepository {

    private EntityManager entityManager;

    public GeneroService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Genero getOrCreate(String name) {

        List<Genero> resultados = entityManager.createQuery(
                        "SELECT g FROM Genero g WHERE g.name =: name", Genero.class)
                .setParameter("name", name)
                .getResultList();
        if (!resultados.isEmpty()) {
            return resultados.get(0);
        }

        Genero genero = new Genero(name);
        entityManager.persist(genero);
        return genero;
    }

    @Override
    public List<Genero> listarTodos() {
        return entityManager.createQuery("from Genero", Genero.class)
                .getResultList();
    }
}
