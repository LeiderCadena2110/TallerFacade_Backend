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