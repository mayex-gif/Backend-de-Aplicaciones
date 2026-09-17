import net.AlcaIT.Service.AutoService;
import net.AlcaIT.Repository.*;
import net.AlcaIT.Service.MarcaService;
import net.AlcaIT.Service.ModeloService;
import net.AlcaIT.Service.TipoService;
import org.junit.jupiter.api.*;

import jakarta.persistence.*;

public class AutoServiceTest {

    static EntityManagerFactory emf;
    static EntityManager em;
    static AutoService autoService;

    @BeforeAll
    static void init() {
        emf = Persistence.createEntityManagerFactory("MiUnidad");
        em = emf.createEntityManager();

        MarcaRepository marcaRepo = new MarcaRepositoryImpl(em);
        ModeloRepository modeloRepo = new ModeloRepositoryImpl(em);
        TipoRepository tipoRepo = new TipoRepositoryImpl(em);
        AutoRepository autoRepo = new AutoRepositoryImpl(em);

        autoService = new AutoService(
                autoRepo,
                new MarcaService(marcaRepo),
                new ModeloService(modeloRepo),
                new TipoService(tipoRepo)
        );

        em.getTransaction().begin();
    }

    @AfterAll
    static void close() {
        if (em.getTransaction().isActive()) em.getTransaction().rollback();
        em.close();
        emf.close();
    }

    @Test
    void testCargaAutosCSV() {
        autoService.cargarAutosDesdeCSV("src/test/resources/autos.csv");

        var autos = em.createQuery("SELECT a FROM Auto a", net.AlcaIT.Entity.Auto.class).getResultList();
        Assertions.assertFalse(autos.isEmpty());
        Assertions.assertEquals("Ferrari", autos.get(0).getMarca().getMarca());
    }
}
