package com.clinica.model.entity;

import java.time.LocalDateTime;
import java.util.UUID;

public class Cita {
    private String id;
    private String pacienteId;
    private String medicoId;
    private String nombreMedico;
    private String especialidad;
    private LocalDateTime fechaHora;
    private String estado;

    public Cita() {}

    public Cita(String pacienteId, String medicoId, String nombreMedico, String especialidad, LocalDateTime fechaHora) {
        this.id = UUID.randomUUID().toString();
        this.pacienteId = pacienteId;
        this.medicoId = medicoId;
        this.nombreMedico = nombreMedico;
        this.especialidad = especialidad;
        this.fechaHora = fechaHora;
        this.estado = "AGENDADA";
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getPacienteId() { return pacienteId; }
    public void setPacienteId(String pacienteId) { this.pacienteId = pacienteId; }
    public String getMedicoId() { return medicoId; }
    public void setMedicoId(String medicoId) { this.medicoId = medicoId; }
    public String getNombreMedico() { return nombreMedico; }
    public void setNombreMedico(String nombreMedico) { this.nombreMedico = nombreMedico; }
    public String getEspecialidad() { return especialidad; }
    public void setEspecialidad(String especialidad) { this.especialidad = especialidad; }
    public LocalDateTime getFechaHora() { return fechaHora; }
    public void setFechaHora(LocalDateTime fechaHora) { this.fechaHora = fechaHora; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}