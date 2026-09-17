package net.AlcaIT.Service;

import net.AlcaIT.Entity.Auto;
import net.AlcaIT.Entity.Tipo;
import net.AlcaIT.Repository.AutoRepository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class AutoService {

    private final AutoRepository autoRepo;
    private final MarcaService marcaService;
    private final ModeloService modeloService;
    private final TipoService tipoService;

    public AutoService(AutoRepository autoRepo, MarcaService marcaService, ModeloService modeloService, TipoService tipoService) {
        this.autoRepo = autoRepo;
        this.marcaService = marcaService;
        this.modeloService = modeloService;
        this.tipoService = tipoService;
    }

    /**
     * Lee un CSV y persiste autos junto con sus marcas, modelos y tipos.
     */
    public void cargarAutosDesdeCSV(String rutaCsv) {
        List<String> lineas = leerArchivo(rutaCsv);

        for (String linea : lineas) {
            if (linea.trim().isEmpty()) continue;

            try {
                Auto auto = Auto.fromString(linea);

                // Procesar marca
                auto.setMarca(marcaService.obtenerOMarcarCrear(auto.getMarca().getMarca()));

                // Procesar modelo
                auto.setModelo(modeloService.obtenerOMarcarCrear(auto.getModelo().getModelo()));

                // Procesar tipos
                List<Tipo> tiposProcesados = new ArrayList<>();
                for (Tipo tipo : auto.getTipos()) {
                    Tipo tipoPersistido = tipoService.obtenerOMarcarCrear(tipo.getTipo());
                    tiposProcesados.add(tipoPersistido);
                }
                auto.setTipos(new HashSet<>(tiposProcesados));

                // Guardar auto
                autoRepo.save(auto);

            } catch (Exception e) {
                System.err.println("Error al procesar línea: " + linea + " → " + e.getMessage());
            }
        }
    }

    public List<Auto> obtenerTodos() {
        return autoRepo.findAll();
    }

    private List<String> leerArchivo(String rutaCsv) {
        List<String> lineas = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(rutaCsv))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                lineas.add(linea);
            }
        } catch (IOException e) {
            System.err.println("Error al leer archivo: " + e.getMessage());
        }
        return lineas;
    }
}
