package com.clinica.model.entity;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ResultadoLaboratorio {
    private String id;
    private String pacienteId;
    private String tipoExamen;
    private LocalDate fechaSolicitud;
    private Map<String, ValorResultado> resultados;
    private String estado;

    public ResultadoLaboratorio() {
        this.resultados = new HashMap<>();
    }

    public ResultadoLaboratorio(String pacienteId, String tipoExamen, LocalDate fechaSolicitud) {
        this.id = UUID.randomUUID().toString();
        this.pacienteId = pacienteId;
        this.tipoExamen = tipoExamen;
        this.fechaSolicitud = fechaSolicitud;
        this.resultados = new HashMap<>();
        this.estado = "COMPLETADO";
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getPacienteId() { return pacienteId; }
    public void setPacienteId(String pacienteId) { this.pacienteId = pacienteId; }
    public String getTipoExamen() { return tipoExamen; }
    public void setTipoExamen(String tipoExamen) { this.tipoExamen = tipoExamen; }
    public LocalDate getFechaSolicitud() { return fechaSolicitud; }
    public void setFechaSolicitud(LocalDate fechaSolicitud) { this.fechaSolicitud = fechaSolicitud; }
    public Map<String, ValorResultado> getResultados() { return resultados; }
    public void setResultados(Map<String, ValorResultado> resultados) { this.resultados = resultados; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public static class ValorResultado {
        private String nombre;
        private Double valor;
        private String unidad;
        private Double valorMinimo;
        private Double valorMaximo;
        private Boolean dentroRango;

        public ValorResultado() {}

        public ValorResultado(String nombre, Double valor, String unidad, Double valorMinimo, Double valorMaximo) {
            this.nombre = nombre;
            this.valor = valor;
            this.unidad = unidad;
            this.valorMinimo = valorMinimo;
            this.valorMaximo = valorMaximo;
            this.dentroRango = valor >= valorMinimo && valor <= valorMaximo;
        }

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