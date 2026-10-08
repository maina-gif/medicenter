-- Dados ficticios para teste manual.
-- A senha do administrador abaixo e um hash BCrypt da senha temporaria "admin123".
INSERT INTO usuarios (nome, email, senha, perfil, ativo)
SELECT 'Administrador Medicenter', 'admin@medicenter.local', '$2a$10$fTOwvlvvN8nKFDoDjlyndOnYXift.9LIBDj5rs2q5Oi9byA75n2Zq', 'ADMIN', TRUE
WHERE NOT EXISTS (SELECT 1 FROM usuarios WHERE email = 'admin@medicenter.local');

INSERT INTO funcionarios (nome_completo, matricula, cargo, telefone, email, ativo)
SELECT 'Funcionario de Teste', 'FUNC-001', 'Atendimento', '11999999999', 'funcionario@medicenter.local', TRUE
WHERE NOT EXISTS (SELECT 1 FROM funcionarios WHERE matricula = 'FUNC-001');
