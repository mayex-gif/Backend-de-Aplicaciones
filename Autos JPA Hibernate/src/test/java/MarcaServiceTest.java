import net.AlcaIT.Entity.Marca;
import net.AlcaIT.Repository.MarcaRepository;
import net.AlcaIT.Repository.MarcaRepositoryImpl;
import net.AlcaIT.Service.MarcaService;
import org.junit.jupiter.api.*;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import static org.junit.jupiter.api.Assertions.*;

public class MarcaServiceTest {

    static EntityManagerFactory emf;
    static EntityManager em;
    static MarcaService marcaService;

    @BeforeAll
    static void init() {
        emf = Persistence.createEntityManagerFactory("MiUnidad");
        em = emf.createEntityManager();
        MarcaRepository marcaRepo = new MarcaRepositoryImpl(em);
        marcaService = new MarcaService(marcaRepo);

        em.getTransaction().begin();
    }

    @AfterAll
    static void close() {
        if (em.getTransaction().isActive()) em.getTransaction().rollback();
        em.close();
        emf.close();
    }

    @Test
    void testCrearOMarcarCrear() {
        Marca m = marcaService.obtenerOMarcarCrear("Ferrari");
        assertNotNull(m);
        assertEquals("Ferrari", m.getMarca());

        Marca m2 = marcaService.obtenerOMarcarCrear("Ferrari");
        assertSame(m, m2); // Debe usar cache
    }
}
