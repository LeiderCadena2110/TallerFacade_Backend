package com.clinica.service;

import com.clinica.model.dto.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ClinicaFacade {

    private final PacienteService pacienteService;
    private final AgendaService agendaService;
    private final HistoriaClinicaService historiaClinicaService;
    private final PrescripcionService prescripcionService;
    private final LaboratorioService laboratorioService;

    public ClinicaFacade(PacienteService pacienteService, 
                         AgendaService agendaService,
                         HistoriaClinicaService historiaClinicaService,
                         PrescripcionService prescripcionService,
                         LaboratorioService laboratorioService) {
        this.pacienteService = pacienteService;
        this.agendaService = agendaService;
        this.historiaClinicaService = historiaClinicaService;
        this.prescripcionService = prescripcionService;
        this.laboratorioService = laboratorioService;
    }

    public CitaDTO agendarCita(String pacienteId, String especialidad, String medicoId, LocalDateTime fechaHora) {
        pacienteService.getPatientProfile(pacienteId)
                .orElseThrow(() -> new IllegalArgumentException("Paciente no encontrado"));

        CitaRequestDTO request = new CitaRequestDTO();
        request.setPacienteId(pacienteId);
        request.setMedicoId(medicoId);
        request.setEspecialidad(especialidad);
        request.setFechaHora(fechaHora);

        return agendaService.scheduleAppointment(request);
    }

    public HistoriaCompletaDTO verHistoriaCompleta(String pacienteId) {
        HistoriaCompletaDTO historiaCompleta = new HistoriaCompletaDTO();
        historiaCompleta.setPacienteId(pacienteId);

        List<CitaDTO> citasPasadas = agendaService.getCitasFuturasByPacienteId(pacienteId);
        historiaCompleta.setCitasPasadas(citasPasadas);

        List<PrescripcionDTO> prescripciones = prescripcionService.getPrescripcionesByPacienteId(pacienteId);
        historiaCompleta.setPrescripciones(prescripciones);

        List<ResultadoLaboratorioDTO> resultados = laboratorioService.getResultadosByPacienteId(pacienteId);
        historiaCompleta.setResultadosLaboratorio(resultados);

        return historiaCompleta;
    }

    public PrescripcionDTO generarPrescripcion(String pacienteId, List<MedicamentoDTO> medicamentos, String medico) {
        PrescripcionRequestDTO request = new PrescripcionRequestDTO();
        request.setPacienteId(pacienteId);
        request.setMedicamentos(medicamentos);
        request.setMedico(medico);

        return prescripcionService.generatePrescription(request);
    }

    public List<ResultadoLaboratorioDTO> solicitarExamenes(String pacienteId, List<String> examenes) {
        pacienteService.getPatientProfile(pacienteId)
                .orElseThrow(() -> new IllegalArgumentException("Paciente no encontrado"));

        LaboratorioRequestDTO request = new LaboratorioRequestDTO();
        request.setPacienteId(pacienteId);
        request.setExamenes(examenes);

        return laboratorioService.requestExams(request);
    }
}