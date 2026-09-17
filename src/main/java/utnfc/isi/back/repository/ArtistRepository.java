package utnfc.isi.back.repository;

import utnfc.isi.back.entity.Artista;

import java.util.List;

public interface ArtistRepository {
    void nuevo(Artista artista);
    List<Artista> listarTodos();
    Artista buscarPorId(Integer id);
    Artista getOrCreate(String name);
}
