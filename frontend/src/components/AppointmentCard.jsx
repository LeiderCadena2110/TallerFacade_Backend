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