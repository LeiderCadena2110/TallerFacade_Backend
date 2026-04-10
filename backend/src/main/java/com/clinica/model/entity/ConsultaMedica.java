package com.clinica.model.entity;

import java.time.LocalDate;
import java.util.UUID;

public class ConsultaMedica {
    private String id;
    private String pacienteId;
    private LocalDate fecha;
    private String motivo;
    private String diagnostico;
    private String notas;

    public ConsultaMedica() {}

    public ConsultaMedica(String pacienteId, LocalDate fecha, String motivo, String diagnostico, String notas) {
        this.id = UUID.randomUUID().toString();
        this.pacienteId = pacienteId;
        this.fecha = fecha;
        this.motivo = motivo;
        this.diagnostico = diagnostico;
        this.notas = notas;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getPacienteId() { return pacienteId; }
    public void setPacienteId(String pacienteId) { this.pacienteId = pacienteId; }
    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
    public String getMotivo() { return motivo; }
    public void setMotivo(String motivo) { this.motivo = motivo; }
    public String getDiagnostico() { return diagnostico; }
    public void setDiagnostico(String diagnostico) { this.diagnostico = diagnostico; }
    public String getNotas() { return notas; }
    public void setNotas(String notas) { this.notas = notas; }
}