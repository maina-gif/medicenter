Medicenter

API REST e frontend de um sistema acadêmico para gerenciamento de clínica médica.


Projeto acadêmico. Não utilizar dados reais de pacientes, senhas reais ou credenciais de produção.

Integrantes

•
Maina

•
Gabriel

•
Nicolas

Tecnologias

Backend

•
Java 21

•
Spring Boot 3.4.5

•
Maven

•
Spring Web

•
Spring Data JPA

•
Bean Validation

•
Spring Security

•
MySQL/MariaDB

Frontend

•
React

•
TypeScript

•
Vite

•
Axios

•
Lucide React

Funcionalidades atuais

•
CRUD de pacientes;

•
CRUD de médicos;

•
CRUD de especialidades;

•
criação e consulta de agendamentos;

•
alteração de status de consultas;

•
validação de conflitos de horário;

•
bloqueio de consulta para paciente inativo;

•
bloqueio de consulta para médico inativo;

•
inativação lógica de pacientes e médicos;

•
tratamento de erros HTTP;

•
configuração de CORS para o frontend;

•
frontend com dashboard e telas de pacientes, médicos e consultas;

•
persistência em banco MySQL/MariaDB.

Estrutura do projeto

Plain Text


medicenter/
├── README.md
├── .gitignore
├── pom.xml
├── database/
│   ├── schema.sql
│   └── seed.sql
├── docs/
│   ├── requests.http
│   ├── arquitetura.md
│   ├── regras-negocio.md
│   └── proximos-passos.md
├── src/
│   ├── main/
│   │   ├── java/com/medicenter/
│   │   │   ├── config/
│   │   │   ├── controller/
│   │   │   ├── dto/
│   │   │   ├── entity/
│   │   │   ├── exception/
│   │   │   ├── repository/
│   │   │   └── service/
│   │   └── resources/
│   │       ├── application.properties.example
│   │       └── application.properties
│   └── test/
└── frontend/
    ├── package.json
    ├── src/
    └── vite.config.ts



Pré-requisitos

Instale:

•
JDK 21;

•
IntelliJ IDEA;

•
MySQL ou MariaDB;

•
Node.js e npm;

•
Git.

Verifique no PowerShell:

Plain Text


java -version
javac -version
node -v
npm -v
git --version



Configuração do banco

O projeto pode utilizar MySQL ou MariaDB local. A porta padrão é 3306.

Crie o banco no Workbench, HeidiSQL ou outro cliente SQL:

SQL


CREATE DATABASE IF NOT EXISTS medicenter
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;



Configuração local do backend

Na pasta:

Plain Text


src/main/resources/



copie:

Plain Text


application.properties.example



para:

Plain Text


application.properties



No Windows PowerShell:

Plain Text


Copy-Item src\main\resources\application.properties.example src\main\resources\application.properties



Edite o arquivo local e informe a senha do banco:

Plain Text


spring.datasource.url=jdbc:mysql://localhost:3306/medicenter?serverTimezone=America/Sao_Paulo&allowPublicKeyRetrieval=true&useSSL=false
spring.datasource.username=root
spring.datasource.password=SUA_SENHA_LOCAL
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.open-in-view=false

server.port=8080




O arquivo application.properties com senha real não deve ser enviado ao GitHub. Apenas application.properties.example deve ser versionado.

Executar o backend pelo IntelliJ

1.
Abra o projeto no IntelliJ.

2.
Aguarde o Maven carregar as dependências.

3.
Confirme o JDK 21.

4.
Confirme que o banco está ligado.

5.
Execute:

Plain Text


com.medicenter.MedicenterApplication



A API estará disponível em:

Plain Text


http://localhost:8080



O início correto é indicado por:

Plain Text


Started MedicenterApplication



As tabelas são criadas/atualizadas pelo Hibernate com:

Plain Text


spring.jpa.hibernate.ddl-auto=update



Executar o frontend

Em outro terminal:

Plain Text


cd frontend
npm install
npm run dev



Acesse:

Plain Text


http://localhost:5173



O frontend consulta a API em:

Plain Text


http://localhost:8080/api



Backend e frontend precisam permanecer rodando ao mesmo tempo.

Endpoints principais

Pacientes

Plain Text


GET    /api/pacientes
GET    /api/pacientes/{id}
POST   /api/pacientes
PUT    /api/pacientes/{id}
DELETE /api/pacientes/{id}



Exemplo:

JSON


{
  "nomeCompleto": "Ana Oliveira",
  "cpf": "987.654.321-00",
  "dataNascimento": "1998-08-15",
  "telefone": "(11 ) 98888-7777",
  "email": "ana.oliveira@example.com"
}



Especialidades

Plain Text


GET    /api/especialidades
GET    /api/especialidades/{id}
POST   /api/especialidades
PUT    /api/especialidades/{id}
DELETE /api/especialidades/{id}



Médicos

Plain Text


GET    /api/medicos
GET    /api/medicos/{id}
POST   /api/medicos
PUT    /api/medicos/{id}
DELETE /api/medicos/{id}



Consultas

Plain Text


GET    /api/consultas
GET    /api/consultas/{id}
POST   /api/consultas
PUT    /api/consultas/{id}
PATCH  /api/consultas/{id}/status
DELETE /api/consultas/{id}



Exemplo:

JSON


{
  "pacienteId": 1,
  "medicoId": 1,
  "dataHoraInicio": "2026-10-20T14:00:00",
  "dataHoraFim": "2026-10-20T14:30:00",
  "motivo": "Check-up"
}



Regras de negócio principais

•
CPF de paciente não pode ser duplicado;

•
CRM de médico não pode ser duplicado;

•
médico inativo não recebe novos agendamentos;

•
paciente inativo não recebe novos agendamentos;

•
horário final deve ser posterior ao inicial;

•
médico não pode ter consultas sobrepostas;

•
paciente não deve ter consultas sobrepostas;

•
consultas canceladas não bloqueiam novos horários;

•
pacientes e médicos são inativados para preservar histórico.

Testar pelo PowerShell

Com o backend rodando:

Plain Text


$body = @{
    nomeCompleto = "Teste Paciente"
    cpf = "111.222.333-44"
    dataNascimento = "1995-04-10"
    telefone = "(11) 90000-0000"
    email = "teste.paciente@example.com"
} | ConvertTo-Json

Invoke-RestMethod `
    -Uri "http://localhost:8080/api/pacientes" `
    -Method Post `
    -ContentType "application/json" `
    -Body $body



Consultar pacientes:

Plain Text


Invoke-RestMethod `
    -Uri "http://localhost:8080/api/pacientes" `
    -Method Get



Consultar diretamente no banco:

SQL


USE medicenter;
SELECT * FROM pacientes;



Testar os conflitos de horário

1.
Crie uma consulta das 14:00 às 14:30.

2.
Tente criar outra consulta do mesmo médico das 14:15 às 14:45.

3.
A API deve rejeitar a segunda consulta com erro de conflito, normalmente HTTP 409.

Segurança atual

A autenticação está em desenvolvimento. Durante os testes locais, os endpoints podem estar liberados temporariamente para facilitar a validação dos CRUDs.

Antes da entrega final, deve ser implementado ou revisado:

•
login;

•
senha com hash BCrypt;

•
autorização por perfil;

•
proteção dos endpoints;

•
configuração segura de CORS;

•
remoção de credenciais do código.

Pendências recomendadas

Prioridade alta

•
confirmar que pom.xml, src/main/java, frontend e database foram enviados para o GitHub;

•
manter somente application.properties.example no repositório;

•
criar formulário de cadastro de paciente no frontend;

•
criar formulário de cadastro de médico no frontend;

•
criar formulário de criação de consulta no frontend;

•
testar conflito de horário;

•
adicionar pelo menos testes automatizados para as regras principais.

Prioridade média

•
criar login visual;

•
implementar autenticação por perfil;

•
adicionar CRUD de funcionários;

•
adicionar usuários e autorização;

•
implementar prontuários;

•
completar diagrama de caso de uso;

•
adicionar documentação de modelo de dados.

Melhorias futuras

•
recuperação de senha;

•
notificações;

•
WhatsApp;

•
convênios;

•
pagamentos;

•
anexos de exames;

•
auditoria avançada;

•
deploy.

Possíveis tarefas para delegar

Pessoa 1 — Frontend

•
criar formulário de pacientes;

•
criar formulário de médicos;

•
criar formulário de especialidades;

•
exibir mensagens de sucesso e erro;

•
configurar atualização automática da lista após cadastro.

Pessoa 2 — Autenticação

•
revisar SecurityConfig;

•
criar endpoint de login;

•
criar DTO de login;

•
configurar senha BCrypt;

•
criar autorização por perfil;

•
documentar usuário de teste sem senha real.

Pessoa 3 — Testes e documentação

•
testar todos os endpoints;

•
criar testes de conflito de horário;

•
criar diagrama de caso de uso;

•
criar schema.sql e seed.sql reproduzíveis;

•
revisar README;

•
verificar se não existem segredos no Git.

Pessoa 4 — Funcionários e prontuários

•
criar CRUD de funcionários;

•
criar entidade e CRUD de prontuários;

•
validar que o prontuário só seja criado para consulta realizada;

•
verificar autorização do médico.

Enviar para o GitHub

Antes, execute na raiz do projeto:

Plain Text


git status
git diff --check



Confirme que estes itens não serão enviados:

Plain Text


src/main/resources/application.properties
.env
.idea/
target/
frontend/node_modules/
frontend/dist/



Depois:

Plain Text


git add .
git commit -m "docs: atualiza README e integra projeto"
git push origin main



Se o repositório remoto ainda não estiver configurado:

Plain Text


git remote -v
git remote add origin https://github.com/maina-gif/medicenter.git
git push -u origin main



Depois atualize a página do GitHub e confira se aparecem:

Plain Text


pom.xml
src/main/java
frontend
database
docs
README.md



Licença e finalidade

Projeto desenvolvido para fins acadêmicos. O sistema não deve ser utilizado em ambiente clínico real sem auditoria, adequação à LGPD, revisão de segurança, backups, monitoramento e validação profissional.

