package utnfc.isi.back.service;

import jakarta.persistence.EntityManager;
import lombok.val;
import utnfc.isi.back.entity.Album;
import utnfc.isi.back.repository.AlbumRepository;

import java.util.List;

public class AlbumService implements AlbumRepository {

    private EntityManager entityManager;

    public AlbumService(EntityManager em) {
        this.entityManager = em;
    }

    @Override
    public void nuevo(Album album) {
        entityManager.persist(album);
    }

    @Override
    public List<Album> listarTodos() {
        return entityManager.createQuery("from Album", Album.class)
                .getResultList();
    }

    @Override
    public Album buscarPorId(Integer id) {
        return entityManager.find(Album.class, id);
    }
}
