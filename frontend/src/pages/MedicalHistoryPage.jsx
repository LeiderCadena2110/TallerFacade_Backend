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