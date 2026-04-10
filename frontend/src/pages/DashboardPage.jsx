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