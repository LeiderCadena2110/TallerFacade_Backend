package com.clinica.model.dto;

import java.util.List;

public class PrescripcionRequestDTO {
    private String pacienteId;
    private String medico;
    private List<MedicamentoDTO> medicamentos;

    public PrescripcionRequestDTO() {}

    public String getPacienteId() { return pacienteId; }
    public void setPacienteId(String pacienteId) { this.pacienteId = pacienteId; }
    public String getMedico() { return medico; }
    public void setMedico(String medico) { this.medico = medico; }
    public List<MedicamentoDTO> getMedicamentos() { return medicamentos; }
    public void setMedicamentos(List<MedicamentoDTO> medicamentos) { this.medicamentos = medicamentos; }
}