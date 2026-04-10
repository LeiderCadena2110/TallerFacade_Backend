package com.clinica.model.dto;

public class MedicamentoDTO {
    private String nombre;
    private String dosis;
    private String frecuencia;
    private String duracion;

    public MedicamentoDTO() {}

    public MedicamentoDTO(String nombre, String dosis, String frecuencia, String duracion) {
        this.nombre = nombre;
        this.dosis = dosis;
        this.frecuencia = frecuencia;
        this.duracion = duracion;
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getDosis() { return dosis; }
    public void setDosis(String dosis) { this.dosis = dosis; }
    public String getFrecuencia() { return frecuencia; }
    public void setFrecuencia(String frecuencia) { this.frecuencia = frecuencia; }
    public String getDuracion() { return duracion; }
    public void setDuracion(String duracion) { this.duracion = duracion; }
}