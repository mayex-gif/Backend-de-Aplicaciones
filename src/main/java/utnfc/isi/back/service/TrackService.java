package utnfc.isi.back.service;

import jakarta.persistence.EntityManager;
import lombok.val;
import utnfc.isi.back.entity.Invoice;
import utnfc.isi.back.entity.Track;
import utnfc.isi.back.repository.TrackRepository;

import java.util.List;

public class TrackService implements TrackRepository {

    private EntityManager entityManager;

    public TrackService(EntityManager em) {
        this.entityManager = em;
    }

    @Override
    public void nuevo(Track track) {
        entityManager.persist(track);
    }

    @Override
    public List<Track> listarTodos() {
        return entityManager.createQuery("from Track", Track.class)
                .getResultList();
    }

    @Override
    public Track buscarPorId(Integer id) {
        return entityManager.find(Track.class, id);
    }

    //EJEMPLO
    public List<Track> findTop5ByBytes() {
        return entityManager.createQuery(
                        "SELECT t FROM Track t ORDER BY t.bytes DESC", Track.class)
                // Usamos setMaxResults para limitar la consulta a N elementos (el Top N)
                .setMaxResults(5)
                .getResultList();
    }

    public List<Track> findTracksByUnitPriceGreaterThan(double price) {
        return entityManager.createQuery(
                        // Nota: Asegúrate de que 'unitPrice' en la entidad sea un double o BigDecimal
                        "SELECT t FROM Track t WHERE t.unitPrice > :price", Track.class)
                // Usamos setParameter para evitar inyección de SQL y facilitar la lectura
                .setParameter("price", price)
                .getResultList();
    }
}
