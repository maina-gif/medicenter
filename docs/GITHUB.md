# Publicar o Medicenter no GitHub

O repositório deve ser criado na pasta que contém o `pom.xml`
(`Downloads\medicenter\medicenter`).

1. **Proteger a senha primeiro.** Confirme que o `.gitignore` contém as linhas de
   `gitignore-sugestao.txt`. Copie `application.properties` para `application.properties.example`
   e troque a senha por `SUA_SENHA_AQUI`.
2. **IntelliJ:** Version Control > Create Git Repository, escolhendo a pasta do `pom.xml`.
3. **Commit (Ctrl+K):** confira a lista. `application.properties`, `target/` e `node_modules/`
   NÃO podem aparecer. Mensagem sugerida: `Primeira versão: API, frontend e schema`.
4. **Publicar:** Version Control > Share Project on GitHub, nome `medicenter`, marcar **Private**.
   Faça login pelo navegador (Log in via GitHub). Nunca cole token em chat ou arquivo.
5. **Verificar no site:** deve existir `application.properties.example` e não `application.properties`.
6. **Equipe:** Settings > Collaborators > Add people (Nicolas, Gabriel e Maina).
