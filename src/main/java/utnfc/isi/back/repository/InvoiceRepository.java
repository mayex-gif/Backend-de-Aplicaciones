package utnfc.isi.back.repository;

import utnfc.isi.back.entity.Invoice;

import java.util.List;

public interface InvoiceRepository {
    Invoice nuevo(Invoice invoice);
    List<Invoice> listarTodos();
    Invoice buscarPorId(Integer id);
}
