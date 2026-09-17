package net.AlcaIT.Entity;

import jakarta.persistence.*;

import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "autos")
public class Auto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "anio")
	private int anio;

	@ManyToOne
	@JoinColumn(name = "marca_id")
	private Marca marca;

	@ManyToOne
	@JoinColumn(name = "modelo_id")
	private Modelo modelo;

	@ManyToMany
	@JoinTable(
			name = "auto_tipo",
			joinColumns = @JoinColumn(name = "auto_id"),
			inverseJoinColumns = @JoinColumn(name = "tipo_id")
	)
	private Set<Tipo> tipos = new HashSet<>();

	public Auto() {
	}

	public Auto(int anio, Marca marca, Modelo modelo, Set<Tipo> tipos) {
		this.anio = anio;
		this.marca = marca;
		this.modelo = modelo;
		this.tipos = tipos;
	}

	public int getId() {
		return id;
	}

	public int getAnio() {
		return anio;
	}

	public Marca getMarca() {
		return marca;
	}

	public Modelo getModelo() {
		return modelo;
	}

	public Set<Tipo> getTipos() {
		return tipos;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setAnio(int anio) {
		this.anio = anio;
	}

	public void setMarca(Marca marca) {
		this.marca = marca;
	}

	public void setModelo(Modelo modelo) {
		this.modelo = modelo;
	}

	public void setTipos(Set<Tipo> tipos) {
		this.tipos = tipos;
	}

	public static Auto fromString(String linea) {
		String[] campos = linea.split(",");

		int anio = Integer.parseInt(campos[0]);
		Marca marca = new Marca(campos[1]);
		Modelo modelo = new Modelo(campos[2]);

		Set<Tipo> tipos = Arrays.stream(campos[3].split("\\|"))
				.map(Tipo::new)
				.collect(Collectors.toSet());

		return new Auto(anio, marca, modelo, tipos);
	}
}
