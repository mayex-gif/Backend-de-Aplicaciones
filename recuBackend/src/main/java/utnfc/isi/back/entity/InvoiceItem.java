package utnfc.isi.back.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "INVOICE_ITEMS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "INVOICE_LINE_ID", nullable = false)
    private int invoiceLineId;
    @ManyToOne(optional = false)
    @JoinColumn(name = "INVOICE_ID", nullable = false)
    private Invoice invoice;
    @ManyToOne(optional = false)
    @JoinColumn(name = "TRACK_ID", nullable = false)
    private Track track;
    @Column(name = "UNIT_PRICE", nullable = false, precision = 10, scale = 2)
    private BigDecimal unitPrice;
    @Column(name = "QUANTITY", nullable = false)
    private Integer quantity;
}
