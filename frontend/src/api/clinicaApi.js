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