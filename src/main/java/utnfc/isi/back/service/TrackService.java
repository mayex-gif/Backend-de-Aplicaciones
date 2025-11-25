package utnfc.isi.back.service;

import jakarta.persistence.EntityManager;
import lombok.val;
import utnfc.isi.back.entity.Invoice;
import utnfc.isi.back.entity.Track;
import utnfc.isi.back.repository.TrackRepository;

import java.util.List;

public class TrackService implements TrackRepository {

    private EntityManager entityManager;

    public TrackService(EntityManager em) {
        this.entityManager = em;
    }

    @Override
    public void nuevo(Track track) {
        entityManager.persist(track);
    }

    @Override
    public List<Track> listarTodos() {
        return entityManager.createQuery("from Track", Track.class)
                .getResultList();
    }

    @Override
    public Track buscarPorId(Integer id) {
        return entityManager.find(Track.class, id);
    }
}
