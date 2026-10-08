# Medicenter Frontend

Frontend em React + TypeScript + Vite.

## Executar

```bash
npm install
npm run dev
```

Acesse `http://localhost:5173`.

A API esperada é `http://localhost:8080/api`. Para alterar, copie `.env.example` para `.env` e ajuste `VITE_API_URL`.

As telas já estão organizadas para consumir:

- `GET /api/pacientes`
- `GET /api/medicos`
- `GET /api/consultas`

Os botões de cadastro são o ponto de extensão para os formulários que serão feitos depois que os endpoints POST estiverem prontos.
