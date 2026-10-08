import { useEffect, useState, type ReactNode } from 'react'
import { CalendarDays, LayoutDashboard, Menu, Stethoscope, Users, X } from 'lucide-react'
import { api, Consulta, Medico, Paciente } from './api'

type Page = 'dashboard' | 'pacientes' | 'medicos' | 'consultas'

const pageTitles: Record<Page, string> = {
  dashboard: 'Visão geral',
  pacientes: 'Pacientes',
  medicos: 'Médicos',
  consultas: 'Consultas',
}

function App() {
  const [page, setPage] = useState<Page>('dashboard')
  const [menuOpen, setMenuOpen] = useState(false)

  return (
    <div className="app-shell">
      <aside className={menuOpen ? 'sidebar open' : 'sidebar'}>
        <div className="brand"><span className="brand-mark">M</span><div><strong>Medicenter</strong><small>Gestão em saúde</small></div></div>
        <nav>
          <NavItem icon={<LayoutDashboard size={18} />} label="Visão geral" active={page === 'dashboard'} onClick={() => { setPage('dashboard'); setMenuOpen(false) }} />
          <NavItem icon={<Users size={18} />} label="Pacientes" active={page === 'pacientes'} onClick={() => { setPage('pacientes'); setMenuOpen(false) }} />
          <NavItem icon={<Stethoscope size={18} />} label="Médicos" active={page === 'medicos'} onClick={() => { setPage('medicos'); setMenuOpen(false) }} />
          <NavItem icon={<CalendarDays size={18} />} label="Consultas" active={page === 'consultas'} onClick={() => { setPage('consultas'); setMenuOpen(false) }} />
        </nav>
        <div className="sidebar-footer"><span className="avatar">N</span><div><strong>Equipe Medicenter</strong><small>Administrador</small></div></div>
      </aside>
      {menuOpen && <button className="overlay" onClick={() => setMenuOpen(false)} aria-label="Fechar menu" />}
      <main className="main-content">
        <header className="topbar"><button className="menu-button" onClick={() => setMenuOpen(!menuOpen)} aria-label="Abrir menu">{menuOpen ? <X /> : <Menu />}</button><div><span className="eyebrow">PAINEL ADMINISTRATIVO</span><h1>{pageTitles[page]}</h1></div><div className="topbar-user"><span className="avatar">N</span><span>Administrador</span></div></header>
        <section className="content">{page === 'dashboard' && <Dashboard onNavigate={setPage} />}{page === 'pacientes' && <Pacientes />}{page === 'medicos' && <Medicos />}{page === 'consultas' && <Consultas />}</section>
      </main>
    </div>
  )
}

function NavItem({ icon, label, active, onClick }: { icon: ReactNode; label: string; active: boolean; onClick: () => void }) {
  return <button className={active ? 'nav-item active' : 'nav-item'} onClick={onClick}>{icon}<span>{label}</span></button>
}

function Dashboard({ onNavigate }: { onNavigate: (page: Page) => void }) {
  const [counts, setCounts] = useState({ pacientes: 0, medicos: 0, consultas: 0 })
  useEffect(() => { Promise.all([api.get('/pacientes'), api.get('/medicos'), api.get('/consultas')]).then(([p, m, c]) => setCounts({ pacientes: p.data.length, medicos: m.data.length, consultas: c.data.length })).catch(() => undefined) }, [])
  return <><div className="welcome"><div><span className="eyebrow">BEM-VINDO AO MEDICENTER</span><h2>Cuide da gestão. <em>Cuide das pessoas.</em></h2><p>Use o menu para consultar os cadastros e acompanhar os agendamentos.</p></div><div className="welcome-icon">✦</div></div><div className="stats"><Stat label="Pacientes cadastrados" value={counts.pacientes} color="blue" /><Stat label="Médicos cadastrados" value={counts.medicos} color="green" /><Stat label="Consultas registradas" value={counts.consultas} color="orange" /></div><div className="quick-actions"><h3>Acesso rápido</h3><div className="action-grid"><Action title="Gerenciar pacientes" text="Consultar e cadastrar pacientes" icon={<Users />} onClick={() => onNavigate('pacientes')} /><Action title="Gerenciar médicos" text="Consultar profissionais" icon={<Stethoscope />} onClick={() => onNavigate('medicos')} /><Action title="Ver consultas" text="Acompanhar agendamentos" icon={<CalendarDays />} onClick={() => onNavigate('consultas')} /></div></div></>
}

function Stat({ label, value, color }: { label: string; value: number; color: string }) { return <div className="stat-card"><span className={`stat-icon ${color}`}>●</span><div><strong>{value}</strong><span>{label}</span></div></div> }
function Action({ title, text, icon, onClick }: { title: string; text: string; icon: ReactNode; onClick: () => void }) { return <button className="action-card" onClick={onClick}><span className="action-icon">{icon}</span><span><strong>{title}</strong><small>{text}</small></span><b>→</b></button> }

function Pacientes() { return <DataPage<Paciente> title="Pacientes cadastrados" endpoint="/pacientes" columns={['nomeCompleto', 'cpf', 'email', 'ativo']} /> }
function Medicos() { return <DataPage<Medico> title="Médicos cadastrados" endpoint="/medicos" columns={['nomeCompleto', 'crm', 'ativo']} /> }
function Consultas() { return <DataPage<Consulta> title="Consultas registradas" endpoint="/consultas" columns={['dataHoraInicio', 'status', 'motivo']} /> }

function DataPage<T extends Record<string, unknown>>({ title, endpoint, columns }: { title: string; endpoint: string; columns: string[] }) {
  const [items, setItems] = useState<T[]>([])
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState(false)
  useEffect(() => { api.get<T[]>(endpoint).then((response) => setItems(response.data)).catch(() => setError(true)).finally(() => setLoading(false)) }, [endpoint])
  return <div className="data-page"><div className="page-heading"><div><span className="eyebrow">CADASTRO</span><h2>{title}</h2></div><button className="primary-button">+ Novo cadastro</button></div><div className="table-card">{loading ? <div className="empty">Carregando dados...</div> : error ? <div className="empty"><strong>API ainda não conectada</strong><span>Confira se o backend está rodando em http://localhost:8080.</span></div> : items.length === 0 ? <div className="empty"><strong>Nenhum registro encontrado</strong><span>Os dados aparecerão aqui quando forem cadastrados.</span></div> : <div className="table-wrap"><table><thead><tr>{columns.map((column) => <th key={column}>{column}</th>)}</tr></thead><tbody>{items.map((item, index) => <tr key={String(item.id ?? index)}>{columns.map((column) => <td key={column}>{String(item[column] ?? '—')}</td>)}</tr>)}</tbody></table></div>}</div></div>
}

export default App
