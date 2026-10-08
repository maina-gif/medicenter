# Implementação adicionada

Esta versão adiciona as entidades `Funcionario`, `Usuario`, `Prontuario`, os enums `Perfil`, repositories, DTOs, services, controllers e autenticação com Spring Security HTTP Basic.

## Autenticação

O endpoint `POST /api/auth/login` valida e-mail e senha. As demais rotas exigem autenticação HTTP Basic. O administrador pode criar usuários em `/api/usuarios`; a senha é armazenada com BCrypt.

Para teste local, o `database/seed.sql` inclui um administrador fictício com a senha temporária documentada no próprio arquivo. Troque essa senha antes de qualquer uso fora do ambiente acadêmico.

## Prontuários

Um prontuário só pode ser criado quando a consulta estiver com status `REALIZADA`, e cada consulta pode possuir no máximo um prontuário.

## Observação

JWT pode ser adotado em uma etapa futura para uma experiência melhor no frontend. A implementação atual usa HTTP Basic para manter a primeira versão simples e didática.
