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