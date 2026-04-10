export default function RangeIndicator({ dentroRango }) {
  return (
    <span className={`range-indicator ${dentroRango ? 'in-range' : 'out-of-range'}`}>
      {dentroRango ? '✓' : '✗'}
    </span>
  )
}