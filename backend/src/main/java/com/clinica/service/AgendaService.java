package com.clinica.service;

import com.clinica.model.dto.CitaDTO;
import com.clinica.model.dto.CitaRequestDTO;
import com.clinica.model.dto.MedicoDTO;
import com.clinica.model.entity.Cita;
import com.clinica.model.entity.Medico;
import com.clinica.repository.InMemoryRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AgendaService {

    private final InMemoryRepository repository;

    public AgendaService(InMemoryRepository repository) {
        this.repository = repository;
    }

    public List<MedicoDTO> getMedicosByEspecialidad(String especialidad) {
        List<Medico> medicos;
        if (especialidad == null || especialidad.isEmpty()) {
            medicos = (List<Medico>) repository.getAllMedicos();
        } else {
            medicos = repository.findMedicosByEspecialidad(especialidad);
        }
        return medicos.stream()
                .map(this::mapMedicoToDTO)
                .collect(Collectors.toList());
    }

    public List<LocalTime> getAvailableSlots(String medicoId, String fecha) {
        LocalDate date = LocalDate.parse(fecha);
        Optional<Medico> medico = repository.findMedicoById(medicoId);
        
        if (medico.isEmpty()) {
            return List.of();
        }

        return medico.get().getDisponibilidad().getOrDefault(date, List.of());
    }

    public CitaDTO scheduleAppointment(CitaRequestDTO request) {
        Optional<Medico> medico = repository.findMedicoById(request.getMedicoId());
        
        if (medico.isEmpty()) {
            throw new IllegalArgumentException("Médico no encontrado");
        }

        Cita cita = new Cita(
                request.getPacienteId(),
                request.getMedicoId(),
                medico.get().getNombreCompleto(),
                request.getEspecialidad(),
                request.getFechaHora()
        );

        repository.saveCita(cita);
        
        LocalDate date = request.getFechaHora().toLocalDate();
        LocalTime time = request.getFechaHora().toLocalTime();
        repository.removeAvailableSlot(request.getMedicoId(), date, time);

        return mapToDTO(cita);
    }

    public Optional<CitaDTO> getCitaById(String citaId) {
        return repository.findCitaById(citaId).map(this::mapToDTO);
    }

    public List<CitaDTO> getCitasFuturasByPacienteId(String pacienteId) {
        return repository.findCitasFuturasByPacienteId(pacienteId).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public boolean cancelAppointment(String citaId) {
        Optional<Cita> cita = repository.findCitaById(citaId);
        if (cita.isPresent()) {
            cita.get().setEstado("CANCELADA");
            return true;
        }
        return false;
    }

    private MedicoDTO mapMedicoToDTO(Medico medico) {
        return new MedicoDTO(medico.getId(), medico.getNombreCompleto(), medico.getEspecialidad());
    }

    private CitaDTO mapToDTO(Cita cita) {
        CitaDTO dto = new CitaDTO();
        dto.setId(cita.getId());
        dto.setPacienteId(cita.getPacienteId());
        dto.setMedicoId(cita.getMedicoId());
        dto.setNombreMedico(cita.getNombreMedico());
        dto.setEspecialidad(cita.getEspecialidad());
        dto.setFechaHora(cita.getFechaHora());
        dto.setEstado(cita.getEstado());
        dto.setRecordatorio("Recuerde asistir a su cita el " + cita.getFechaHora().toLocalDate() + " a las " + cita.getFechaHora().toLocalTime());
        return dto;
    }
}