package net.AlcaIT.Repository;

import net.AlcaIT.Entity.Modelo;
import net.AlcaIT.Entity.Tipo;

import java.util.List;

public interface TipoRepository {
    void save(Tipo tipo);
    Tipo findByNombre(String nombre);

    List<Tipo> findAll();
}
