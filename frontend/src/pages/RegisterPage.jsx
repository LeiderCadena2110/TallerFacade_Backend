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