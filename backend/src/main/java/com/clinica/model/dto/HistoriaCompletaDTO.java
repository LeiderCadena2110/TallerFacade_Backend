package com.clinica.model.dto;

import java.util.List;

public class HistoriaCompletaDTO {
    private String pacienteId;
    private List<CitaDTO> citasPasadas;
    private List<PrescripcionDTO> prescripciones;
    private List<ResultadoLaboratorioDTO> resultadosLaboratorio;

    public HistoriaCompletaDTO() {}

    public String getPacienteId() { return pacienteId; }
    public void setPacienteId(String pacienteId) { this.pacienteId = pacienteId; }
    public List<CitaDTO> getCitasPasadas() { return citasPasadas; }
    public void setCitasPasadas(List<CitaDTO> citasPasadas) { this.citasPasadas = citasPasadas; }
    public List<PrescripcionDTO> getPrescripciones() { return prescripciones; }
    public void setPrescripciones(List<PrescripcionDTO> prescripciones) { this.prescripciones = prescripciones; }
    public List<ResultadoLaboratorioDTO> getResultadosLaboratorio() { return resultadosLaboratorio; }
    public void setResultadosLaboratorio(List<ResultadoLaboratorioDTO> resultadosLaboratorio) { this.resultadosLaboratorio = resultadosLaboratorio; }
}