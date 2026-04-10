package com.clinica.model.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Paciente {
    private String id;
    private String nombreCompleto;
    private String numeroDocumento;
    private LocalDate fechaNacimiento;
    private String correoElectronico;
    private String telefono;
    private List<String> listaAlergias;

    public Paciente() {
        this.id = UUID.randomUUID().toString();
        this.listaAlergias = new ArrayList<>();
    }

    public Paciente(String nombreCompleto, String numeroDocumento, LocalDate fechaNacimiento, 
                    String correoElectronico, String telefono) {
        this();
        this.nombreCompleto = nombreCompleto;
        this.numeroDocumento = numeroDocumento;
        this.fechaNacimiento = fechaNacimiento;
        this.correoElectronico = correoElectronico;
        this.telefono = telefono;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }
    public String getNumeroDocumento() { return numeroDocumento; }
    public void setNumeroDocumento(String numeroDocumento) { this.numeroDocumento = numeroDocumento; }
    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(LocalDate fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }
    public String getCorreoElectronico() { return correoElectronico; }
    public void setCorreoElectronico(String correoElectronico) { this.correoElectronico = correoElectronico; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public List<String> getListaAlergias() { return listaAlergias; }
    public void setListaAlergias(List<String> listaAlergias) { this.listaAlergias = listaAlergias; }
}