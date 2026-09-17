import net.AlcaIT.Entity.Auto;
import net.AlcaIT.Repository.*;
import net.AlcaIT.Service.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class CollectionTest {

    @Test
    public void collectionTest() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("MiUnidad");
        EntityManager em = emf.createEntityManager();

        MarcaRepository marcaRepo = new MarcaRepositoryImpl(em);
        ModeloRepository modeloRepo = new ModeloRepositoryImpl(em);
        TipoRepository tipoRepo = new TipoRepositoryImpl(em);
        AutoRepository autoRepo = new AutoRepositoryImpl(em);

        MarcaService marcaService = new MarcaService(marcaRepo);
        ModeloService modeloService = new ModeloService(modeloRepo);
        TipoService tipoService = new TipoService(tipoRepo);

        AutoService autoService = new AutoService(autoRepo, marcaService, modeloService, tipoService);

        em.getTransaction().begin();

        autoService.cargarAutosDesdeCSV("src/test/resources/autos.csv");

        em.getTransaction().commit();

        // Si quieres comprobar el contenido cargado:
        List<Auto> autos = autoRepo.findAll();

        assertFalse(autos.isEmpty());
        assertEquals("Ferrari", autos.get(0).getMarca().getMarca());

        em.close();
        emf.close();
    }
}
