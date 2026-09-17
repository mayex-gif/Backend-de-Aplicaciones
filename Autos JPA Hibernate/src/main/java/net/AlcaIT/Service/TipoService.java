package net.AlcaIT.Service;

import net.AlcaIT.Entity.Tipo;
import net.AlcaIT.Repository.TipoRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TipoService {
    private final TipoRepository tipoRepository;
    private final Map<String, Tipo> cacheTipos = new HashMap<>();

    public TipoService(TipoRepository tipoRepository) {
        this.tipoRepository = tipoRepository;
    }

    /**
     * Busca un tipo por nombre.
     * Si no existe, la crea, persiste y la guarda en cache.
     */
    public Tipo obtenerOMarcarCrear(String nombreTipo) {
        // 1. Buscar en cache
        Tipo tipo = cacheTipos.get(nombreTipo);

        // 2. Si no está en cache, buscar en DB
        if (tipo == null) {
            tipo = tipoRepository.findByNombre(nombreTipo);

            // 3. Si no existe en DB, crear y persistir
            if (tipo == null) {
                tipo = new Tipo(nombreTipo);
                tipoRepository.save(tipo);
            }

            // 4. Guardar en cache
            cacheTipos.put(nombreTipo, tipo);
        }

        return tipo;
    }

    public List<Tipo> obtenerTodos() {
        return tipoRepository.findAll();
    }
}
