package com.clinica.model.entity;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class Prescripcion {
    private String id;
    private String pacienteId;
    private LocalDate fechaEmision;
    private List<Medicamento> medicamentos;
    private String medico;

    public Prescripcion() {}

    public Prescripcion(String pacienteId, LocalDate fechaEmision, List<Medicamento> medicamentos, String medico) {
        this.id = UUID.randomUUID().toString();
        this.pacienteId = pacienteId;
        this.fechaEmision = fechaEmision;
        this.medicamentos = medicamentos;
        this.medico = medico;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getPacienteId() { return pacienteId; }
    public void setPacienteId(String pacienteId) { this.pacienteId = pacienteId; }
    public LocalDate getFechaEmision() { return fechaEmision; }
    public void setFechaEmision(LocalDate fechaEmision) { this.fechaEmision = fechaEmision; }
    public List<Medicamento> getMedicamentos() { return medicamentos; }
    public void setMedicamentos(List<Medicamento> medicamentos) { this.medicamentos = medicamentos; }
    public String getMedico() { return medico; }
    public void setMedico(String medico) { this.medico = medico; }
}