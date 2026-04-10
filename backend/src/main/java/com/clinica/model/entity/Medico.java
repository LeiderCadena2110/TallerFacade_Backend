package com.clinica.model.entity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Medico {
    private String id;
    private String nombreCompleto;
    private String especialidad;
    private Map<LocalDate, List<LocalTime>> disponibilidad;

    public Medico() {
        this.disponibilidad = new HashMap<>();
    }

    public Medico(String nombreCompleto, String especialidad) {
        this();
        this.id = UUID.randomUUID().toString();
        this.nombreCompleto = nombreCompleto;
        this.especialidad = especialidad;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }
    public String getEspecialidad() { return especialidad; }
    public void setEspecialidad(String especialidad) { this.especialidad = especialidad; }
    public Map<LocalDate, List<LocalTime>> getDisponibilidad() { return disponibilidad; }
    public void setDisponibilidad(Map<LocalDate, List<LocalTime>> disponibilidad) { this.disponibilidad = disponibilidad; }
}