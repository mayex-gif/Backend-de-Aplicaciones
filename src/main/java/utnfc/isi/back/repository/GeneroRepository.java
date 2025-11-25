package utnfc.isi.back.repository;

import utnfc.isi.back.entity.Genero;

import java.util.List;

public interface GeneroRepository {

    Genero getOrCreate(String name);
    List<Genero> listarTodos();

}
