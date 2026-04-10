package com.clinica.repository;

import com.clinica.model.entity.*;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
public class InMemoryRepository {

    private final Map<String, Paciente> pacientes = new ConcurrentHashMap<>();
    private final Map<String, Medico> medicos = new ConcurrentHashMap<>();
    private final Map<String, Cita> citas = new ConcurrentHashMap<>();
    private final Map<String, ConsultaMedica> consultas = new ConcurrentHashMap<>();
    private final Map<String, Prescripcion> prescripciones = new ConcurrentHashMap<>();
    private final Map<String, ResultadoLaboratorio> resultadosLaboratorio = new ConcurrentHashMap<>();

    public InMemoryRepository() {
        initializePredefinedData();
    }

    private void initializePredefinedData() {
        Medico drJuanPerez = new Medico("Dr. Juan Pérez", "Cardiología");
        drJuanPerez.setId("med-001");
        
        Medico draMariaGonzalez = new Medico("Dra. María González", "Cardiología");
        draMariaGonzalez.setId("med-002");
        
        Medico drCarlosRodriguez = new Medico("Dr. Carlos Rodríguez", "Dermatología");
        drCarlosRodriguez.setId("med-003");
        
        Medico draAnaMartinez = new Medico("Dra. Ana Martínez", "Medicina General");
        draAnaMartinez.setId("med-004");
        
        Medico drLuisSanchez = new Medico("Dr. Luis Sánchez", "Medicina General");
        drLuisSanchez.setId("med-005");

        medicos.put(drJuanPerez.getId(), drJuanPerez);
        medicos.put(draMariaGonzalez.getId(), draMariaGonzalez);
        medicos.put(drCarlosRodriguez.getId(), drCarlosRodriguez);
        medicos.put(draAnaMartinez.getId(), draAnaMartinez);
        medicos.put(drLuisSanchez.getId(), drLuisSanchez);

        LocalDate today = LocalDate.now();
        for (Medico medico : medicos.values()) {
            Map<LocalDate, List<LocalTime>> disponibilidad = new HashMap<>();
            for (int i = 1; i <= 7; i++) {
                LocalDate date = today.plusDays(i);
                if (date.getDayOfWeek() != java.time.DayOfWeek.SATURDAY && 
                    date.getDayOfWeek() != java.time.DayOfWeek.SUNDAY) {
                    List<LocalTime> slots = new ArrayList<>();
                    slots.add(LocalTime.of(9, 0));
                    slots.add(LocalTime.of(10, 0));
                    slots.add(LocalTime.of(11, 0));
                    slots.add(LocalTime.of(14, 0));
                    slots.add(LocalTime.of(15, 0));
                    slots.add(LocalTime.of(16, 0));
                    disponibilidad.put(date, slots);
                }
            }
            medico.setDisponibilidad(disponibilidad);
        }
    }

    public Paciente savePaciente(Paciente paciente) {
        pacientes.put(paciente.getId(), paciente);
        return paciente;
    }

    public Optional<Paciente> findPacienteById(String id) {
        return Optional.ofNullable(pacientes.get(id));
    }

    public Optional<Paciente> findPacienteByNumeroDocumento(String numeroDocumento) {
        return pacientes.values().stream()
                .filter(p -> p.getNumeroDocumento().equals(numeroDocumento))
                .findFirst();
    }

    public Collection<Medico> getAllMedicos() {
        return medicos.values();
    }

    public Optional<Medico> findMedicoById(String id) {
        return Optional.ofNullable(medicos.get(id));
    }

    public List<Medico> findMedicosByEspecialidad(String especialidad) {
        return medicos.values().stream()
                .filter(m -> m.getEspecialidad().equals(especialidad))
                .collect(Collectors.toList());
    }

    public Collection<Medico> getMedicos() {
        return medicos.values();
    }

    public Cita saveCita(Cita cita) {
        citas.put(cita.getId(), cita);
        return cita;
    }

    public Optional<Cita> findCitaById(String id) {
        return Optional.ofNullable(citas.get(id));
    }

    public List<Cita> findCitasByPacienteId(String pacienteId) {
        return citas.values().stream()
                .filter(c -> c.getPacienteId().equals(pacienteId))
                .collect(Collectors.toList());
    }

    public List<Cita> findCitasPasadasByPacienteId(String pacienteId) {
        LocalDateTime now = LocalDateTime.now();
        return citas.values().stream()
                .filter(c -> c.getPacienteId().equals(pacienteId) && c.getFechaHora().isBefore(now))
                .collect(Collectors.toList());
    }

    public List<Cita> findCitasFuturasByPacienteId(String pacienteId) {
        LocalDateTime now = LocalDateTime.now();
        return citas.values().stream()
                .filter(c -> c.getPacienteId().equals(pacienteId) && c.getFechaHora().isAfter(now) && "AGENDADA".equals(c.getEstado()))
                .collect(Collectors.toList());
    }

    public void removeAvailableSlot(String medicoId, LocalDate date, LocalTime time) {
        Medico medico = medicos.get(medicoId);
        if (medico != null && medico.getDisponibilidad().containsKey(date)) {
            List<LocalTime> slots = medico.getDisponibilidad().get(date);
            slots.remove(time);
        }
    }

    public ConsultaMedica saveConsulta(ConsultaMedica consulta) {
        consultas.put(consulta.getId(), consulta);
        return consulta;
    }

    public List<ConsultaMedica> findConsultasByPacienteId(String pacienteId) {
        return consultas.values().stream()
                .filter(c -> c.getPacienteId().equals(pacienteId))
                .collect(Collectors.toList());
    }

    public Prescripcion savePrescripcion(Prescripcion prescripcion) {
        prescripciones.put(prescripcion.getId(), prescripcion);
        return prescripcion;
    }

    public List<Prescripcion> findPrescripcionesByPacienteId(String pacienteId) {
        return prescripciones.values().stream()
                .filter(p -> p.getPacienteId().equals(pacienteId))
                .collect(Collectors.toList());
    }

    public ResultadoLaboratorio saveResultadoLaboratorio(ResultadoLaboratorio resultado) {
        resultadosLaboratorio.put(resultado.getId(), resultado);
        return resultado;
    }

    public List<ResultadoLaboratorio> findResultadosLaboratorioByPacienteId(String pacienteId) {
        return resultadosLaboratorio.values().stream()
                .filter(r -> r.getPacienteId().equals(pacienteId))
                .collect(Collectors.toList());
    }
}