package com.clinica.model.dto;

import java.time.LocalDate;
import java.util.List;

public class PrescripcionDTO {
    private String id;
    private String pacienteId;
    private LocalDate fechaEmision;
    private List<MedicamentoDTO> medicamentos;
    private String medico;

    public PrescripcionDTO() {}

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getPacienteId() { return pacienteId; }
    public void setPacienteId(String pacienteId) { this.pacienteId = pacienteId; }
    public LocalDate getFechaEmision() { return fechaEmision; }
    public void setFechaEmision(LocalDate fechaEmision) { this.fechaEmision = fechaEmision; }
    public List<MedicamentoDTO> getMedicamentos() { return medicamentos; }
    public void setMedicamentos(List<MedicamentoDTO> medicamentos) { this.medicamentos = medicamentos; }
    public String getMedico() { return medico; }
    public void setMedico(String medico) { this.medico = medico; }
}