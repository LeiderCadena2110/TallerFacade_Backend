package com.clinica.service;

import com.clinica.model.dto.PacienteDTO;
import com.clinica.model.entity.Paciente;
import com.clinica.repository.InMemoryRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PacienteService {

    private final InMemoryRepository repository;

    public PacienteService(InMemoryRepository repository) {
        this.repository = repository;
    }

    public PacienteDTO registerPatient(PacienteDTO pacienteDTO) {
        if (validateUniqueDocument(pacienteDTO.getNumeroDocumento())) {
            throw new IllegalArgumentException("El número de documento ya está registrado");
        }

        Paciente paciente = new Paciente(
                pacienteDTO.getNombreCompleto(),
                pacienteDTO.getNumeroDocumento(),
                pacienteDTO.getFechaNacimiento(),
                pacienteDTO.getCorreoElectronico(),
                pacienteDTO.getTelefono()
        );

        if (pacienteDTO.getListaAlergias() != null) {
            paciente.getListaAlergias().addAll(pacienteDTO.getListaAlergias());
        }

        repository.savePaciente(paciente);
        return mapToDTO(paciente);
    }

    public boolean validateUniqueDocument(String numeroDocumento) {
        return repository.findPacienteByNumeroDocumento(numeroDocumento).isPresent();
    }

    public Optional<PacienteDTO> getPatientProfile(String pacienteId) {
        return repository.findPacienteById(pacienteId).map(this::mapToDTO);
    }

    public Optional<Paciente> getPacienteEntity(String pacienteId) {
        return repository.findPacienteById(pacienteId);
    }

    private PacienteDTO mapToDTO(Paciente paciente) {
        PacienteDTO dto = new PacienteDTO();
        dto.setId(paciente.getId());
        dto.setNombreCompleto(paciente.getNombreCompleto());
        dto.setNumeroDocumento(paciente.getNumeroDocumento());
        dto.setFechaNacimiento(paciente.getFechaNacimiento());
        dto.setCorreoElectronico(paciente.getCorreoElectronico());
        dto.setTelefono(paciente.getTelefono());
        dto.setListaAlergias(paciente.getListaAlergias());
        return dto;
    }
}