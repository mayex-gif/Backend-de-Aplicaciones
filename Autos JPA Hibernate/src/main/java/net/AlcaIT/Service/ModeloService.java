package net.AlcaIT.Service;

import net.AlcaIT.Entity.Modelo;
import net.AlcaIT.Repository.ModeloRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModeloService {
    private final ModeloRepository modeloRepository;
    private final Map<String, Modelo> cacheModelos = new HashMap<>();

    public ModeloService(ModeloRepository modeloRepository) {
        this.modeloRepository = modeloRepository;
    }

    /**
     * Busca un modelo por nombre.
     * Si no existe, la crea, persiste y la guarda en cache.
     */
    public Modelo obtenerOMarcarCrear(String nombreModelo) {
        // 1. Buscar en cache
        Modelo modelo = cacheModelos.get(nombreModelo);

        // 2. Si no está en cache, buscar en DB
        if (modelo == null) {
            modelo = modeloRepository.findByNombre(nombreModelo);

            // 3. Si no existe en DB, crear y persistir
            if (modelo == null) {
                modelo = new Modelo(nombreModelo);
                modeloRepository.save(modelo);
            }

            // 4. Guardar en cache
            cacheModelos.put(nombreModelo, modelo);
        }

        return modelo;
    }

    public List<Modelo> obtenerTodos() {
        return modeloRepository.findAll();
    }
}
