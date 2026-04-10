export default function AlertMessage({ type, message }) {
  if (!message) return null
  
  return (
    <div className={`alert-message alert-${type}`}>
      {type === 'error' && '❌ '}
      {type === 'success' && '✅ '}
      {type === 'warning' && '⚠️ '}
      {message}
    </div>
  )
}