import RangeIndicator from './RangeIndicator'

export default function LabResultCard({ resultado }) {
  const formatDate = (date) => {
    return new Date(date).toLocaleDateString('es-ES')
  }

  return (
    <div className="lab-result-card">
      <div className="lab-result-header">
        <h3>{resultado.tipoExamen}</h3>
        <span className="lab-date">{formatDate(resultado.fechaSolicitud)}</span>
      </div>
      <div className="lab-results">
        {Object.entries(resultado.resultados).map(([key, value]) => (
          <div key={key} className="result-item">
            <span className="result-name">{value.nombre}</span>
            <div className="result-value">
              <span className="result-number">{value.valor}</span>
              <span className="result-unit">{value.unidad}</span>
              <RangeIndicator dentroRango={value.dentroRango} />
            </div>
            <span className="result-range">
              Normal: {value.valorMinimo} - {value.valorMaximo}
            </span>
          </div>
        ))}
      </div>
    </div>
  )
}