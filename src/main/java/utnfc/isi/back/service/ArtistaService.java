package utnfc.isi.back.service;

import jakarta.persistence.EntityManager;
import lombok.val;
import utnfc.isi.back.entity.Album;
import utnfc.isi.back.entity.Artista;
import utnfc.isi.back.entity.Genero;
import utnfc.isi.back.repository.ArtistRepository;

import java.util.List;

public class ArtistaService implements ArtistRepository {

    private EntityManager entityManager;

    public ArtistaService(EntityManager em) {
        this.entityManager = em;
    }

    @Override
    public void nuevo(Artista artista) {
        entityManager.persist(artista);
    }

    @Override
    public List<Artista> listarTodos() {
        return entityManager.createQuery("from Artista", Artista.class)
                .getResultList();
    }

    @Override
    public Artista buscarPorId(Integer id) {
        return entityManager.find(Artista.class, id);
    }

    public Artista getOrCreate(String name) {
        try {
            return entityManager.createQuery(
                            "SELECT a FROM Artista a WHERE a.name = :name", Artista.class)
                    .setParameter("name", name)
                    .getSingleResult();
        } catch (jakarta.persistence.NoResultException e) {
            Artista nuevo = new Artista();
            nuevo.setName(name);
            entityManager.persist(nuevo);
            return nuevo;
        }
    }
}
