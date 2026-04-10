# Medical Clinic System - Specification Document

## Project Overview

- **Project Name**: Sistema de Gestión Médica (Medical Clinic Management System)
- **Type**: Full-stack web application
- **Core Functionality**: Patient portal for scheduling appointments, viewing medical history, receiving prescriptions, and requesting lab tests
- **Target Users**: Clinic patients

---

## Architecture

### Backend (Spring Boot)

**Port**: 8080

**Package Structure**:
```
com.clinica
├── controller
│   └── ClinicaController.java
├── service
│   ├── PacienteService.java
│   ├── AgendaService.java
│   ├── HistoriaClinicaService.java
│   ├── PrescripcionService.java
│   ├── LaboratorioService.java
│   └── ClinicaFacade.java
├── model
│   ├── dto/
│   │   ├── PacienteDTO.java
│   │   ├── CitaDTO.java
│   │   ├── MedicoDTO.java
│   │   ├── HistoriaClinicaDTO.java
│   │   ├── PrescripcionDTO.java
│   │   └── ResultadoLaboratorioDTO.java
│   └── entity/
│       └── (in-memory models)
└── repository
    └── (in-memory data stores)
```

**Subsystems**:

1. **PacienteService**
   - `registerPatient(PacienteDTO)` - Register with personal data
   - `validateUniqueDocument(String numeroDocumento)` - Unique document validation
   - `getPatientProfile(String pacienteId)` - Query patient profile

2. **AgendaService**
   - `getAvailableSlots(String especialidad, String fecha)` - Available slots by specialty
   - `scheduleAppointment(CitaDTO)` - Schedule or cancel appointments
   - Reminders included in response

3. **HistoriaClinicaService**
   - `registerConsultation(String pacienteId, ConsultaDTO)` - Record consultations
   - `getPatientHistory(String pacienteId)` - Query medical history (diagnoses, allergies)

4. **PrescripcionService**
   - `generatePrescription(String pacienteId, List<MedicamentoDTO>)` - Generate prescription with validation
   - Allergy validation before generating

5. **LaboratorioService**
   - `requestExams(String pacienteId, List<String> examenes)` - Request lab tests
   - Return simulated results with reference values (hemograma, glicemia, perfil lipídico)

**ClinicaFacade** (Required):

| Method | Invoked Subsystems |
|--------|---------------------|
| `agendarCita(pacienteId, especialidad, fecha)` | AgendaService + PacienteService |
| `verHistoriaCompleta(pacienteId)` | HistoriaClinicaService + PrescripcionService + LaboratorioService |
| `generarPrescripcion(pacienteId, medicamentos[])` | PrescripcionService + PacienteService (allergy check) |
| `solicitarExamenes(pacienteId, examenes[])` | LaboratorioService + PacienteService |

### Frontend (React + Vite)

**Stack**: React 18, React Router v6, Vite

**Pages** (Routes):

| Route | Component | Description |
|-------|-----------|-------------|
| `/` | LoginPage | Patient login |
| `/register` | RegisterPage | Patient registration |
| `/dashboard` | Dashboard | Next appointments + recent results |
| `/citas/nueva` | NewAppointmentPage | Schedule new appointment |
| `/historia` | MedicalHistoryPage | Full history with tabs |

**UI Language**: Spanish (all user-facing text)

---

## Data Models

### Backend Entities (In-Memory)

**Paciente**:
```java
- id: String (UUID)
- nombreCompleto: String
- numeroDocumento: String (unique)
- fechaNacimiento: LocalDate
- correoElectronico: String
- telefono: String
- listaAlergias: List<String>
```

**Medico**:
```java
- id: String
- nombreCompleto: String
- especialidad: String
- disponibilidad: Map<LocalDate, List<LocalTime>>
```

**Cita**:
```java
- id: String
- pacienteId: String
- medicoId: String
- especialidad: String
- fechaHora: LocalDateTime
- estado: String (AGENDADA, CANCELADA, COMPLETADA)
```

**ConsultaMedica**:
```java
- id: String
- pacienteId: String
- fecha: LocalDate
- motivo: String
- diagnostico: String
- notas: String
```

**Prescripcion**:
```java
- id: String
- pacienteId: String
- fechaEmision: LocalDate
- medicamentos: List<Medicamento>
- medico: String
```

**Medicamento**:
```java
- nombre: String
- dosis: String
- frecuencia: String
- duracion: String
```

**ResultadoLaboratorio**:
```java
- id: String
- pacienteId: String
- tipoExamen: String
- fechaSolicitud: LocalDate
- resultados: Map<String, ValorResultado>
- estado: String (PENDIENTE, COMPLETADO)
```

**ValorResultado**:
```java
- nombre: String
- valor: Double
- unidad: String
- valorMinimo: Double
- valorMaximo: Double
- dentroRango: Boolean
```

---

## API Endpoints

| Method | Endpoint | Body | Response |
|--------|-----------|------|----------|
| POST | `/api/clinica/paciente` | PacienteDTO | PacienteDTO |
| POST | `/api/clinica/cita` | CitaRequestDTO | CitaDTO |
| GET | `/api/clinica/historia/{pacienteId}` | - | HistoriaCompletaDTO |
| POST | `/api/clinica/prescripcion` | PrescripcionRequestDTO | PrescripcionDTO |
| POST | `/api/clinica/laboratorio` | LaboratorioRequestDTO | ResultadoLaboratorioDTO |
| GET | `/api/clinica/medicos?especialidad=` | - | List<MedicoDTO> |
| GET | `/api/clinica/disponibilidad?especialidad=&fecha=` | - | List<LocalTime> |

---

## Predefined Data

**Specialties** (3 minimum):
- Cardiología
- Dermatología
- Medicina General

**Doctors** (5 minimum):
1. Dr. Juan Pérez - Cardiología
2. Dra. María González - Cardiología
3. Dr. Carlos Rodríguez - Dermatología
4. Dra. Ana Martínez - Medicina General
5. Dr. Luis Sánchez - Medicina General

**Lab Tests**:
- Hemograma (Hb, leucocitos, plaquetas)
- Glicemia (glucosa en ayunas)
- Perfil Lipídico (colesterol total, LDL, HDL, triglicéridos)

---

## Frontend Components

### Reusable Components

1. **Navbar** - Navigation menu with patient name
2. **AppointmentCard** - Display appointment info
3. **LabResultCard** - Display lab results with visual indicators
4. **TabContainer** - Switch between consultations, prescriptions, labs
5. **RangeIndicator** - Green/red indicator for normal/abnormal values
6. **LoadingSpinner** - Loading states
7. **AlertMessage** - Error/success messages

### Page Specifications

**Login/Register**:
- Form fields in Spanish
- Validation messages in Spanish

**Dashboard**:
- "Próximas Citas" section
- "Últimos Resultados de Laboratorio" section
- Quick actions buttons

**New Appointment**:
- Dropdown: Especialidad
- Dropdown: Médico (filtered by specialty)
- Calendar/Time slot picker
- Confirm button: "Agendar Cita"

**Medical History**:
- Tabs: "Consultas", "Prescripciones", "Laboratorio"
- Each tab shows relevant data
- Lab results show color-coded values (verde=normal, rojo=anormal)

---

## Variable Naming Conventions

### Must Use (Medical Context)

| Concept | Variable Name |
|---------|---------------|
| Patient ID | `pacienteId` / `patientId` |
| Medical appointment | `cita` / `appointment` |
| Doctor | `medico` / `physician` |
| Specialty | `especialidad` / `specialty` |
| Medical history | `historiaClinica` / `medicalHistory` |
| Prescription | `prescripcion` / `prescription` |
| Medication | `medicamento` / `medication` |
| Lab test | `examenLaboratorio` / `labExam` |
| Lab result | `resultadoLaboratorio` / `labResult` |
| Document number | `numeroDocumento` / `documentNumber` |
| Full name | `nombreCompleto` / `fullName` |
| Birth date | `fechaNacimiento` / `birthDate` |
| Email | `correoElectronico` / `email` |
| Phone | `telefono` / `phoneNumber` |
| Allergy | `alergia` / `allergy` |
| Diagnosis | `diagnostico` / `diagnosis` |
| Consultation | `consulta` / `consultation` |
| Schedule/Book | `agendar` / `schedule` |
| Cancel | `cancelar` / `cancel` |

### Must NOT Use (Professor's Example Variables)

Generic e-commerce terms from professor's example:
- ❌ `tienda`, `producto`, `orden`, `carrito`
- ❌ `cliente` (use `paciente` instead)
- ❌ `carrito_compras`, `agregar`, `remover`
- ❌ Generic names like `data`, `item`, `list`, `temp`

---

## Deployment

| Component | Platform | Configuration |
|-----------|----------|---------------|
| Backend | Railway | Spring Boot JAR or Docker |
| Frontend | Vercel | Connect to GitHub repository |

**Environment Variables**:
- Backend: `PORT=8080`
- Frontend: API URL pointing to Railway

---

## Acceptance Criteria

1. ✅ Backend starts successfully on Railway
2. ✅ Frontend deploys successfully on Vercel
3. ✅ All 6 REST endpoints work correctly
4. ✅ ClinicaFacade orchestrates 2+ subsystems per method
5. ✅ Patient can register and login
6. ✅ Patient can schedule appointments with any specialty
7. ✅ Patient can view complete medical history
8. ✅ Lab results show visual indicator for normal/abnormal values
9. ✅ All user-facing text is in Spanish
10. ✅ All code (variables, classes) is in English
11. ✅ No generic variable names from professor's example