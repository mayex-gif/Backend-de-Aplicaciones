package utnfc.isi.back.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "EMPLOYEES")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Empleado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EMPLOYEE_ID", nullable = false)
    private Integer employeeId;
    @Column(name = "LAST_NAME", nullable = false, length = 20)
    private String lastName;
    @Column(name = "FIRST_NAME", nullable = false, length = 20)
    private String firstName;
    @Column(name = "TITLE", length = 30)
    private String title;
    @ManyToOne(optional = true)
    @JoinColumn(name = "REPORTS_TO")
    private Empleado reportsTo;
    @Column(name = "BIRTH_DATE")
    private Date birthDate;
    @Column(name = "HIRE_DATE")
    private Date hireDate;
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
    @Column(name = "EMAIL", length = 60)
    private String email;


}
