package com.clinica.service;

import com.clinica.model.dto.LaboratorioRequestDTO;
import com.clinica.model.dto.ResultadoLaboratorioDTO;
import com.clinica.model.entity.ResultadoLaboratorio;
import com.clinica.repository.InMemoryRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class LaboratorioService {

    private final InMemoryRepository repository;

    public LaboratorioService(InMemoryRepository repository) {
        this.repository = repository;
    }

    public List<ResultadoLaboratorioDTO> requestExams(LaboratorioRequestDTO request) {
        List<ResultadoLaboratorioDTO> resultados = new ArrayList<>();

        for (String tipoExamen : request.getExamenes()) {
            ResultadoLaboratorio resultado = new ResultadoLaboratorio(
                    request.getPacienteId(),
                    tipoExamen,
                    LocalDate.now()
            );

            Map<String, ResultadoLaboratorio.ValorResultado> resultadosDetallados = generateSimulatedResults(tipoExamen);
            resultado.setResultados(resultadosDetallados);
            resultado.setEstado("COMPLETADO");

            repository.saveResultadoLaboratorio(resultado);
            resultados.add(mapToDTO(resultado));
        }

        return resultados;
    }

    public List<ResultadoLaboratorioDTO> getResultadosByPacienteId(String pacienteId) {
        return repository.findResultadosLaboratorioByPacienteId(pacienteId).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private Map<String, ResultadoLaboratorio.ValorResultado> generateSimulatedResults(String tipoExamen) {
        Map<String, ResultadoLaboratorio.ValorResultado> results = new HashMap<>();
        Random random = new Random();

        switch (tipoExamen.toLowerCase()) {
            case "hemograma":
                results.put("hemoglobina", new ResultadoLaboratorio.ValorResultado("Hemoglobina", 14.0 + random.nextDouble() * 2, "g/dL", 12.0, 17.5));
                results.put("leucocitos", new ResultadoLaboratorio.ValorResultado("Leucocitos", 7000.0 + random.nextDouble() * 3000, "/µL", 4000.0, 11000.0));
                results.put("plaquetas", new ResultadoLaboratorio.ValorResultado("Plaquetas", 250000.0 + random.nextDouble() * 100000, "/µL", 150000.0, 400000.0));
                break;
            case "glicemia":
                results.put("glucosa_ayunas", new ResultadoLaboratorio.ValorResultado("Glucosa en ayunas", 90.0 + random.nextDouble() * 30, "mg/dL", 70.0, 100.0));
                break;
            case "perfil_lipidico":
                results.put("colesterol_total", new ResultadoLaboratorio.ValorResultado("Colesterol total", 180.0 + random.nextDouble() * 40, "mg/dL", 0.0, 200.0));
                results.put("ldl", new ResultadoLaboratorio.ValorResultado("LDL", 100.0 + random.nextDouble() * 30, "mg/dL", 0.0, 130.0));
                results.put("hdl", new ResultadoLaboratorio.ValorResultado("HDL", 50.0 + random.nextDouble() * 20, "mg/dL", 40.0, 60.0));
                results.put("trigliceridos", new ResultadoLaboratorio.ValorResultado("Triglicéridos", 120.0 + random.nextDouble() * 80, "mg/dL", 0.0, 150.0));
                break;
        }

        return results;
    }

    private ResultadoLaboratorioDTO mapToDTO(ResultadoLaboratorio resultado) {
        ResultadoLaboratorioDTO dto = new ResultadoLaboratorioDTO();
        dto.setId(resultado.getId());
        dto.setPacienteId(resultado.getPacienteId());
        dto.setTipoExamen(resultado.getTipoExamen());
        dto.setFechaSolicitud(resultado.getFechaSolicitud());
        dto.setEstado(resultado.getEstado());

        Map<String, ResultadoLaboratorioDTO.ValorResultadoDTO> resultadosDTO = new HashMap<>();
        for (Map.Entry<String, ResultadoLaboratorio.ValorResultado> entry : resultado.getResultados().entrySet()) {
            ResultadoLaboratorio.ValorResultado original = entry.getValue();
            ResultadoLaboratorioDTO.ValorResultadoDTO converted = new ResultadoLaboratorioDTO.ValorResultadoDTO();
            converted.setNombre(original.getNombre());
            converted.setValor(original.getValor());
            converted.setUnidad(original.getUnidad());
            converted.setValorMinimo(original.getValorMinimo());
            converted.setValorMaximo(original.getValorMaximo());
            converted.setDentroRango(original.getDentroRango());
            resultadosDTO.put(entry.getKey(), converted);
        }
        dto.setResultados(resultadosDTO);

        return dto;
    }
}