package com.clinica.service;

import com.clinica.model.dto.MedicamentoDTO;
import com.clinica.model.dto.PrescripcionDTO;
import com.clinica.model.dto.PrescripcionRequestDTO;
import com.clinica.model.entity.Medicamento;
import com.clinica.model.entity.Prescripcion;
import com.clinica.repository.InMemoryRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PrescripcionService {

    private final InMemoryRepository repository;
    private final PacienteService pacienteService;

    public PrescripcionService(InMemoryRepository repository, PacienteService pacienteService) {
        this.repository = repository;
        this.pacienteService = pacienteService;
    }

    public PrescripcionDTO generatePrescription(PrescripcionRequestDTO request) {
        List<String> alergias = pacienteService.getPacienteEntity(request.getPacienteId())
                .map(p -> p.getListaAlergias())
                .orElse(new ArrayList<>());

        List<String> medicamentosIngresados = request.getMedicamentos().stream()
                .map(MedicamentoDTO::getNombre)
                .map(String::toLowerCase)
                .toList();

        for (String alergia : alergias) {
            if (medicamentosIngresados.contains(alergia.toLowerCase())) {
                throw new IllegalArgumentException("Alerta: El paciente es alérgico a " + alergia);
            }
        }

        List<Medicamento> medicamentos = request.getMedicamentos().stream()
                .map(this::mapToEntity)
                .collect(Collectors.toList());

        Prescripcion prescripcion = new Prescripcion(
                request.getPacienteId(),
                LocalDate.now(),
                medicamentos,
                request.getMedico()
        );

        repository.savePrescripcion(prescripcion);
        return mapToDTO(prescripcion);
    }

    public List<PrescripcionDTO> getPrescripcionesByPacienteId(String pacienteId) {
        return repository.findPrescripcionesByPacienteId(pacienteId).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private Medicamento mapToEntity(MedicamentoDTO dto) {
        return new Medicamento(dto.getNombre(), dto.getDosis(), dto.getFrecuencia(), dto.getDuracion());
    }

    private PrescripcionDTO mapToDTO(Prescripcion prescripcion) {
        PrescripcionDTO dto = new PrescripcionDTO();
        dto.setId(prescripcion.getId());
        dto.setPacienteId(prescripcion.getPacienteId());
        dto.setFechaEmision(prescripcion.getFechaEmision());
        dto.setMedico(prescripcion.getMedico());
        dto.setMedicamentos(prescripcion.getMedicamentos().stream()
                .map(this::mapMedicamentoToDTO)
                .collect(Collectors.toList()));
        return dto;
    }

    private MedicamentoDTO mapMedicamentoToDTO(Medicamento medicamento) {
        return new MedicamentoDTO(
                medicamento.getNombre(),
                medicamento.getDosis(),
                medicamento.getFrecuencia(),
                medicamento.getDuracion()
        );
    }
}