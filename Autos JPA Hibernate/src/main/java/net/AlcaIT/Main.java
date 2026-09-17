package net.AlcaIT;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import net.AlcaIT.Entity.Auto;
import net.AlcaIT.Entity.Tipo;
import net.AlcaIT.Repository.*;
import net.AlcaIT.Service.AutoService;
import net.AlcaIT.Service.MarcaService;
import net.AlcaIT.Service.ModeloService;
import net.AlcaIT.Service.TipoService;

import java.util.List;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        Stream.of(args)
                .forEach(arg ->
                        System.out.println("Argumento: " + arg));

        String rutaCsv = args[0];
        System.out.println("Leyendo archivo: " + rutaCsv);

        //==============================================================================================================
        //==============================================================================================================

        // 1. Crear el EntityManagerFactory a partir del persistence.xml
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("MiUnidad");
        // 2. Crear un EntityManager
        EntityManager em = emf.createEntityManager();

        MarcaRepository marcaRepo = new MarcaRepositoryImpl(em);
        ModeloRepository modeloRepo = new ModeloRepositoryImpl(em);
        TipoRepository tipoRepo = new TipoRepositoryImpl(em);
        AutoRepository autoRepo = new AutoRepositoryImpl(em);

        MarcaService marcaService = new MarcaService(marcaRepo);
        ModeloService modeloService = new ModeloService(modeloRepo);
        TipoService tipoService = new TipoService(tipoRepo);

        AutoService autoService = new AutoService(
                autoRepo, marcaService, modeloService, tipoService
        );

        try {
            // 3. Iniciar transacción
            em.getTransaction().begin();


            // 4. Crear y persistir una entidad de prueba
//            Marca marcaPrueba = new Marca("Ferrari");
//            em.persist(marcaPrueba);

            autoService.cargarAutosDesdeCSV(args[0]);

            // 5. Confirmar transacción
            em.getTransaction().commit();

            System.out.println("\n=== Marcas cargadas ===");
            marcaService.obtenerTodas().forEach(m -> System.out.println(m.getMarca()));

            System.out.println("\n=== Modelos cargados ===");
            modeloService.obtenerTodos().forEach(m -> System.out.println(m.getModelo()));

            System.out.println("\n=== Tipos cargados ===");
            tipoService.obtenerTodos().forEach(t -> System.out.println(t.getTipo()));

            System.out.println("\n=== Autos cargados ===");
            autoService.obtenerTodos().forEach(a ->
                    System.out.printf("%d - %s %s [%s]%n",
                            a.getAnio(),
                            a.getMarca().getMarca(),
                            a.getModelo().getModelo(),
                            a.getTipos().stream()
                                    .map(Tipo::getTipo)
                                    .reduce((x, y) -> x + ", " + y)
                                    .orElse("")
                    )
            );



        } catch (Exception e) {
            e.printStackTrace();
            // rollback si falla algo
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        } finally {
        // 6. Cerrar em y emf
        em.close();
        emf.close();
        }
    }
}
