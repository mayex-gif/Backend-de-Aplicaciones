package net.AlcaIT.Repository;

import net.AlcaIT.Entity.Modelo;

import java.util.List;

public interface ModeloRepository {
    Modelo findByNombre(String nombre);
    void save(Modelo modelo);

    List<Modelo> findAll();
}
