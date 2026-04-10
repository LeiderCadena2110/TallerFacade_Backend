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