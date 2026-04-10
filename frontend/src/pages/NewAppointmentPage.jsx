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