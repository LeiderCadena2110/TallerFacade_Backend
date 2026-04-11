package com.clinica.controller;

import com.clinica.model.dto.*;
import com.clinica.service.AgendaService;
import com.clinica.service.ClinicaFacade;
import com.clinica.service.LaboratorioService;
import com.clinica.service.PacienteService;
import com.clinica.service.PrescripcionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/api/clinica")
@CrossOrigin(origins = "*")
public class ClinicaController {

    private final PacienteService pacienteService;
    private final AgendaService agendaService;
    private final ClinicaFacade clinicaFacade;
    private final PrescripcionService prescripcionService;
    private final LaboratorioService laboratorioService;

    public ClinicaController(PacienteService pacienteService,
                            AgendaService agendaService,
                            ClinicaFacade clinicaFacade,
                            PrescripcionService prescripcionService,
                            LaboratorioService laboratorioService) {
        this.pacienteService = pacienteService;
        this.agendaService = agendaService;
        this.clinicaFacade = clinicaFacade;
        this.prescripcionService = prescripcionService;
        this.laboratorioService = laboratorioService;
    }

    @PostMapping("/paciente")
    public ResponseEntity<PacienteDTO> registerPatient(@RequestBody PacienteDTO pacienteDTO) {
        PacienteDTO registered = pacienteService.registerPatient(pacienteDTO);
        return ResponseEntity.ok(registered);
    }

    @PostMapping("/cita")
    public ResponseEntity<CitaDTO> scheduleAppointment(@RequestBody CitaRequestDTO request) {
        CitaDTO cita = clinicaFacade.agendarCita(
                request.getPacienteId(),
                request.getEspecialidad(),
                request.getMedicoId(),
                request.getFechaHora()
        );
        return ResponseEntity.ok(cita);
    }

    @GetMapping("/historia/{pacienteId}")
    public ResponseEntity<HistoriaCompletaDTO> getHistoriaCompleta(@PathVariable String pacienteId) {
        HistoriaCompletaDTO historia = clinicaFacade.verHistoriaCompleta(pacienteId);
        return ResponseEntity.ok(historia);
    }

    @PostMapping("/prescripcion")
    public ResponseEntity<PrescripcionDTO> generatePrescription(@RequestBody PrescripcionRequestDTO request) {
        PrescripcionDTO prescripcion = clinicaFacade.generarPrescripcion(
                request.getPacienteId(),
                request.getMedicamentos(),
                request.getMedico()
        );
        return ResponseEntity.ok(prescripcion);
    }

    @PostMapping("/laboratorio")
    public ResponseEntity<List<ResultadoLaboratorioDTO>> requestLabExams(@RequestBody LaboratorioRequestDTO request) {
        List<ResultadoLaboratorioDTO> resultados = clinicaFacade.solicitarExamenes(
                request.getPacienteId(),
                request.getExamenes()
        );
        return ResponseEntity.ok(resultados);
    }

    @GetMapping("/medicos")
    public ResponseEntity<List<MedicoDTO>> getMedicos(@RequestParam(required = false) String especialidad) {
        List<MedicoDTO> medicos = agendaService.getMedicosByEspecialidad(especialidad);
        return ResponseEntity.ok(medicos);
    }

    @GetMapping("/disponibilidad")
    public ResponseEntity<List<LocalTime>> getDisponibilidad(
            @RequestParam String medicoId,
            @RequestParam String fecha) {
        List<LocalTime> slots = agendaService.getAvailableSlots(medicoId, fecha);
        return ResponseEntity.ok(slots);
    }

    @PostMapping("/login")
    public ResponseEntity<PacienteDTO> login(@RequestBody LoginRequest request) {
        return pacienteService.getPatientProfile(request.getPacienteId())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/test")
    public String test() {
        return "Backend funcionando!";
    }

    public static class LoginRequest {
        private String pacienteId;

        public String getPacienteId() { return pacienteId; }
        public void setPacienteId(String pacienteId) { this.pacienteId = pacienteId; }
    }
}