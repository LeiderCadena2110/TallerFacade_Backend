# Medical Clinic System - Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Build a full-stack medical clinic management system with Spring Boot backend (5 subsystems + Facade) and React frontend, deployed to Railway and Vercel

**Architecture:** Spring Boot REST API with 5 services + ClinicaFacade as orchestrator. React SPA with Spanish UI. In-memory data storage.

**Tech Stack:** Spring Boot 3.x, Java 17, React 18, Vite, React Router v6, CSS Modules

---

## File Structure

```
C:\Users\Cisna\Documents\Ing Software\Patrones de Software\Taller Facade\
├── backend/
│   ├── pom.xml
│   └── src/main/java/com/clinica/
│       ├── ClinicaApplication.java
│       ├── controller/
│       │   └── ClinicaController.java
│       ├── service/
│       │   ├── PacienteService.java
│       │   ├── AgendaService.java
│       │   ├── HistoriaClinicaService.java
│       │   ├── PrescripcionService.java
│       │   ├── LaboratorioService.java
│       │   └── ClinicaFacade.java
│       ├── model/
│       │   ├── entity/
│       │   │   ├── Paciente.java
│       │   │   ├── Medico.java
│       │   │   ├── Cita.java
│       │   │   ├── ConsultaMedica.java
│       │   │   ├── Prescripcion.java
│       │   │   ├── Medicamento.java
│       │   │   └── ResultadoLaboratorio.java
│       │   └── dto/
│       │       ├── PacienteDTO.java
│       │       ├── CitaRequestDTO.java
│       │       ├── CitaDTO.java
│       │       ├── MedicoDTO.java
│       │       ├── HistoriaCompletaDTO.java
│       │       ├── PrescripcionRequestDTO.java
│       │       ├── PrescripcionDTO.java
│       │       ├── MedicamentoDTO.java
│       │       ├── LaboratorioRequestDTO.java
│       │       └── ResultadoLaboratorioDTO.java
│       └── repository/
│           └── InMemoryRepository.java
├── frontend/
│   ├── package.json
│   ├── vite.config.js
│   ├── index.html
│   ├── src/
│   │   ├── main.jsx
│   │   ├── App.jsx
│   │   ├── index.css
│   │   ├── api/
│   │   │   └── clinicaApi.js
│   │   ├── components/
│   │   │   ├── Navbar.jsx
│   │   │   ├── AppointmentCard.jsx
│   │   │   ├── LabResultCard.jsx
│   │   │   ├── TabContainer.jsx
│   │   │   ├── RangeIndicator.jsx
│   │   │   ├── LoadingSpinner.jsx
│   │   │   └── AlertMessage.jsx
│   │   └── pages/
│   │       ├── LoginPage.jsx
│   │       ├── RegisterPage.jsx
│   │       ├── DashboardPage.jsx
│   │       ├── NewAppointmentPage.jsx
│   │       └── MedicalHistoryPage.jsx
│   └── public/
└── SPEC.md
```

---

## Backend Implementation

### Task 1: Spring Boot Project Setup

**Files:**
- Create: `backend/pom.xml`
- Create: `backend/src/main/java/com/clinica/ClinicaApplication.java`
- Create: `backend/src/main/resources/application.properties`

- [ ] **Step 1: Create pom.xml with Spring Boot dependencies**

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>3.2.0</version>
        <relativePath/>
    </parent>
    
    <groupId>com.clinica</groupId>
    <artifactId>clinica-backend</artifactId>
    <version>1.0.0</version>
    <name>clinica-backend</name>
    <description>Medical Clinic Management System Backend</description>
    
    <properties>
        <java.version>17</java.version>
    </properties>
    
    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-validation</artifactId>
        </dependency>
    </dependencies>
    
    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>
</project>
```

- [ ] **Step 2: Create main application class**

```java
package com.clinica;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ClinicaApplication {
    public static void main(String[] args) {
        SpringApplication.run(ClinicaApplication.class, args);
    }
}
```

- [ ] **Step 3: Create application.properties**

```properties
server.port=8080
spring.application.name=clinica-backend
```

- [ ] **Step 4: Test build**

Run: `cd backend && mvn clean compile`
Expected: BUILD SUCCESS

---

### Task 2: Entity Models

**Files:**
- Create: `backend/src/main/java/com/clinica/model/entity/Paciente.java`
- Create: `backend/src/main/java/com/clinica/model/entity/Medico.java`
- Create: `backend/src/main/java/com/clinica/model/entity/Cita.java`
- Create: `backend/src/main/java/com/clinica/model/entity/ConsultaMedica.java`
- Create: `backend/src/main/java/com/clinica/model/entity/Prescripcion.java`
- Create: `backend/src/main/java/com/clinica/model/entity/Medicamento.java`
- Create: `backend/src/main/java/com/clinica/model/entity/ResultadoLaboratorio.java`

- [ ] **Step 1: Create Paciente entity**

```java
package com.clinica.model.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Paciente {
    private String id;
    private String nombreCompleto;
    private String numeroDocumento;
    private LocalDate fechaNacimiento;
    private String correoElectronico;
    private String telefono;
    private List<String> listaAlergias;

    public Paciente() {
        this.id = UUID.randomUUID().toString();
        this.listaAlergias = new ArrayList<>();
    }

    public Paciente(String nombreCompleto, String numeroDocumento, LocalDate fechaNacimiento, 
                    String correoElectronico, String telefono) {
        this();
        this.nombreCompleto = nombreCompleto;
        this.numeroDocumento = numeroDocumento;
        this.fechaNacimiento = fechaNacimiento;
        this.correoElectronico = correoElectronico;
        this.telefono = telefono;
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }
    public String getNumeroDocumento() { return numeroDocumento; }
    public void setNumeroDocumento(String numeroDocumento) { this.numeroDocumento = numeroDocumento; }
    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(LocalDate fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }
    public String getCorreoElectronico() { return correoElectronico; }
    public void setCorreoElectronico(String correoElectronico) { this.correoElectronico = correoElectronico; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public List<String> getListaAlergias() { return listaAlergias; }
    public void setListaAlergias(List<String> listaAlergias) { this.listaAlergias = listaAlergias; }
}
```

- [ ] **Step 2: Create Medico entity**

```java
package com.clinica.model.entity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Medico {
    private String id;
    private String nombreCompleto;
    private String especialidad;
    private Map<LocalDate, List<LocalTime>> disponibilidad;

    public Medico() {
        this.disponibilidad = new HashMap<>();
    }

    public Medico(String nombreCompleto, String especialidad) {
        this();
        this.id = java.util.UUID.randomUUID().toString();
        this.nombreCompleto = nombreCompleto;
        this.especialidad = especialidad;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }
    public String getEspecialidad() { return especialidad; }
    public void setEspecialidad(String especialidad) { this.especialidad = especialidad; }
    public Map<LocalDate, List<LocalTime>> getDisponibilidad() { return disponibilidad; }
    public void setDisponibilidad(Map<LocalDate, List<LocalTime>> disponibilidad) { this.disponibilidad = disponibilidad; }
}
```

- [ ] **Step 3: Create Cita entity**

```java
package com.clinica.model.entity;

import java.time.LocalDateTime;

public class Cita {
    private String id;
    private String pacienteId;
    private String medicoId;
    private String nombreMedico;
    private String especialidad;
    private LocalDateTime fechaHora;
    private String estado;

    public Cita() {}

    public Cita(String pacienteId, String medicoId, String nombreMedico, String especialidad, LocalDateTime fechaHora) {
        this.id = java.util.UUID.randomUUID().toString();
        this.pacienteId = pacienteId;
        this.medicoId = medicoId;
        this.nombreMedico = nombreMedico;
        this.especialidad = especialidad;
        this.fechaHora = fechaHora;
        this.estado = "AGENDADA";
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getPacienteId() { return pacienteId; }
    public void setPacienteId(String pacienteId) { this.pacienteId = pacienteId; }
    public String getMedicoId() { return medicoId; }
    public void setMedicoId(String medicoId) { this.medicoId = medicoId; }
    public String getNombreMedico() { return nombreMedico; }
    public void setNombreMedico(String nombreMedico) { this.nombreMedico = nombreMedico; }
    public String getEspecialidad() { return especialidad; }
    public void setEspecialidad(String especialidad) { this.especialidad = especialidad; }
    public LocalDateTime getFechaHora() { return fechaHora; }
    public void setFechaHora(LocalDateTime fechaHora) { this.fechaHora = fechaHora; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}
```

- [ ] **Step 4: Create ConsultaMedica entity**

```java
package com.clinica.model.entity;

import java.time.LocalDate;

public class ConsultaMedica {
    private String id;
    private String pacienteId;
    private LocalDate fecha;
    private String motivo;
    private String diagnostico;
    private String notas;

    public ConsultaMedica() {}

    public ConsultaMedica(String pacienteId, LocalDate fecha, String motivo, String diagnostico, String notas) {
        this.id = java.util.UUID.randomUUID().toString();
        this.pacienteId = pacienteId;
        this.fecha = fecha;
        this.motivo = motivo;
        this.diagnostico = diagnostico;
        this.notas = notas;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getPacienteId() { return pacienteId; }
    public void setPacienteId(String pacienteId) { this.pacienteId = pacienteId; }
    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
    public String getMotivo() { return motivo; }
    public void setMotivo(String motivo) { this.motivo = motivo; }
    public String getDiagnostico() { return diagnostico; }
    public void setDiagnostico(String diagnostico) { this.diagnostico = diagnostico; }
    public String getNotas() { return notas; }
    public void setNotas(String notas) { this.notas = notas; }
}
```

- [ ] **Step 5: Create Medicamento entity**

```java
package com.clinica.model.entity;

public class Medicamento {
    private String nombre;
    private String dosis;
    private String frecuencia;
    private String duracion;

    public Medicamento() {}

    public Medicamento(String nombre, String dosis, String frecuencia, String duracion) {
        this.nombre = nombre;
        this.dosis = dosis;
        this.frecuencia = frecuencia;
        this.duracion = duracion;
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getDosis() { return dosis; }
    public void setDosis(String dosis) { this.dosis = dosis; }
    public String getFrecuencia() { return frecuencia; }
    public void setFrecuencia(String frecuencia) { this.frecuencia = frecuencia; }
    public String getDuracion() { return duracion; }
    public void setDuracion(String duracion) { this.duracion = duracion; }
}
```

- [ ] **Step 6: Create Prescripcion entity**

```java
package com.clinica.model.entity;

import java.time.LocalDate;
import java.util.List;

public class Prescripcion {
    private String id;
    private String pacienteId;
    private LocalDate fechaEmision;
    private List<Medicamento> medicamentos;
    private String medico;

    public Prescripcion() {}

    public Prescripcion(String pacienteId, LocalDate fechaEmision, List<Medicamento> medicamentos, String medico) {
        this.id = java.util.UUID.randomUUID().toString();
        this.pacienteId = pacienteId;
        this.fechaEmision = fechaEmision;
        this.medicamentos = medicamentos;
        this.medico = medico;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getPacienteId() { return pacienteId; }
    public void setPacienteId(String pacienteId) { this.pacienteId = pacienteId; }
    public LocalDate getFechaEmision() { return fechaEmision; }
    public void setFechaEmision(LocalDate fechaEmision) { this.fechaEmision = fechaEmision; }
    public List<Medicamento> getMedicamentos() { return medicamentos; }
    public void setMedicamentos(List<Medicamento> medicamentos) { this.medicamentos = medicamentos; }
    public String getMedico() { return medico; }
    public void setMedico(String medico) { this.medico = medico; }
}
```

- [ ] **Step 7: Create ResultadoLaboratorio entity**

```java
package com.clinica.model.entity;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

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
        this.id = java.util.UUID.randomUUID().toString();
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
```

- [ ] **Step 8: Commit changes**

```bash
git add backend/pom.xml backend/src/main/java/com/clinica/model/entity/ backend/src/main/resources/
git commit -m "feat: add entity models for medical clinic"
```

---

### Task 3: DTOs (Data Transfer Objects)

**Files:**
- Create: `backend/src/main/java/com/clinica/model/dto/PacienteDTO.java`
- Create: `backend/src/main/java/com/clinica/model/dto/CitaRequestDTO.java`
- Create: `backend/src/main/java/com/clinica/model/dto/CitaDTO.java`
- Create: `backend/src/main/java/com/clinica/model/dto/MedicoDTO.java`
- Create: `backend/src/main/java/com/clinica/model/dto/HistoriaCompletaDTO.java`
- Create: `backend/src/main/java/com/clinica/model/dto/PrescripcionRequestDTO.java`
- Create: `backend/src/main/java/com/clinica/model/dto/PrescripcionDTO.java`
- Create: `backend/src/main/java/com/clinica/model/dto/MedicamentoDTO.java`
- Create: `backend/src/main/java/com/clinica/model/dto/LaboratorioRequestDTO.java`
- Create: `backend/src/main/java/com/clinica/model/dto/ResultadoLaboratorioDTO.java`

- [ ] **Step 1: Create PacienteDTO**

```java
package com.clinica.model.dto;

import java.time.LocalDate;
import java.util.List;

public class PacienteDTO {
    private String id;
    private String nombreCompleto;
    private String numeroDocumento;
    private LocalDate fechaNacimiento;
    private String correoElectronico;
    private String telefono;
    private List<String> listaAlergias;

    public PacienteDTO() {}

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }
    public String getNumeroDocumento() { return numeroDocumento; }
    public void setNumeroDocumento(String numeroDocumento) { this.numeroDocumento = numeroDocumento; }
    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(LocalDate fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }
    public String getCorreoElectronico() { return correoElectronico; }
    public void setCorreoElectronico(String correoElectronico) { this.correoElectronico = correoElectronico; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public List<String> getListaAlergias() { return listaAlergias; }
    public void setListaAlergias(List<String> listaAlergias) { this.listaAlergias = listaAlergias; }
}
```

- [ ] **Step 2: Create CitaRequestDTO**

```java
package com.clinica.model.dto;

import java.time.LocalDateTime;

public class CitaRequestDTO {
    private String pacienteId;
    private String medicoId;
    private String especialidad;
    private LocalDateTime fechaHora;

    public CitaRequestDTO() {}

    public String getPacienteId() { return pacienteId; }
    public void setPacienteId(String pacienteId) { this.pacienteId = pacienteId; }
    public String getMedicoId() { return medicoId; }
    public void setMedicoId(String medicoId) { this.medicoId = medicoId; }
    public String getEspecialidad() { return especialidad; }
    public void setEspecialidad(String especialidad) { this.especialidad = especialidad; }
    public LocalDateTime getFechaHora() { return fechaHora; }
    public void setFechaHora(LocalDateTime fechaHora) { this.fechaHora = fechaHora; }
}
```

- [ ] **Step 3: Create CitaDTO**

```java
package com.clinica.model.dto;

import java.time.LocalDateTime;

public class CitaDTO {
    private String id;
    private String pacienteId;
    private String medicoId;
    private String nombreMedico;
    private String especialidad;
    private LocalDateTime fechaHora;
    private String estado;
    private String recordatorio;

    public CitaDTO() {}

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getPacienteId() { return pacienteId; }
    public void setPacienteId(String pacienteId) { this.pacienteId = pacienteId; }
    public String getMedicoId() { return medicoId; }
    public void setMedicoId(String medicoId) { this.medicoId = medicoId; }
    public String getNombreMedico() { return nombreMedico; }
    public void setNombreMedico(String nombreMedico) { this.nombreMedico = nombreMedico; }
    public String getEspecialidad() { return especialidad; }
    public void setEspecialidad(String especialidad) { this.especialidad = especialidad; }
    public LocalDateTime getFechaHora() { return fechaHora; }
    public void setFechaHora(LocalDateTime fechaHora) { this.fechaHora = fechaHora; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public String getRecordatorio() { return recordatorio; }
    public void setRecordatorio(String recordatorio) { this.recordatorio = recordatorio; }
}
```

- [ ] **Step 4: Create MedicoDTO**

```java
package com.clinica.model.dto;

public class MedicoDTO {
    private String id;
    private String nombreCompleto;
    private String especialidad;

    public MedicoDTO() {}

    public MedicoDTO(String id, String nombreCompleto, String especialidad) {
        this.id = id;
        this.nombreCompleto = nombreCompleto;
        this.especialidad = especialidad;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }
    public String getEspecialidad() { return especialidad; }
    public void setEspecialidad(String especialidad) { this.especialidad = especialidad; }
}
```

- [ ] **Step 5: Create HistoriaCompletaDTO**

```java
package com.clinica.model.dto;

import java.util.List;

public class HistoriaCompletaDTO {
    private String pacienteId;
    private List<CitaDTO> citasPasadas;
    private List<PrescripcionDTO> prescripciones;
    private List<ResultadoLaboratorioDTO> resultadosLaboratorio;

    public HistoriaCompletaDTO() {}

    public String getPacienteId() { return pacienteId; }
    public void setPacienteId(String pacienteId) { this.pacienteId = pacienteId; }
    public List<CitaDTO> getCitasPasadas() { return citasPasadas; }
    public void setCitasPasadas(List<CitaDTO> citasPasadas) { this.citasPasadas = citasPasadas; }
    public List<PrescripcionDTO> getPrescripciones() { return prescripciones; }
    public void setPrescripciones(List<PrescripcionDTO> prescripciones) { this.prescripciones = prescripciones; }
    public List<ResultadoLaboratorioDTO> getResultadosLaboratorio() { return resultadosLaboratorio; }
    public void setResultadosLaboratorio(List<ResultadoLaboratorioDTO> resultadosLaboratorio) { this.resultadosLaboratorio = resultadosLaboratorio; }
}
```

- [ ] **Step 6: Create MedicamentoDTO**

```java
package com.clinica.model.dto;

public class MedicamentoDTO {
    private String nombre;
    private String dosis;
    private String frecuencia;
    private String duracion;

    public MedicamentoDTO() {}

    public MedicamentoDTO(String nombre, String dosis, String frecuencia, String duracion) {
        this.nombre = nombre;
        this.dosis = dosis;
        this.frecuencia = frecuencia;
        this.duracion = duracion;
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getDosis() { return dosis; }
    public void setDosis(String dosis) { this.dosis = dosis; }
    public String getFrecuencia() { return frecuencia; }
    public void setFrecuencia(String frecuencia) { this.frecuencia = frecuencia; }
    public String getDuracion() { return duracion; }
    public void setDuracion(String duracion) { this.duracion = duracion; }
}
```

- [ ] **Step 7: Create PrescripcionRequestDTO**

```java
package com.clinica.model.dto;

import java.util.List;

public class PrescripcionRequestDTO {
    private String pacienteId;
    private String medico;
    private List<MedicamentoDTO> medicamentos;

    public PrescripcionRequestDTO() {}

    public String getPacienteId() { return pacienteId; }
    public void setPacienteId(String pacienteId) { this.pacienteId = pacienteId; }
    public String getMedico() { return medico; }
    public void setMedico(String medico) { this.medico = medico; }
    public List<MedicamentoDTO> getMedicamentos() { return medicamentos; }
    public void setMedicamentos(List<MedicamentoDTO> medicamentos) { this.medicamentos = medicamentos; }
}
```

- [ ] **Step 8: Create PrescripcionDTO**

```java
package com.clinica.model.dto;

import java.time.LocalDate;
import java.util.List;

public class PrescripcionDTO {
    private String id;
    private String pacienteId;
    private LocalDate fechaEmision;
    private List<MedicamentoDTO> medicamentos;
    private String medico;

    public PrescripcionDTO() {}

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getPacienteId() { return pacienteId; }
    public void setPacienteId(String pacienteId) { this.pacienteId = pacienteId; }
    public LocalDate getFechaEmision() { return fechaEmision; }
    public void setFechaEmision(LocalDate fechaEmision) { this.fechaEmision = fechaEmision; }
    public List<MedicamentoDTO> getMedicamentos() { return medicamentos; }
    public void setMedicamentos(List<MedicamentoDTO> medicamentos) { this.medicamentos = medicamentos; }
    public String getMedico() { return medico; }
    public void setMedico(String medico) { this.medico = medico; }
}
```

- [ ] **Step 9: Create LaboratorioRequestDTO**

```java
package com.clinica.model.dto;

import java.util.List;

public class LaboratorioRequestDTO {
    private String pacienteId;
    private List<String> examenes;

    public LaboratorioRequestDTO() {}

    public String getPacienteId() { return pacienteId; }
    public void setPacienteId(String pacienteId) { this.pacienteId = pacienteId; }
    public List<String> getExamenes() { return examenes; }
    public void setExamenes(List<String> examenes) { this.examenes = examenes; }
}
```

- [ ] **Step 10: Create ResultadoLaboratorioDTO**

```java
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
```

- [ ] **Step 11: Commit changes**

```bash
git add backend/src/main/java/com/clinica/model/dto/
git commit -m "feat: add DTOs for API communication"
```

---

### Task 4: In-Memory Repository with Predefined Data

**Files:**
- Create: `backend/src/main/java/com/clinica/repository/InMemoryRepository.java`

- [ ] **Step 1: Create InMemoryRepository with predefined doctors and sample data**

```java
package com.clinica.repository;

import com.clinica.model.entity.*;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
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
        // Create 5 predefined doctors
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

        // Initialize availability for next 7 days
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

    // Paciente operations
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

    public Collection<Paciente> getAllPacientes() {
        return pacientes.values();
    }

    // Medico operations
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

    // Cita operations
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

    // Consulta operations
    public ConsultaMedica saveConsulta(ConsultaMedica consulta) {
        consultas.put(consulta.getId(), consulta);
        return consulta;
    }

    public List<ConsultaMedica> findConsultasByPacienteId(String pacienteId) {
        return consultas.values().stream()
                .filter(c -> c.getPacienteId().equals(pacienteId))
                .collect(Collectors.toList());
    }

    // Prescripcion operations
    public Prescripcion savePrescripcion(Prescripcion prescripcion) {
        prescripciones.put(prescripcion.getId(), prescripcion);
        return prescripcion;
    }

    public List<Prescripcion> findPrescripcionesByPacienteId(String pacienteId) {
        return prescripciones.values().stream()
                .filter(p -> p.getPacienteId().equals(pacienteId))
                .collect(Collectors.toList());
    }

    // ResultadoLaboratorio operations
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
```

- [ ] **Step 2: Commit changes**

```bash
git add backend/src/main/java/com/clinica/repository/InMemoryRepository.java
git commit -m "feat: add in-memory repository with predefined data"
```

---

### Task 5: PacienteService

**Files:**
- Create: `backend/src/main/java/com/clinica/service/PacienteService.java`

- [ ] **Step 1: Create PacienteService**

```java
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
```

- [ ] **Step 2: Commit changes**

```bash
git add backend/src/main/java/com/clinica/service/PacienteService.java
git commit -m "feat: add PacienteService with registration and validation"
```

---

### Task 6: AgendaService

**Files:**
- Create: `backend/src/main/java/com/clinica/service/AgendaService.java`

- [ ] **Step 1: Create AgendaService**

```java
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
        
        // Remove the slot from availability
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
        
        // Add reminder
        dto.setRecordatorio("Recuerde asistir a su cita el " + cita.getFechaHora().toLocalDate() + " a las " + cita.getFechaHora().toLocalTime());
        
        return dto;
    }
}
```

- [ ] **Step 2: Commit changes**

```bash
git add backend/src/main/java/com/clinica/service/AgendaService.java
git commit -m "feat: add AgendaService for appointment scheduling"
```

---

### Task 7: HistoriaClinicaService

**Files:**
- Create: `backend/src/main/java/com/clinica/service/HistoriaClinicaService.java`

- [ ] **Step 1: Create HistoriaClinicaService**

```java
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
        
        // Extract unique diagnoses and allergies
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
```

- [ ] **Step 2: Commit changes**

```bash
git add backend/src/main/java/com/clinica/service/HistoriaClinicaService.java
git commit -m "feat: add HistoriaClinicaService for medical history"
```

---

### Task 8: PrescripcionService

**Files:**
- Create: `backend/src/main/java/com/clinica/service/PrescripcionService.java`

- [ ] **Step 1: Create PrescripcionService**

```java
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
        // Validate allergies
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
```

- [ ] **Step 2: Commit changes**

```bash
git add backend/src/main/java/com/clinica/service/PrescripcionService.java
git commit -m "feat: add PrescripcionService with allergy validation"
```

---

### Task 9: LaboratorioService

**Files:**
- Create: `backend/src/main/java/com/clinica/service/LaboratorioService.java`

- [ ] **Step 1: Create LaboratorioService**

```java
package com.clinica.service;

import com.clinica.model.dto.LaboratorioRequestDTO;
import com.clinica.model.dto.ResultadoLaboratorioDTO;
import com.clinica.model.entity.ResultadoLaboratorio;
import com.clinica.repository.InMemoryRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

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

            // Add simulated results based on exam type
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
                results.put("leucocitos", new ResultadoLaboratorio.ValorResultado("Leucocitos", 7000.0 + random.nextDouble() * 3000, "/µL", 4000, 11000));
                results.put("plaquetas", new ResultadoLaboratorio.ValorResultado("Plaquetas", 250000.0 + random.nextDouble() * 100000, "/µL", 150000, 400000));
                break;
            case "glicemia":
                results.put("glucosa_ayunas", new ResultadoLaboratorio.ValorResultado("Glucosa en ayunas", 90.0 + random.nextDouble() * 30, "mg/dL", 70, 100));
                break;
            case "perfil_lipidico":
                results.put("colesterol_total", new ResultadoLaboratorio.ValorResultado("Colesterol total", 180.0 + random.nextDouble() * 40, "mg/dL", 0, 200));
                results.put("ldl", new ResultadoLaboratorio.ValorResultado("LDL", 100.0 + random.nextDouble() * 30, "mg/dL", 0, 130));
                results.put("hdl", new ResultadoLaboratorio.ValorResultado("HDL", 50.0 + random.nextDouble() * 20, "mg/dL", 40, 60));
                results.put("trigliceridos", new ResultadoLaboratorio.ValorResultado("Triglicéridos", 120.0 + random.nextDouble() * 80, "mg/dL", 0, 150));
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
```

- [ ] **Step 2: Commit changes**

```bash
git add backend/src/main/java/com/clinica/service/LaboratorioService.java
git commit -m "feat: add LaboratorioService with simulated results"
```

---

### Task 10: ClinicaFacade (Required)

**Files:**
- Create: `backend/src/main/java/com/clinica/service/ClinicaFacade.java`

- [ ] **Step 1: Create ClinicaFacade that orchestrates 2+ subsystems per method**

```java
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
        // Invokes: PacienteService + AgendaService
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
        // Invokes: HistoriaClinicaService + PrescripcionService + LaboratorioService
        HistoriaCompletaDTO historiaCompleta = new HistoriaCompletaDTO();
        historiaCompleta.setPacienteId(pacienteId);

        // Get past appointments
        List<CitaDTO> citasPasadas = agendaService.getCitasFuturasByPacienteId(pacienteId);
        historiaCompleta.setCitasPasadas(citasPasadas);

        // Get prescriptions
        List<PrescripcionDTO> prescripciones = prescripcionService.getPrescripcionesByPacienteId(pacienteId);
        historiaCompleta.setPrescripciones(prescripciones);

        // Get lab results
        List<ResultadoLaboratorioDTO> resultados = laboratorioService.getResultadosByPacienteId(pacienteId);
        historiaCompleta.setResultadosLaboratorio(resultados);

        return historiaCompleta;
    }

    public PrescripcionDTO generarPrescripcion(String pacienteId, List<MedicamentoDTO> medicamentos, String medico) {
        // Invokes: PacienteService (allergy check) + PrescripcionService
        PrescripcionRequestDTO request = new PrescripcionRequestDTO();
        request.setPacienteId(pacienteId);
        request.setMedicamentos(medicamentos);
        request.setMedico(medico);

        return prescripcionService.generatePrescription(request);
    }

    public List<ResultadoLaboratorioDTO> solicitarExamenes(String pacienteId, List<String> examenes) {
        // Invokes: PacienteService + LaboratorioService
        pacienteService.getPatientProfile(pacienteId)
                .orElseThrow(() -> new IllegalArgumentException("Paciente no encontrado"));

        LaboratorioRequestDTO request = new LaboratorioRequestDTO();
        request.setPacienteId(pacienteId);
        request.setExamenes(examenes);

        return laboratorioService.requestExams(request);
    }
}
```

- [ ] **Step 2: Commit changes**

```bash
git add backend/src/main/java/com/clinica/service/ClinicaFacade.java
git commit -m "feat: add ClinicaFacade orchestrating all subsystems"
```

---

### Task 11: ClinicaController (REST Endpoints)

**Files:**
- Create: `backend/src/main/java/com/clinica/controller/ClinicaController.java`

- [ ] **Step 1: Create ClinicaController**

```java
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

    public static class LoginRequest {
        private String pacienteId;

        public String getPacienteId() { return pacienteId; }
        public void setPacienteId(String pacienteId) { this.pacienteId = pacienteId; }
    }
}
```

- [ ] **Step 2: Commit changes**

```bash
git add backend/src/main/java/com/clinica/controller/ClinicaController.java
git commit -m "feat: add REST controller with all endpoints"
```

- [ ] **Step 3: Test backend build**

Run: `cd backend && mvn clean package -DskipTests`
Expected: BUILD SUCCESS

---

## Frontend Implementation

### Task 12: React Project Setup

**Files:**
- Create: `frontend/package.json`
- Create: `frontend/vite.config.js`
- Create: `frontend/index.html`
- Create: `frontend/src/main.jsx`
- Create: `frontend/src/App.jsx`
- Create: `frontend/src/index.css`

- [ ] **Step 1: Create package.json**

```json
{
  "name": "clinica-frontend",
  "private": true,
  "version": "1.0.0",
  "type": "module",
  "scripts": {
    "dev": "vite",
    "build": "vite build",
    "preview": "vite preview"
  },
  "dependencies": {
    "react": "^18.2.0",
    "react-dom": "^18.2.0",
    "react-router-dom": "^6.21.0"
  },
  "devDependencies": {
    "@types/react": "^18.2.43",
    "@types/react-dom": "^18.2.17",
    "@vitejs/plugin-react": "^4.2.1",
    "vite": "^5.0.8"
  }
}
```

- [ ] **Step 2: Create vite.config.js**

```javascript
import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

export default defineConfig({
  plugins: [react()],
  server: {
    port: 5173
  }
})
```

- [ ] **Step 3: Create index.html**

```html
<!DOCTYPE html>
<html lang="es">
  <head>
    <meta charset="UTF-8" />
    <link rel="icon" type="image/svg+xml" href="/vite.svg" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Sistema de Gestión Médica</title>
  </head>
  <body>
    <div id="root"></div>
    <script type="module" src="/src/main.jsx"></script>
  </body>
</html>
```

- [ ] **Step 4: Create main.jsx**

```jsx
import React from 'react'
import ReactDOM from 'react-dom/client'
import App from './App.jsx'
import './index.css'

ReactDOM.createRoot(document.getElementById('root')).render(
  <React.StrictMode>
    <App />
  </React.StrictMode>,
)
```

- [ ] **Step 5: Create App.jsx with routing**

```jsx
import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom'
import LoginPage from './pages/LoginPage'
import RegisterPage from './pages/RegisterPage'
import DashboardPage from './pages/DashboardPage'
import NewAppointmentPage from './pages/NewAppointmentPage'
import MedicalHistoryPage from './pages/MedicalHistoryPage'

function App() {
  const pacienteId = localStorage.getItem('pacienteId')

  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={pacienteId ? <Navigate to="/dashboard" /> : <LoginPage />} />
        <Route path="/register" element={pacienteId ? <Navigate to="/dashboard" /> : <RegisterPage />} />
        <Route path="/dashboard" element={pacienteId ? <DashboardPage /> : <Navigate to="/" />} />
        <Route path="/citas/nueva" element={pacienteId ? <NewAppointmentPage /> : <Navigate to="/" />} />
        <Route path="/historia" element={pacienteId ? <MedicalHistoryPage /> : <Navigate to="/" />} />
      </Routes>
    </BrowserRouter>
  )
}

export default App
```

- [ ] **Step 6: Create index.css**

```css
:root {
  --primary-color: #2563eb;
  --secondary-color: #1e40af;
  --success-color: #16a34a;
  --danger-color: #dc2626;
  --warning-color: #f59e0b;
  --background-color: #f8fafc;
  --text-color: #1e293b;
  --border-color: #e2e8f0;
}

* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

body {
  font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', 'Roboto', sans-serif;
  background-color: var(--background-color);
  color: var(--text-color);
  line-height: 1.6;
}

.container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 20px;
}

.card {
  background: white;
  border-radius: 8px;
  padding: 20px;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
  margin-bottom: 20px;
}

.btn {
  padding: 10px 20px;
  border: none;
  border-radius: 6px;
  cursor: pointer;
  font-weight: 500;
  transition: all 0.2s;
}

.btn-primary {
  background-color: var(--primary-color);
  color: white;
}

.btn-primary:hover {
  background-color: var(--secondary-color);
}

.form-group {
  margin-bottom: 15px;
}

.form-group label {
  display: block;
  margin-bottom: 5px;
  font-weight: 500;
}

.form-group input,
.form-group select {
  width: 100%;
  padding: 10px;
  border: 1px solid var(--border-color);
  border-radius: 6px;
  font-size: 14px;
}

.error {
  color: var(--danger-color);
  font-size: 14px;
  margin-top: 5px;
}

.success {
  color: var(--success-color);
  font-size: 14px;
  margin-top: 5px;
}
```

- [ ] **Step 7: Commit changes**

```bash
git add frontend/package.json frontend/vite.config.js frontend/index.html frontend/src/
git commit -m "feat: add React project setup"
```

---

### Task 13: API Service

**Files:**
- Create: `frontend/src/api/clinicaApi.js`

- [ ] **Step 1: Create clinicaApi.js**

```javascript
const API_BASE_URL = import.meta.env.VITE_API_URL || 'http://localhost:8080/api/clinica';

export const clinicaApi = {
  async registerPatient(pacienteData) {
    const response = await fetch(`${API_BASE_URL}/paciente`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(pacienteData)
    });
    if (!response.ok) {
      const error = await response.json();
      throw new Error(error.message || 'Error al registrar paciente');
    }
    return response.json();
  },

  async login(pacienteId) {
    const response = await fetch(`${API_BASE_URL}/login`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ pacienteId })
    });
    if (!response.ok) throw new Error('Paciente no encontrado');
    return response.json();
  },

  async scheduleAppointment(appointmentData) {
    const response = await fetch(`${API_BASE_URL}/cita`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(appointmentData)
    });
    if (!response.ok) {
      const error = await response.json();
      throw new Error(error.message || 'Error al agendar cita');
    }
    return response.json();
  },

  async getHistoriaCompleta(pacienteId) {
    const response = await fetch(`${API_BASE_URL}/historia/${pacienteId}`);
    if (!response.ok) throw new Error('Error al obtener historia clínica');
    return response.json();
  },

  async generatePrescription(prescriptionData) {
    const response = await fetch(`${API_BASE_URL}/prescripcion`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(prescriptionData)
    });
    if (!response.ok) {
      const error = await response.json();
      throw new Error(error.message || 'Error al generar prescripción');
    }
    return response.json();
  },

  async requestLabExams(labData) {
    const response = await fetch(`${API_BASE_URL}/laboratorio`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(labData)
    });
    if (!response.ok) throw new Error('Error al solicitar exámenes');
    return response.json();
  },

  async getMedicos(especialidad) {
    const url = especialidad 
      ? `${API_BASE_URL}/medicos?especialidad=${encodeURIComponent(especialidad)}`
      : `${API_BASE_URL}/medicos`;
    const response = await fetch(url);
    if (!response.ok) throw new Error('Error al obtener médicos');
    return response.json();
  },

  async getDisponibilidad(medicoId, fecha) {
    const response = await fetch(`${API_BASE_URL}/disponibilidad?medicoId=${medicoId}&fecha=${fecha}`);
    if (!response.ok) throw new Error('Error al obtener disponibilidad');
    return response.json();
  }
};
```

- [ ] **Step 2: Commit changes**

```bash
git add frontend/src/api/clinicaApi.js
git commit -m "feat: add API service for backend communication"
```

---

### Task 14: Reusable Components

**Files:**
- Create: `frontend/src/components/Navbar.jsx`
- Create: `frontend/src/components/AppointmentCard.jsx`
- Create: `frontend/src/components/LabResultCard.jsx`
- Create: `frontend/src/components/TabContainer.jsx`
- Create: `frontend/src/components/RangeIndicator.jsx`
- Create: `frontend/src/components/LoadingSpinner.jsx`
- Create: `frontend/src/components/AlertMessage.jsx`

- [ ] **Step 1: Create Navbar component**

```jsx
import { Link, useNavigate } from 'react-router-dom'

export default function Navbar() {
  const navigate = useNavigate()
  const pacienteNombre = localStorage.getItem('pacienteNombre') || 'Paciente'

  const handleLogout = () => {
    localStorage.clear()
    navigate('/')
  }

  return (
    <nav className="navbar">
      <div className="navbar-container">
        <h1 className="navbar-title">🏥 Sistema de Gestión Médica</h1>
        <div className="navbar-links">
          <Link to="/dashboard" className="nav-link">Dashboard</Link>
          <Link to="/citas/nueva" className="nav-link">Nueva Cita</Link>
          <Link to="/historia" className="nav-link">Historia Clínica</Link>
        </div>
        <div className="navbar-user">
          <span>Bienvenido, {pacienteNombre}</span>
          <button onClick={handleLogout} className="btn-logout">Cerrar Sesión</button>
        </div>
      </div>
    </nav>
  )
}
```

```css
.navbar {
  background-color: #2563eb;
  color: white;
  padding: 15px 0;
}

.navbar-container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 20px;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.navbar-title {
  font-size: 20px;
}

.navbar-links {
  display: flex;
  gap: 20px;
}

.nav-link {
  color: white;
  text-decoration: none;
  padding: 8px 16px;
  border-radius: 6px;
  transition: background 0.2s;
}

.nav-link:hover {
  background-color: rgba(255, 255, 255, 0.1);
}

.navbar-user {
  display: flex;
  align-items: center;
  gap: 15px;
}

.btn-logout {
  background-color: #dc2626;
  color: white;
  border: none;
  padding: 8px 16px;
  border-radius: 6px;
  cursor: pointer;
}
```

- [ ] **Step 2: Create AppointmentCard component**

```jsx
export default function AppointmentCard({ cita }) {
  const formatDateTime = (dateTime) => {
    const date = new Date(dateTime)
    return date.toLocaleDateString('es-ES', { 
      weekday: 'long', 
      year: 'numeric', 
      month: 'long', 
      day: 'numeric',
      hour: '2-digit',
      minute: '2-digit'
    })
  }

  return (
    <div className="appointment-card">
      <div className="appointment-header">
        <h3>{cita.especialidad}</h3>
        <span className={`status status-${cita.estado.toLowerCase()}`}>{cita.estado}</span>
      </div>
      <div className="appointment-body">
        <p><strong>Médico:</strong> {cita.nombreMedico}</p>
        <p><strong>Fecha:</strong> {formatDateTime(cita.fechaHora)}</p>
        {cita.recordatorio && <p className="recordatorio">📌 {cita.recordatorio}</p>}
      </div>
    </div>
  )
}
```

```css
.appointment-card {
  background: white;
  border-radius: 8px;
  padding: 15px;
  border-left: 4px solid #2563eb;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.appointment-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 10px;
}

.status {
  padding: 4px 8px;
  border-radius: 4px;
  font-size: 12px;
  font-weight: 600;
}

.status-agendada {
  background-color: #dbeafe;
  color: #1e40af;
}

.status-cancelada {
  background-color: #fee2e2;
  color: #dc2626;
}

.status-completada {
  background-color: #dcfce7;
  color: #16a34a;
}

.recordatorio {
  margin-top: 10px;
  font-size: 13px;
  color: #64748b;
  background: #f8fafc;
  padding: 8px;
  border-radius: 4px;
}
```

- [ ] **Step 3: Create LabResultCard component**

```jsx
import RangeIndicator from './RangeIndicator'

export default function LabResultCard({ resultado }) {
  const formatDate = (date) => {
    return new Date(date).toLocaleDateString('es-ES')
  }

  return (
    <div className="lab-result-card">
      <div className="lab-result-header">
        <h3>{resultado.tipoExamen}</h3>
        <span className="lab-date">{formatDate(resultado.fechaSolicitud)}</span>
      </div>
      <div className="lab-results">
        {Object.entries(resultado.resultados).map(([key, value]) => (
          <div key={key} className="result-item">
            <span className="result-name">{value.nombre}</span>
            <div className="result-value">
              <span className="result-number">{value.valor}</span>
              <span className="result-unit">{value.unidad}</span>
              <RangeIndicator dentroRango={value.dentroRango} />
            </div>
            <span className="result-range">
              Normal: {value.valorMinimo} - {value.valorMaximo}
            </span>
          </div>
        ))}
      </div>
    </div>
  )
}
```

```css
.lab-result-card {
  background: white;
  border-radius: 8px;
  padding: 15px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  margin-bottom: 15px;
}

.lab-result-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 15px;
  padding-bottom: 10px;
  border-bottom: 1px solid #e2e8f0;
}

.result-item {
  display: grid;
  grid-template-columns: 1fr auto auto;
  gap: 10px;
  align-items: center;
  padding: 10px 0;
  border-bottom: 1px solid #f1f5f9;
}

.result-item:last-child {
  border-bottom: none;
}

.result-name {
  font-weight: 500;
}

.result-value {
  display: flex;
  align-items: center;
  gap: 5px;
}

.result-number {
  font-weight: 600;
  font-size: 16px;
}

.result-unit {
  color: #64748b;
  font-size: 14px;
}

.result-range {
  font-size: 12px;
  color: #94a3b8;
}
```

- [ ] **Step 4: Create RangeIndicator component**

```jsx
export default function RangeIndicator({ dentroRango }) {
  return (
    <span className={`range-indicator ${dentroRango ? 'in-range' : 'out-of-range'}`}>
      {dentroRango ? '✓' : '✗'}
    </span>
  )
}
```

```css
.range-indicator {
  width: 24px;
  height: 24px;
  border-radius: 50%;
  display: inline-flex;
  align-items: center;
  justify-content: center;
  font-size: 12px;
  font-weight: bold;
}

.in-range {
  background-color: #dcfce7;
  color: #16a34a;
}

.out-of-range {
  background-color: #fee2e2;
  color: #dc2626;
}
```

- [ ] **Step 5: Create TabContainer component**

```jsx
export default function TabContainer({ tabs, activeTab, onTabChange }) {
  return (
    <div className="tab-container">
      <div className="tab-buttons">
        {tabs.map((tab) => (
          <button
            key={tab.id}
            className={`tab-button ${activeTab === tab.id ? 'active' : ''}`}
            onClick={() => onTabChange(tab.id)}
          >
            {tab.label}
          </button>
        ))}
      </div>
      <div className="tab-content">
        {tabs.find(t => t.id === activeTab)?.content}
      </div>
    </div>
  )
}
```

```css
.tab-container {
  margin-top: 20px;
}

.tab-buttons {
  display: flex;
  gap: 10px;
  border-bottom: 2px solid #e2e8f0;
  margin-bottom: 20px;
}

.tab-button {
  padding: 12px 24px;
  background: none;
  border: none;
  cursor: pointer;
  font-size: 15px;
  font-weight: 500;
  color: #64748b;
  border-bottom: 2px solid transparent;
  margin-bottom: -2px;
  transition: all 0.2s;
}

.tab-button:hover {
  color: #2563eb;
}

.tab-button.active {
  color: #2563eb;
  border-bottom-color: #2563eb;
}

.tab-content {
  padding: 20px 0;
}
```

- [ ] **Step 6: Create LoadingSpinner component**

```jsx
export default function LoadingSpinner() {
  return (
    <div className="loading-spinner">
      <div className="spinner"></div>
      <p>Cargando...</p>
    </div>
  )
}
```

```css
.loading-spinner {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 40px;
}

.spinner {
  width: 40px;
  height: 40px;
  border: 4px solid #e2e8f0;
  border-top-color: #2563eb;
  border-radius: 50%;
  animation: spin 1s linear infinite;
}

@keyframes spin {
  to { transform: rotate(360deg); }
}
```

- [ ] **Step 7: Create AlertMessage component**

```jsx
export default function AlertMessage({ type, message }) {
  if (!message) return null
  
  return (
    <div className={`alert-message alert-${type}`}>
      {type === 'error' && '❌ '}
      {type === 'success' && '✅ '}
      {type === 'warning' && '⚠️ '}
      {message}
    </div>
  )
}
```

```css
.alert-message {
  padding: 12px 16px;
  border-radius: 6px;
  margin-bottom: 15px;
  font-size: 14px;
}

.alert-error {
  background-color: #fee2e2;
  color: #dc2626;
  border: 1px solid #fecaca;
}

.alert-success {
  background-color: #dcfce7;
  color: #16a34a;
  border: 1px solid #bbf7d0;
}

.alert-warning {
  background-color: #fef3c7;
  color: #d97706;
  border: 1px solid #fde68a;
}
```

- [ ] **Step 8: Commit changes**

```bash
git add frontend/src/components/
git commit -m "feat: add reusable UI components"
```

---

### Task 15: Pages

**Files:**
- Create: `frontend/src/pages/LoginPage.jsx`
- Create: `frontend/src/pages/RegisterPage.jsx`
- Create: `frontend/src/pages/DashboardPage.jsx`
- Create: `frontend/src/pages/NewAppointmentPage.jsx`
- Create: `frontend/src/pages/MedicalHistoryPage.jsx`

- [ ] **Step 1: Create LoginPage**

```jsx
import { useState } from 'react'
import { Link, useNavigate } from 'react-router-dom'
import { clinicaApi } from '../api/clinicaApi'
import AlertMessage from '../components/AlertMessage'

export default function LoginPage() {
  const [pacienteId, setPacienteId] = useState('')
  const [loading, setLoading] = useState(false)
  const [error, setError] = useState('')
  const navigate = useNavigate()

  const handleSubmit = async (e) => {
    e.preventDefault()
    setError('')
    setLoading(true)

    try {
      const paciente = await clinicaApi.login(pacienteId)
      localStorage.setItem('pacienteId', paciente.id)
      localStorage.setItem('pacienteNombre', paciente.nombreCompleto)
      navigate('/dashboard')
    } catch (err) {
      setError(err.message)
    } finally {
      setLoading(false)
    }
  }

  return (
    <div className="auth-page">
      <div className="auth-card">
        <h1>🏥 Sistema de Gestión Médica</h1>
        <h2>Iniciar Sesión</h2>
        
        <AlertMessage type="error" message={error} />
        
        <form onSubmit={handleSubmit}>
          <div className="form-group">
            <label>ID de Paciente</label>
            <input
              type="text"
              value={pacienteId}
              onChange={(e) => setPacienteId(e.target.value)}
              placeholder="Ingrese su ID de paciente"
              required
            />
          </div>
          
          <button type="submit" className="btn btn-primary" disabled={loading}>
            {loading ? 'Iniciando...' : 'Iniciar Sesión'}
          </button>
        </form>
        
        <p className="auth-link">
          ¿No tiene cuenta? <Link to="/register">Registrarse</Link>
        </p>
      </div>
    </div>
  )
}
```

```css
.auth-page {
  min-height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
}

.auth-card {
  background: white;
  padding: 40px;
  border-radius: 12px;
  box-shadow: 0 10px 40px rgba(0, 0, 0, 0.2);
  width: 100%;
  max-width: 400px;
}

.auth-card h1 {
  text-align: center;
  margin-bottom: 10px;
  font-size: 24px;
}

.auth-card h2 {
  text-align: center;
  margin-bottom: 30px;
  color: #64748b;
  font-weight: 500;
}

.auth-card .btn {
  width: 100%;
  margin-top: 10px;
}

.auth-link {
  text-align: center;
  margin-top: 20px;
  color: #64748b;
}

.auth-link a {
  color: #2563eb;
  text-decoration: none;
}
```

- [ ] **Step 2: Create RegisterPage**

```jsx
import { useState } from 'react'
import { Link, useNavigate } from 'react-router-dom'
import { clinicaApi } from '../api/clinicaApi'
import AlertMessage from '../components/AlertMessage'

export default function RegisterPage() {
  const [formData, setFormData] = useState({
    nombreCompleto: '',
    numeroDocumento: '',
    fechaNacimiento: '',
    correoElectronico: '',
    telefono: '',
    alergias: []
  })
  const [alergiaInput, setAlergiaInput] = useState('')
  const [loading, setLoading] = useState(false)
  const [error, setError] = useState('')
  const [success, setSuccess] = useState('')
  const navigate = useNavigate()

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value })
  }

  const addAlergia = () => {
    if (alergiaInput.trim()) {
      setFormData({ ...formData, alergias: [...formData.alergias, alergiaInput.trim()] })
      setAlergiaInput('')
    }
  }

  const removeAlergia = (index) => {
    setFormData({ ...formData, alergias: formData.alergias.filter((_, i) => i !== index) })
  }

  const handleSubmit = async (e) => {
    e.preventDefault()
    setError('')
    setSuccess('')
    setLoading(true)

    try {
      const paciente = await clinicaApi.registerPatient({
        ...formData,
        listaAlergias: formData.alergias
      })
      setSuccess(`Paciente registrado exitosamente. Su ID es: ${paciente.id}`)
      setTimeout(() => {
        navigate('/')
      }, 2000)
    } catch (err) {
      setError(err.message)
    } finally {
      setLoading(false)
    }
  }

  return (
    <div className="auth-page">
      <div className="auth-card">
        <h1>🏥 Registro de Paciente</h1>
        
        <AlertMessage type="error" message={error} />
        <AlertMessage type="success" message={success} />
        
        <form onSubmit={handleSubmit}>
          <div className="form-group">
            <label>Nombre Completo</label>
            <input
              type="text"
              name="nombreCompleto"
              value={formData.nombreCompleto}
              onChange={handleChange}
              required
            />
          </div>
          
          <div className="form-group">
            <label>Número de Documento</label>
            <input
              type="text"
              name="numeroDocumento"
              value={formData.numeroDocumento}
              onChange={handleChange}
              required
            />
          </div>
          
          <div className="form-group">
            <label>Fecha de Nacimiento</label>
            <input
              type="date"
              name="fechaNacimiento"
              value={formData.fechaNacimiento}
              onChange={handleChange}
              required
            />
          </div>
          
          <div className="form-group">
            <label>Correo Electrónico</label>
            <input
              type="email"
              name="correoElectronico"
              value={formData.correoElectronico}
              onChange={handleChange}
              required
            />
          </div>
          
          <div className="form-group">
            <label>Teléfono</label>
            <input
              type="tel"
              name="telefono"
              value={formData.telefono}
              onChange={handleChange}
              required
            />
          </div>
          
          <div className="form-group">
            <label>Alergias (opcional)</label>
            <div className="alergia-input">
              <input
                type="text"
                value={alergiaInput}
                onChange={(e) => setAlergiaInput(e.target.value)}
                placeholder="Agregar alergia"
              />
              <button type="button" onClick={addAlergia} className="btn btn-secondary">Agregar</button>
            </div>
            <div className="alergias-list">
              {formData.alergias.map((alergia, index) => (
                <span key={index} className="alergia-tag">
                  {alergia}
                  <button type="button" onClick={() => removeAlergia(index)}>×</button>
                </span>
              ))}
            </div>
          </div>
          
          <button type="submit" className="btn btn-primary" disabled={loading}>
            {loading ? 'Registrando...' : 'Registrarse'}
          </button>
        </form>
        
        <p className="auth-link">
          ¿Ya tiene cuenta? <Link to="/">Iniciar Sesión</Link>
        </p>
      </div>
    </div>
  )
}
```

```css
.alergia-input {
  display: flex;
  gap: 10px;
}

.alergia-input input {
  flex: 1;
}

.btn-secondary {
  background-color: #64748b;
  color: white;
}

.alergias-list {
  display: flex;
  flex-wrap: wrap;
  gap: 8px;
  margin-top: 10px;
}

.alergia-tag {
  background: #fee2e2;
  color: #dc2626;
  padding: 4px 10px;
  border-radius: 20px;
  font-size: 13px;
  display: flex;
  align-items: center;
  gap: 5px;
}

.alergia-tag button {
  background: none;
  border: none;
  cursor: pointer;
  font-size: 16px;
  color: #dc2626;
}
```

- [ ] **Step 3: Create DashboardPage**

```jsx
import { useState, useEffect } from 'react'
import { Link } from 'react-router-dom'
import Navbar from '../components/Navbar'
import AppointmentCard from '../components/AppointmentCard'
import LabResultCard from '../components/LabResultCard'
import LoadingSpinner from '../components/LoadingSpinner'
import { clinicaApi } from '../api/clinicaApi'

export default function DashboardPage() {
  const pacienteId = localStorage.getItem('pacienteId')
  const [citas, setCitas] = useState([])
  const [resultados, setResultados] = useState([])
  const [loading, setLoading] = useState(true)

  useEffect(() => {
    loadDashboardData()
  }, [])

  const loadDashboardData = async () => {
    try {
      const historia = await clinicaApi.getHistoriaCompleta(pacienteId)
      setCitas(historia.citasPasadas || [])
      setResultados(historia.resultadosLaboratorio || [])
    } catch (error) {
      console.error('Error loading dashboard:', error)
    } finally {
      setLoading(false)
    }
  }

  if (loading) return <LoadingSpinner />

  return (
    <div>
      <Navbar />
      <div className="container">
        <h2>Dashboard</h2>
        
        <div className="dashboard-grid">
          <div className="dashboard-section">
            <div className="section-header">
              <h3>📅 Próximas Citas</h3>
              <Link to="/citas/nueva" className="btn btn-primary">Nueva Cita</Link>
            </div>
            
            {citas.length === 0 ? (
              <p className="empty-message">No hay citas programadas</p>
            ) : (
              citas.map(cita => <AppointmentCard key={cita.id} cita={cita} />)
            )}
          </div>
          
          <div className="dashboard-section">
            <div className="section-header">
              <h3>🧪 Últimos Resultados de Laboratorio</h3>
              <Link to="/historia" className="btn btn-secondary">Ver Todo</Link>
            </div>
            
            {resultados.length === 0 ? (
              <p className="empty-message">No hay resultados de laboratorio</p>
            ) : (
              resultados.slice(0, 2).map(resultado => (
                <LabResultCard key={resultado.id} resultado={resultado} />
              ))
            )}
          </div>
        </div>
      </div>
    </div>
  )
}
```

```css
.dashboard-grid {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 30px;
}

.section-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.empty-message {
  color: #94a3b8;
  text-align: center;
  padding: 40px;
  background: #f8fafc;
  border-radius: 8px;
}

.btn-secondary {
  background-color: #64748b;
  color: white;
}
```

- [ ] **Step 4: Create NewAppointmentPage**

```jsx
import { useState, useEffect } from 'react'
import { useNavigate } from 'react-router-dom'
import Navbar from '../components/Navbar'
import AlertMessage from '../components/AlertMessage'
import { clinicaApi } from '../api/clinicaApi'

export default function NewAppointmentPage() {
  const pacienteId = localStorage.getItem('pacienteId')
  const [especialidades] = useState(['Cardiología', 'Dermatología', 'Medicina General'])
  const [medicos, setMedicos] = useState([])
  const [disponibilidad, setDisponibilidad] = useState([])
  const [loading, setLoading] = useState(false)
  const [error, setError] = useState('')
  const [success, setSuccess] = useState('')
  const navigate = useNavigate()

  const [formData, setFormData] = useState({
    especialidad: '',
    medicoId: '',
    fecha: ''
  })

  useEffect(() => {
    loadMedicos()
  }, [])

  useEffect(() => {
    if (formData.medicoId && formData.fecha) {
      loadDisponibilidad()
    }
  }, [formData.medicoId, formData.fecha])

  const loadMedicos = async () => {
    try {
      const data = await clinicaApi.getMedicos(formData.especialidad || null)
      setMedicos(data)
    } catch (err) {
      setError(err.message)
    }
  }

  const loadDisponibilidad = async () => {
    try {
      const slots = await clinicaApi.getDisponibilidad(formData.medicoId, formData.fecha)
      setDisponibilidad(slots)
    } catch (err) {
      setError(err.message)
    }
  }

  const handleEspecialidadChange = (e) => {
    setFormData({ ...formData, especialidad: e.target.value, medicoId: '', fecha: '' })
    setMedicos([])
    setDisponibilidad([])
  }

  const handleMedicoChange = (e) => {
    setFormData({ ...formData, medicoId: e.target.value, fecha: '' })
    setDisponibilidad([])
  }

  const getMinDate = () => {
    const tomorrow = new Date()
    tomorrow.setDate(tomorrow.getDate() + 1)
    return tomorrow.toISOString().split('T')[0]
  }

  const handleSubmit = async (e) => {
    e.preventDefault()
    setError('')
    setSuccess('')
    setLoading(true)

    try {
      const selectedTime = disponibilidad[0]
      const fechaHora = new Date(`${formData.fecha}T${selectedTime}:00`)

      const medico = medicos.find(m => m.id === formData.medicoId)

      await clinicaApi.scheduleAppointment({
        pacienteId,
        medicoId: formData.medicoId,
        especialidad: formData.especialidad,
        fechaHora: fechaHora.toISOString()
      })

      setSuccess('Cita agendada exitosamente')
      setTimeout(() => navigate('/dashboard'), 1500)
    } catch (err) {
      setError(err.message)
    } finally {
      setLoading(false)
    }
  }

  return (
    <div>
      <Navbar />
      <div className="container">
        <h2>Nueva Cita Médica</h2>
        
        <AlertMessage type="error" message={error} />
        <AlertMessage type="success" message={success} />
        
        <div className="card">
          <form onSubmit={handleSubmit}>
            <div className="form-group">
              <label>Especialidad</label>
              <select
                value={formData.especialidad}
                onChange={handleEspecialidadChange}
                required
              >
                <option value="">Seleccionar especialidad</option>
                {especialidades.map(esp => (
                  <option key={esp} value={esp}>{esp}</option>
                ))}
              </select>
            </div>
            
            <div className="form-group">
              <label>Médico</label>
              <select
                value={formData.medicoId}
                onChange={handleMedicoChange}
                disabled={!formData.especialidad}
                required
              >
                <option value="">Seleccionar médico</option>
                {medicos.map(medico => (
                  <option key={medico.id} value={medico.id}>
                    {medico.nombreCompleto} - {medico.especialidad}
                  </option>
                ))}
              </select>
            </div>
            
            <div className="form-group">
              <label>Fecha</label>
              <input
                type="date"
                value={formData.fecha}
                onChange={(e) => setFormData({ ...formData, fecha: e.target.value })}
                min={getMinDate()}
                disabled={!formData.medicoId}
                required
              />
            </div>
            
            {disponibilidad.length > 0 && (
              <div className="form-group">
                <label>Horario Disponible</label>
                <div className="time-slots">
                  {disponibilidad.map((time, index) => (
                    <label key={index} className="time-slot">
                      <input
                        type="radio"
                        name="hora"
                        value={time}
                        defaultChecked={index === 0}
                        required
                      />
                      {time}
                    </label>
                  ))}
                </div>
              </div>
            )}
            
            <button type="submit" className="btn btn-primary" disabled={loading || disponibilidad.length === 0}>
              {loading ? 'Agendando...' : 'Agendar Cita'}
            </button>
          </form>
        </div>
      </div>
    </div>
  )
}
```

```css
.time-slots {
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
}

.time-slot {
  display: flex;
  align-items: center;
  gap: 5px;
  padding: 10px 15px;
  background: #f1f5f9;
  border-radius: 6px;
  cursor: pointer;
  transition: all 0.2s;
}

.time-slot:hover {
  background: #e2e8f0;
}

.time-slot input {
  width: auto;
}
```

- [ ] **Step 5: Create MedicalHistoryPage**

```jsx
import { useState, useEffect } from 'react'
import Navbar from '../components/Navbar'
import TabContainer from '../components/TabContainer'
import LabResultCard from '../components/LabResultCard'
import LoadingSpinner from '../components/LoadingSpinner'
import AlertMessage from '../components/AlertMessage'
import { clinicaApi } from '../api/clinicaApi'

export default function MedicalHistoryPage() {
  const pacienteId = localStorage.getItem('pacienteId')
  const [historia, setHistoria] = useState(null)
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState('')

  useEffect(() => {
    loadHistoria()
  }, [])

  const loadHistoria = async () => {
    try {
      const data = await clinicaApi.getHistoriaCompleta(pacienteId)
      setHistoria(data)
    } catch (err) {
      setError(err.message)
    } finally {
      setLoading(false)
    }
  }

  if (loading) return <LoadingSpinner />

  const formatDate = (date) => new Date(date).toLocaleDateString('es-ES')

  const consultasContent = historia.citasPasadas && historia.citasPasadas.length > 0 ? (
    historia.citasPasadas.map(consulta => (
      <div key={consulta.id} className="history-item">
        <div className="history-header">
          <h4>{consulta.especialidad}</h4>
          <span className="history-date">{formatDate(consulta.fechaHora)}</span>
        </div>
        <p><strong>Médico:</strong> {consulta.nombreMedico}</p>
        <p><strong>Estado:</strong> {consulta.estado}</p>
      </div>
    ))
  ) : (
    <p className="empty-message">No hay consultas registradas</p>
  )

  const prescripcionesContent = historia.prescripciones && historia.prescripciones.length > 0 ? (
    historia.prescripciones.map(prescripcion => (
      <div key={prescripcion.id} className="history-item">
        <div className="history-header">
          <h4>Prescripción</h4>
          <span className="history-date">{formatDate(prescripcion.fechaEmision)}</span>
        </div>
        <p><strong>Médico:</strong> {prescripcion.medico}</p>
        <div className="medicamentos-list">
          {prescripcion.medicamentos.map((med, index) => (
            <div key={index} className="medicamento-item">
              <strong>{med.nombre}</strong> - {med.dosis} - {med.frecuencia} - {med.duracion}
            </div>
          ))}
        </div>
      </div>
    ))
  ) : (
    <p className="empty-message">No hay prescripciones registradas</p>
  )

  const laboratoriosContent = historia.resultadosLaboratorio && historia.resultadosLaboratorio.length > 0 ? (
    historia.resultadosLaboratorio.map(resultado => (
      <LabResultCard key={resultado.id} resultado={resultado} />
    ))
  ) : (
    <p className="empty-message">No hay resultados de laboratorio</p>
  )

  const tabs = [
    { id: 'consultas', label: 'Consultas', content: consultasContent },
    { id: 'prescripciones', label: 'Prescripciones', content: prescripcionesContent },
    { id: 'laboratorio', label: 'Laboratorio', content: laboratoriosContent }
  ]

  return (
    <div>
      <Navbar />
      <div className="container">
        <h2>Historia Clínica Completa</h2>
        
        <AlertMessage type="error" message={error} />
        
        <TabContainer 
          tabs={tabs} 
          activeTab="consultas" 
          onTabChange={() => {}} 
        />
      </div>
    </div>
  )
}
```

```css
.history-item {
  background: white;
  border-radius: 8px;
  padding: 15px;
  margin-bottom: 15px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.history-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 10px;
}

.history-date {
  color: #64748b;
  font-size: 14px;
}

.medicamentos-list {
  margin-top: 10px;
}

.medicamento-item {
  padding: 8px;
  background: #f8fafc;
  border-radius: 4px;
  margin-bottom: 5px;
}
```

- [ ] **Step 6: Commit changes**

```bash
git add frontend/src/pages/
git commit -m "feat: add all frontend pages"
```

- [ ] **Step 7: Test frontend build**

Run: `cd frontend && npm install && npm run build`
Expected: BUILD SUCCESS

---

## Deployment

### Task 16: Deploy to Railway

**Files:**
- Create: `backend/Dockerfile` (optional)

- [ ] **Step 1: Create Dockerfile**

```dockerfile
FROM openjdk:17-alpine
WORKDIR /app
COPY target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
```

- [ ] **Step 2: Push to GitHub and deploy to Railway**

Instructions:
1. Create GitHub repository
2. Push all code
3. Go to Railway.app and create new project
4. Connect GitHub repository
5. Deploy automatically

### Task 17: Deploy to Vercel

**Files:**
- Create: `frontend/.env.production`

- [ ] **Step 1: Create Vercel config**

Create `frontend/vercel.json`:
```json
{
  "rewrites": [
    { "source": "/(.*)", "destination": "/index.html" }
  ]
}
```

- [ ] **Step 2: Deploy to Vercel**

Instructions:
1. Go to Vercel.com
2. Import GitHub repository
3. Set build command: `npm run build`
4. Set output directory: `dist`
5. Add environment variable: `VITE_API_URL=<your-railway-backend-url>`
6. Deploy

---

## Final Verification

- [ ] Backend runs on Railway
- [ ] Frontend runs on Vercel
- [ ] All REST endpoints work
- [ ] Facade orchestrates 2+ subsystems
- [ ] User can register and login
- [ ] User can schedule appointments
- [ ] User can view medical history
- [ ] Lab results show visual indicators
- [ ] All UI text in Spanish
- [ ] All code in English
- [ ] No generic variable names

---

Plan complete. Two execution options:

1. **Subagent-Driven (recommended)** - I dispatch a fresh subagent per task, review between tasks, fast iteration

2. **Inline Execution** - Execute tasks in this session using executing-plans, batch execution with checkpoints

Which approach?