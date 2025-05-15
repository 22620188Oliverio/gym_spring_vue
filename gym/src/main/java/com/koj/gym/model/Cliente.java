package com.koj.gym.model;

import jakarta.persistence.*;

@Entity
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String Objetivo;
    private String nombre;
    private String apellido;
    private String telefono;
    private String email;
    private String imagenURL;
    private String tipoMembresia;
    

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getObjetivo() {
        return Objetivo;
    }
    public void setObjetivo(String objetivo) {
        Objetivo = objetivo;
    }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }
    
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getImagenURL() { return imagenURL; }
    public void setImagenURL(String imagenURL) { this.imagenURL = imagenURL; }

    public String getTipoMembresia() {return tipoMembresia;}
    public void setTipoMembresia(String tipoMembresia) {this.tipoMembresia = tipoMembresia;}   
}