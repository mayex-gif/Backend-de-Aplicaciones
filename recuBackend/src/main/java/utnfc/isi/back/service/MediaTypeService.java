package utnfc.isi.back.service;

import jakarta.persistence.EntityManager;
import utnfc.isi.back.entity.Album;
import utnfc.isi.back.entity.MediaType;
import utnfc.isi.back.repository.MediaTypeRepository;

import java.util.List;

public class MediaTypeService implements MediaTypeRepository {

    private EntityManager entityManager;

    public MediaTypeService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public MediaType getOrCreate(String name) {

        List<MediaType> resultados = entityManager.createQuery(
                "SELECT mt FROM MediaType mt WHERE mt.name =: name", MediaType.class)
                .setParameter("name", name)
                .getResultList();
        if (!resultados.isEmpty()) {
            return resultados.get(0);
        }

        MediaType mediaType = new MediaType(name);
        entityManager.persist(mediaType);
        return mediaType;
    }

    @Override
    public List<MediaType> listarTodos() {
        return entityManager.createQuery("from MediaType ", MediaType.class)
                .getResultList();
    }

}
