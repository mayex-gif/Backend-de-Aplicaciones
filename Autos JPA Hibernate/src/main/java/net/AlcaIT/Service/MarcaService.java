package net.AlcaIT.Service;

import net.AlcaIT.Entity.Auto;
import net.AlcaIT.Entity.Marca;
import net.AlcaIT.Repository.MarcaRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MarcaService {
    private final MarcaRepository marcaRepository;
    private final Map<String, Marca> cacheMarcas = new HashMap<>();

    public MarcaService(MarcaRepository marcaRepository) {
        this.marcaRepository = marcaRepository;
    }

    /**
     * Busca una marca por nombre.
     * Si no existe, la crea, persiste y la guarda en cache.
     */
    public Marca obtenerOMarcarCrear(String nombreMarca) {
        // 1. Buscar en cache
        Marca marca = cacheMarcas.get(nombreMarca);

        // 2. Si no está en cache, buscar en DB
        if (marca == null) {
            marca = marcaRepository.findByNombre(nombreMarca);

            // 3. Si no existe en DB, crear y persistir
            if (marca == null) {
                marca = new Marca(nombreMarca);
                marcaRepository.save(marca);
            }

            // 4. Guardar en cache
            cacheMarcas.put(nombreMarca, marca);
        }

        return marca;
    }

    public List<Marca> obtenerTodas() {
        return marcaRepository.findAll();
    }
}
