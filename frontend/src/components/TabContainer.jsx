export default function TabContainer({ tabs, activeTab, onTabChange }) {
  return (
    <div className="tab-container">
      <div className="tab-buttons">
        {tabs.map((tab) => (
          <button
            key={tab.id}
            className={`tab-button ${activeTab === tab.id ? 'active' : ''}`}
            onClick={() => onTabChange(tab.id)}
          >
            {tab.label}
          </button>
        ))}
      </div>
      <div className="tab-content">
        {tabs.find(t => t.id === activeTab)?.content}
      </div>
    </div>
  )
}