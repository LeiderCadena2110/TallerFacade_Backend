package com.clinica.model.dto;

import java.time.LocalDate;
import java.util.Map;

public class ResultadoLaboratorioDTO {
    private String id;
    private String pacienteId;
    private String tipoExamen;
    private LocalDate fechaSolicitud;
    private Map<String, ValorResultadoDTO> resultados;
    private String estado;

    public ResultadoLaboratorioDTO() {}

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getPacienteId() { return pacienteId; }
    public void setPacienteId(String pacienteId) { this.pacienteId = pacienteId; }
    public String getTipoExamen() { return tipoExamen; }
    public void setTipoExamen(String tipoExamen) { this.tipoExamen = tipoExamen; }
    public LocalDate getFechaSolicitud() { return fechaSolicitud; }
    public void setFechaSolicitud(LocalDate fechaSolicitud) { this.fechaSolicitud = fechaSolicitud; }
    public Map<String, ValorResultadoDTO> getResultados() { return resultados; }
    public void setResultados(Map<String, ValorResultadoDTO> resultados) { this.resultados = resultados; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public static class ValorResultadoDTO {
        private String nombre;
        private Double valor;
        private String unidad;
        private Double valorMinimo;
        private Double valorMaximo;
        private Boolean dentroRango;

        public ValorResultadoDTO() {}

        public String getNombre() { return nombre; }
        public void setNombre(String nombre) { this.nombre = nombre; }
        public Double getValor() { return valor; }
        public void setValor(Double valor) { this.valor = valor; }
        public String getUnidad() { return unidad; }
        public void setUnidad(String unidad) { this.unidad = unidad; }
        public Double getValorMinimo() { return valorMinimo; }
        public void setValorMinimo(Double valorMinimo) { this.valorMinimo = valorMinimo; }
        public Double getValorMaximo() { return valorMaximo; }
        public void setValorMaximo(Double valorMaximo) { this.valorMaximo = valorMaximo; }
        public Boolean getDentroRango() { return dentroRango; }
        public void setDentroRango(Boolean dentroRango) { this.dentroRango = dentroRango; }
    }
}