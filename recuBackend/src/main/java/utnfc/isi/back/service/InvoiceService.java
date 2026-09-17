package utnfc.isi.back.service;

import jakarta.persistence.EntityManager;
import lombok.val;
import utnfc.isi.back.entity.Artista;
import utnfc.isi.back.entity.Invoice;
import utnfc.isi.back.repository.InvoiceRepository;

import java.util.List;

public class InvoiceService implements InvoiceRepository {

    private EntityManager entityManager;

    public InvoiceService(EntityManager em) {
        this.entityManager = em;
    }

    @Override
    public Invoice nuevo(Invoice invoice) {
        try {
            entityManager.getTransaction().begin();
            val nuevo = entityManager.merge(invoice);
            entityManager.getTransaction().commit();
            return nuevo;
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public List<Invoice> listarTodos() {
        return entityManager.createQuery("from Invoice", Invoice.class)
                .getResultList();
    }

    @Override
    public Invoice buscarPorId(Integer id) {
        return entityManager.find(Invoice.class, id);
    }
}
