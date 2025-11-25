package utnfc.isi.back.repository;

import utnfc.isi.back.entity.Album;

import java.util.List;

public interface AlbumRepository {

    void nuevo(Album album);
    List<Album> listarTodos();
    Album buscarPorId(Integer id);
}
