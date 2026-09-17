package net.AlcaIT.Entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "LIBRO")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "TITULO")
    private String titulo;

    @ManyToOne
    @JoinColumn(name = "AUTOR_ID")
    private Autor autor;

    @Column(name = "ESTADO")
    private Estado estado;

    public enum Estado {
        Disponible, Prestado, Reservado, Roto, Comprado;
    }

    @ManyToMany
    @JoinTable(
            name = "LIBRO_GENERO",
            joinColumns = @JoinColumn(name = "LIBRO_ID"),
            inverseJoinColumns = @JoinColumn(name = "GENERO_ID")
    )
    private List<Genero> generos;

    public Libro() {}
    public Libro(String titulo, Autor autor, Estado estado) {
        this.titulo = titulo;
        this.autor = autor;
        this.estado = estado;
    }

    public String getTitulo() {
        return titulo;
    }

    public Autor getAutor() {
        return autor;
    }

    public Estado getEstado() {
        return estado;
    }

    public List<Genero> getGeneros() {
        return generos;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public void setGeneros(List<Genero> generos) {
        this.generos = generos;
    }
}
