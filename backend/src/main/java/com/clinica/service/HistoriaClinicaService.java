package com.clinica.service;

import com.clinica.model.entity.ConsultaMedica;
import com.clinica.repository.InMemoryRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class HistoriaClinicaService {

    private final InMemoryRepository repository;

    public HistoriaClinicaService(InMemoryRepository repository) {
        this.repository = repository;
    }

    public Map<String, Object> getPatientHistory(String pacienteId) {
        List<ConsultaMedica> consultas = repository.findConsultasByPacienteId(pacienteId);
        
        Map<String, Object> history = new HashMap<>();
        history.put("consultas", consultas);
        
        List<String> diagnosticos = consultas.stream()
                .map(ConsultaMedica::getDiagnostico)
                .distinct()
                .toList();
        history.put("diagnosticos", diagnosticos);
        
        return history;
    }

    public void registerConsultation(String pacienteId, String motivo, String diagnostico, String notas) {
        ConsultaMedica consulta = new ConsultaMedica(
                pacienteId,
                LocalDate.now(),
                motivo,
                diagnostico,
                notas
        );
        repository.saveConsulta(consulta);
    }

    public List<ConsultaMedica> getConsultasByPacienteId(String pacienteId) {
        return repository.findConsultasByPacienteId(pacienteId);
    }
}