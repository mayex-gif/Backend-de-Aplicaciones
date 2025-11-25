package utnfc.isi.back.repository;

import utnfc.isi.back.entity.MediaType;

import java.util.List;

public interface MediaTypeRepository {

    MediaType getOrCreate(String name);
    List<MediaType> listarTodos();
}
