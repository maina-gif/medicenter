# Arquitetura inicial

A aplicação segue uma arquitetura em camadas:

```text
Controller -> Service -> Repository -> MySQL
                  |
                Entity
```

## Responsabilidades

- `entity`: representa as tabelas e relacionamentos do banco.
- `repository`: acessa o banco usando Spring Data JPA.
- `service`: concentrará as regras de negócio.
- `controller`: receberá as requisições HTTP.
- `dto`: controlará os dados de entrada e saída da API.

A regra de conflito de horário deve ficar no service, nunca diretamente no controller.
