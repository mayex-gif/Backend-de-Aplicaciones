package utnfc.isi.back.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "CUSTOMERS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CUSTOMER_ID", nullable = false)
    private Integer customerId;
    @Column(name = "FIRST_NAME", nullable = false, length = 40)
    private String firstName;
    @Column(name = "LAST_NAME", nullable = false, length = 20)
    private String lastName;
    @Column(name = "COMPANY", length = 80)
    private String company;
    @Column(name = "ADDRESS", length = 70)
    private String address;
    @Column(name = "CITY", length = 40)
    private String city;
    @Column(name = "STATE", length = 40)
    private String state;
    @Column(name = "COUNTRY", length = 40)
    private String country;
    @Column(name = "POSTAL_CODE", length = 10)
    private String postalCode;
    @Column(name = "PHONE", length = 24)
    private String phone;
    @Column(name = "FAX", length = 24)
    private String fax;
    @Column(name = "EMAIL", length = 60, nullable = false)
    private String email;
    @ManyToOne(optional = true)
    @JoinColumn(name = "SUPPORT_REP_ID")
    private Empleado supportRep;
}
