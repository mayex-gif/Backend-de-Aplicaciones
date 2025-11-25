package utnfc.isi.back.repository;

import utnfc.isi.back.entity.Track;

import java.util.List;

public interface TrackRepository {
    void nuevo(Track track);
    List<Track> listarTodos();
    Track buscarPorId(Integer id);
}
