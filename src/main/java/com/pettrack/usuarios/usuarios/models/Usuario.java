package com.pettrack.usuarios.usuarios.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;


@Entity
@Table(name = "usuario")
public class Usuario {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_usuario")
  private Long id;

  @Column
  @NotBlank(message = "El nombre no puede estar vacío")
  @Size(max = 100, message = "El nombre no puede exceder los 100 caracteres")
  private String nombre;

  @Column
  private String apellido;

  @Column
  @Pattern(regexp = "\\d{7,8}-[\\dkK]", message = "RUT inválido")
  private String rut;

  @Column
  @Email(message = "Correo inválido")
  private String correo;

  @Column
  @Pattern(regexp = "\\+56\\d{9}", message = "Teléfono inválido")
  private String telefono;

  @Column
  private String direccion;

  @Enumerated(EnumType.STRING)
  private Rol rol;

  @SuppressWarnings("squid:S00115")
  public enum Rol {
    usuario,
    veterinario
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public String getApellido() {
    return apellido;
  }

  public void setApellido(String apellido) {
    this.apellido = apellido;
  }

  public String getRut() {
    return rut;
  }

  public void setRut(String rut) {
    this.rut = rut;
  }

  public String getCorreo() {
    return correo;
  }

  public void setCorreo(String correo) {
    this.correo = correo;
  }

  public String getTelefono() {
    return telefono;
  }

  public void setTelefono(String telefono) {
    this.telefono = telefono;
  }

  public String getDireccion() {
    return direccion;
  }

  public void setDireccion(String direccion) {
    this.direccion = direccion;
  }

  public Rol getRol() {
    return rol;
  }

  public void setRol(Rol rol) {
    this.rol = rol;
  }
}
