package utnfc.isi.back.service;

import jakarta.persistence.EntityManager;
import utnfc.isi.back.entity.Album;
import utnfc.isi.back.entity.Artista;
import utnfc.isi.back.repository.AlbumRepository;

import java.util.List;

public class AlbumService implements AlbumRepository {

    private EntityManager entityManager;
    private ArtistaService artistaService;

    public AlbumService(EntityManager em,
                        ArtistaService artistaService) {
        this.entityManager = em;
        this.artistaService = artistaService;
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

    public Album getOrCreate(String title, String artistName) {

        // 1. Buscar Album por título
        try {
            return entityManager.createQuery(
                            "SELECT a FROM Album a WHERE a.title = :title", Album.class)
                    .setParameter("title", title)
                    .getSingleResult();
        } catch (jakarta.persistence.NoResultException e) {
            // 2. Si no existe, crear el Artista (Find or Create)
            Artista artista = artistaService.getOrCreate(artistName); // Debes implementar getOrCreate en ArtistaService

            // 3. Crear el nuevo Album
            Album nuevoAlbum = new Album();
            nuevoAlbum.setTitle(title);
            nuevoAlbum.setArtista(artista);

            entityManager.persist(nuevoAlbum);
            return nuevoAlbum;
        }
    }
}
