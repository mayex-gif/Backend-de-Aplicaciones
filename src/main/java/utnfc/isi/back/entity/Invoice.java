package utnfc.isi.back.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "INVOICES")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "INVOICE_ID", nullable = false)
    private Integer invoiceId;
    @ManyToOne(optional = false)
    @JoinColumn(name = "CUSTOMER_ID", nullable = false)
    private Customer customer;
    @Column(name = "INVOICE_DATE", nullable = false)
    private Date invoiceDate;
    @Column(name = "BILLING_ADDRESS", length = 70)
    private String billingAddress;
    @Column(name = "BILLING_CITY", length = 40)
    private String billingCity;
    @Column(name = "BILLING_STATE", length = 40)
    private String billingState;
    @Column(name = "BILLING_COUNTRY", length = 40)
    private String billingCountry;
    @Column(name = "BILLING_POSTAL_CODE", length = 10)
    private String billingPostalCode;
    @Column(name = "TOTAL", nullable = false, precision = 10, scale = 2)
    private BigDecimal total;


}
