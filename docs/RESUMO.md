# Medicenter — Resumo do que foi feito

## 1. Conexão com o banco (resolvida)
- O servidor na porta 3306 é o **MariaDB 10.4 do XAMPP**, não o MySQL 8.0 do plano. Funciona igual para o projeto.
- Erro inicial: `Access denied for user 'root'@'localhost'` (senha errada no `application.properties`).
- A senha do root foi alterada no Workbench com `ALTER USER`. O `FLUSH PRIVILEGES` falhou com erro Aria 176
  (tabela de sistema do MariaDB corrompida), mas a senha nova passou a valer mesmo assim.
- Segundo problema: o IntelliJ não reconhecia o projeto como Maven ("There are no Maven projects to display"),
  então o `application.properties` não era copiado para `target/classes`.
  Solução: adicionar o `pom.xml` como projeto Maven (botão **+** na aba Maven) e rodar `clean`.
- Resultado: `HikariPool-1 - Start completed`, o Hibernate criou as tabelas
  `consultas`, `especialidades`, `medicos` e `pacientes`, e `Started MedicenterApplication`.

## 2. API (backend) criada
Pacote `com.medicenter`:
- `controller/`: Paciente, Medico, Especialidade, Consulta
- `service/`: regras de negócio de cada área
- `dto/`: MedicoRequest/Response, ConsultaRequest/Response
- `exception/`: erros em JSON (400, 404, 409)
- `config/CorsConfig`: libera o frontend em http://localhost:5173

Endpoints (`/api/...`):
| Recurso | Operações |
|---|---|
| /pacientes | GET, GET/{id}, POST, PUT/{id}, DELETE/{id} (inativa) |
| /medicos | GET, GET/{id}, POST, PUT/{id}, DELETE/{id} (inativa) |
| /especialidades | GET, GET/{id}, POST, PUT/{id}, DELETE/{id} |
| /consultas | GET, GET/{id}, POST, PUT/{id}, PATCH/{id}/status, DELETE/{id} |

Regras:
- Conflito de horário: 409 se o médico ou o paciente já tem consulta no intervalo (canceladas não contam).
- O fim da consulta precisa ser depois do início.
- Médico ou paciente inativo não recebe consulta nova.
- DELETE de paciente/médico apenas inativa, para manter o histórico.
- Datas no formato `2026-10-20T14:00:00`.

## 3. Testes feitos
- Criados pelo PowerShell: 1 especialidade, 1 paciente, 1 médico e 1 consulta.
- Conferido no Workbench: `SELECT * FROM medicenter.consultas;` retornou a consulta.
- Frontend (`npm install` + `npm run dev` na pasta `frontend`) abriu em http://localhost:5173
  e o painel mostrou 1 paciente, 1 médico e 1 consulta.

## 4. Como rodar
1. Iniciar o MySQL/MariaDB (XAMPP).
2. Copiar `application.properties.example` para `application.properties` e colocar a senha.
3. IntelliJ: rodar `MedicenterApplication` (porta 8080).
4. Em outro terminal: `cd frontend`, `npm install`, `npm run dev` (porta 5173).

## 5. Pendências
- Subir para o GitHub (ver `GITHUB.md`).
- Testar o conflito de horário (consulta 14:15–14:45 deve dar 409).
- Conferir se as telas de Pacientes, Médicos e Consultas mostram todos os campos.
- Formulários de cadastro no frontend (hoje só lista).
- Opcional: reparar o erro Aria do MariaDB (fazer backup de `C:\xampp\mysql\data` antes).
- Opcional: `spring.jpa.database-platform=org.hibernate.dialect.MariaDBDialect` para tirar o aviso do dialeto.

## 6. Segurança
Nunca publicar: senha do MySQL, `.env`, tokens, dados reais de pacientes.
