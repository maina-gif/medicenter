# Medicenter

Sistema acadêmico de gerenciamento de clínica médica.


## Escopo do projeto

O Medicenter deverá gerenciar pacientes, médicos, funcionários, usuários, consultas e prontuários, com autenticação e autorização por perfil.

Perfis planejados:

- `ADMIN`
- `MEDICO`
- `FUNCIONARIO`
- `PACIENTE`

Decisões de negócio já definidas:

- somente o administrador cadastra usuários de acesso;
- o paciente pode cancelar seus próprios agendamentos;
- o paciente pode visualizar o prontuário completo;
- o prontuário só pode ser criado quando a consulta estiver `REALIZADA`;
- médico e paciente não podem ter consultas sobrepostas;
- pacientes e médicos são inativados para preservar o histórico.

## Conteúdo atual

- endpoints de pacientes, médicos, especialidades e consultas;
- services com regras de negócio;
- DTOs de médicos e consultas;
- tratamento de erros HTTP;
- configuração de CORS para `http://localhost:5173`;
- requisições de teste em `docs/requests.http`;
- exemplo de configuração do banco em `src/main/resources/application.properties.example`.

## O que falta integrar

- `pom.xml`;
- classe principal Spring Boot;
- entidades JPA;
- repositories;
- CRUD de funcionários;
- usuários e autenticação;
- autorização por perfil;
- prontuários;
- frontend;
- script SQL final;
- documentação final e diagrama de caso de uso.

O passo a passo para completar o projeto está em [`docs/GUIA-INTEGRACAO.md`](docs/GUIA-INTEGRACAO.md).

## Segurança

Nunca publique `application.properties` com senha real, tokens, chaves de API ou dados reais de pacientes. Use o arquivo `.example` como modelo e mantenha a configuração local fora do Git.

## Documentação existente

- [`docs/RESUMO.md`](docs/RESUMO.md)
- [`docs/GITHUB.md`](docs/GITHUB.md)
- [`docs/GUIA-INTEGRACAO.md`](docs/GUIA-INTEGRACAO.md)
- [`docs/requests.http`](docs/requests.http)
