package net.AlcaIT.Repository;

import net.AlcaIT.Entity.Auto;

import java.util.List;

public interface AutoRepository {
    Auto findById(int id);
    void save(Auto auto);

    List<Auto> findAll();
}
