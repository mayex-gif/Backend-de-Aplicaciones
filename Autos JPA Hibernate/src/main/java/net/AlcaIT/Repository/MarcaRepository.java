package net.AlcaIT.Repository;

import net.AlcaIT.Entity.Marca;

import java.util.List;

public interface MarcaRepository {
    Marca findByNombre(String nombre);
    void save(Marca marca);

    List<Marca> findAll();
}
