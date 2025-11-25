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

    @Override
    public Artista getOrCreate(String name) {
        List<Artista> resultados = entityManager.createQuery(
                        "SELECT a FROM Artista a WHERE a.name =: name", Artista.class)
                .setParameter("name", name)
                .getResultList();
        if (!resultados.isEmpty()) {
            return resultados.get(0);
        }

        Artista artista = new Artista(name);
        entityManager.persist(artista);
        return artista;
    }
}
