# Medicenter

Sistema acadêmico de gerenciamento de uma clínica médica, desenvolvido com API REST, frontend web e banco de dados relacional.

> Projeto acadêmico. Não utilizar dados reais de pacientes, senhas reais ou credenciais de produção.

## Equipe

- Maina
- Gabriel
- Nicolas

## Tecnologias

### Backend

- Java 21
- Spring Boot 3
- Maven
- Spring Web
- Spring Data JPA
- Bean Validation
- Spring Security
- MySQL/MariaDB

### Frontend

- React
- TypeScript
- Vite
- Axios
- Lucide React

## Funcionalidades

- Cadastro, consulta, alteração e inativação de pacientes;
- cadastro, consulta, alteração e inativação de médicos;
- cadastro e gerenciamento de especialidades;
- criação e consulta de consultas;
- alteração do status de consultas;
- validação de conflitos de horário;
- bloqueio de consulta para médico inativo;
- bloqueio de consulta para paciente inativo;
- tratamento de erros HTTP;
- dashboard web;
- telas de pacientes, médicos e consultas;
- persistência em MySQL/MariaDB.

## Estrutura do projeto

```text
medicenter/
├── README.md
├── pom.xml
├── database/
├── docs/
├── frontend/
└── src/
    └── main/
        ├── java/com/medicenter/
        │   ├── config/
        │   ├── controller/
        │   ├── dto/
        │   ├── entity/
        │   ├── exception/
        │   ├── repository/
        │   └── service/
        └── resources/
            ├── application.properties.example
            └── application.properties
```

## Pré-requisitos

Instale:

- JDK 21;
- IntelliJ IDEA;
- MySQL ou MariaDB;
- Node.js e npm;
- Git.

Verifique:

```powershell
java -version
node -v
npm -v
git --version
```

## Configuração do banco

Crie o banco:

```sql
CREATE DATABASE IF NOT EXISTS medicenter
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;
```

O banco utiliza normalmente:

```text
Host: localhost
Porta: 3306
Banco: medicenter
Usuário: root
```

## Configuração do backend

Na pasta `src/main/resources`, copie:

```text
application.properties.example
```

para:

```text
application.properties
```

Configure a senha local do banco:

```properties
spring.application.name=medicenter

spring.datasource.url=jdbc:mysql://localhost:3306/medicenter?serverTimezone=America/Sao_Paulo&allowPublicKeyRetrieval=true&useSSL=false
spring.datasource.username=root
spring.datasource.password=SUA_SENHA_LOCAL
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.open-in-view=false

server.port=8080
```

> O arquivo `application.properties` com senha real não deve ser enviado ao GitHub.

## Executar o backend

Abra o projeto no IntelliJ, aguarde o Maven carregar as dependências e execute:

```text
com.medicenter.MedicenterApplication
```

A API será executada em:

```text
http://localhost:8080
```

A aplicação iniciou corretamente quando aparecer:

```text
Started MedicenterApplication
```

## Executar o frontend

Em outro terminal:

```powershell
cd frontend
npm install
npm run dev
```

Abra:

```text
http://localhost:5173
```

O frontend utiliza a API:

```text
http://localhost:8080/api
```

O backend e o frontend precisam estar executando ao mesmo tempo.

## Endpoints principais

### Pacientes

```text
GET    /api/pacientes
GET    /api/pacientes/{id}
POST   /api/pacientes
PUT    /api/pacientes/{id}
DELETE /api/pacientes/{id}
```

Exemplo:

```json
{
  "nomeCompleto": "Ana Oliveira",
  "cpf": "987.654.321-00",
  "dataNascimento": "1998-08-15",
  "telefone": "(11 ) 98888-7777",
  "email": "ana.oliveira@example.com"
}
```

### Especialidades

```text
GET    /api/especialidades
GET    /api/especialidades/{id}
POST   /api/especialidades
PUT    /api/especialidades/{id}
DELETE /api/especialidades/{id}
```

### Médicos

```text
GET    /api/medicos
GET    /api/medicos/{id}
POST   /api/medicos
PUT    /api/medicos/{id}
DELETE /api/medicos/{id}
```

### Consultas

```text
GET    /api/consultas
GET    /api/consultas/{id}
POST   /api/consultas
PUT    /api/consultas/{id}
PATCH  /api/consultas/{id}/status
DELETE /api/consultas/{id}
```

Exemplo:

```json
{
  "pacienteId": 1,
  "medicoId": 1,
  "dataHoraInicio": "2026-10-20T14:00:00",
  "dataHoraFim": "2026-10-20T14:30:00",
  "motivo": "Check-up"
}
```

## Regras de negócio

- CPF de paciente não pode ser duplicado;
- CRM de médico não pode ser duplicado;
- médico inativo não recebe novos agendamentos;
- paciente inativo não recebe novos agendamentos;
- o horário final deve ser posterior ao horário inicial;
- médico não pode ter consultas sobrepostas;
- paciente não deve ter consultas sobrepostas;
- consultas canceladas não bloqueiam novos horários;
- pacientes e médicos são inativados para preservar o histórico.

## Conferir os dados no banco

```sql
USE medicenter;

SHOW TABLES;

SELECT * FROM pacientes;
SELECT * FROM medicos;
SELECT * FROM especialidades;
SELECT * FROM consultas;
```

## Segurança

A autenticação e a autorização por perfil ainda estão em desenvolvimento/revisão.

Antes da entrega final, devem ser revisados:

- login;
- senha com BCrypt;
- autorização por perfil;
- proteção dos endpoints;
- configuração de CORS;
- remoção de credenciais do código.

Nunca publique:

```text
application.properties
.env
senhas
tokens
dados reais de pacientes
```

## Próximas melhorias

- formulários de cadastro no frontend;
- login e autorização por perfil;
- CRUD de funcionários;
- gerenciamento de usuários;
- prontuários médicos;
- testes automatizados;
- diagrama de caso de uso;
- scripts SQL completos;
- recuperação de senha;
- notificações;
- pagamentos e convênios.

## Finalidade

Este sistema foi desenvolvido para fins acadêmicos. Não deve ser utilizado em ambiente clínico real sem auditoria, adequação à LGPD, revisão de segurança, backups, monitoramento e validação profissional.
