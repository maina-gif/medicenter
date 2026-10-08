import axios from 'axios'

export const api = axios.create({
  baseURL: import.meta.env.VITE_API_URL ?? 'http://localhost:8080/api',
  headers: { 'Content-Type': 'application/json' },
})

export type Paciente = {
  id: number
  nomeCompleto: string
  cpf: string
  dataNascimento: string
  telefone?: string
  email?: string
  ativo: boolean
}

export type Medico = {
  id: number
  nomeCompleto: string
  crm: string
  especialidade?: { id: number; nome: string }
  ativo: boolean
}

export type Consulta = {
  id: number
  paciente?: Paciente
  medico?: Medico
  dataHoraInicio: string
  dataHoraFim: string
  status: string
  motivo?: string
}
