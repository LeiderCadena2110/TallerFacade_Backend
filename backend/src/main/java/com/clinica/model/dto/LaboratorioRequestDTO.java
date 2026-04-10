package com.clinica.model.dto;

import java.util.List;

public class LaboratorioRequestDTO {
    private String pacienteId;
    private List<String> examenes;

    public LaboratorioRequestDTO() {}

    public String getPacienteId() { return pacienteId; }
    public void setPacienteId(String pacienteId) { this.pacienteId = pacienteId; }
    public List<String> getExamenes() { return examenes; }
    public void setExamenes(List<String> examenes) { this.examenes = examenes; }
}