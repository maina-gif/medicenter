# Guia de integração do Medicenter

Este guia explica como clonar a versão parcial, juntar o projeto completo da outra máquina e estruturar o `pom.xml` e as entidades principais.

## 1. Pré-requisitos na outra máquina

Instale ou confirme:

- JDK 21;
- IntelliJ IDEA;
- MySQL ou MariaDB;
- Node.js e npm, para o frontend;
- Git.

Verifique no PowerShell:

```powershell
java -version
javac -version
node -v
npm -v
git --version
```

O Java deve ser a versão 21.

## 2. Clonar o repositório

Abra o PowerShell em uma pasta de trabalho e execute:

```powershell
cd C:\projetos
git clone https://github.com/maina-gif/medicenter.git
cd medicenter
```

Se o repositório estiver com outro proprietário ou nome, use a URL exibida na página do GitHub.

Abra o arquivo `pom.xml` pelo IntelliJ depois que ele for integrado. Na versão parcial, o `pom.xml` ainda será adicionado a partir da outra máquina.

## 3. Fazer backup da versão completa

Na outra máquina, localize a pasta completa, por exemplo:

```text
Downloads\medicenter\medicenter
```

Antes de misturar os arquivos, faça uma cópia da pasta inteira:

```text
Downloads\medicenter\medicenter-backup
```

Não sobrescreva a cópia de segurança.

## 4. Integrar os arquivos

A meta é combinar os arquivos sem apagar a documentação e o histórico do repositório clonado.

Copie da versão completa para a pasta clonada:

- `pom.xml`;
- `src/main/java`;
- `src/main/resources`, preservando o `application.properties.example`;
- `frontend/`;
- scripts SQL, se existirem;
- classe principal Spring Boot;
- entidades e repositories;
- arquivos de configuração que não contenham senhas.

Não copie para o Git:

```text
src/main/resources/application.properties
.idea/
target/
frontend/node_modules/
.env
```

Se já houver um `README.md` ou `docs/` na pasta completa, compare os arquivos antes de substituir. Preserve o README mais completo e combine as informações.

## 5. Estrutura final esperada

```text
medicenter/
├── pom.xml
├── README.md
├── .gitignore
├── docs/
│   ├── RESUMO.md
│   ├── GITHUB.md
│   ├── GUIA-INTEGRACAO.md
│   ├── requests.http
│   ├── diagrama-casos-de-uso.mmd
│   └── modelo-dados.md
├── database/
│   ├── schema.sql
│   └── seed.sql
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/medicenter/
│   │   │       ├── MedicenterApplication.java
│   │   │       ├── config/
│   │   │       ├── controller/
│   │   │       ├── dto/
│   │   │       ├── entity/
│   │   │       ├── enums/
│   │   │       ├── exception/
│   │   │       ├── repository/
│   │   │       └── service/
│   │   └── resources/
│   │       ├── application.properties.example
│   │       └── application.properties
│   └── test/
└── frontend/
    ├── package.json
    ├── src/
    └── public/
```

O arquivo `application.properties` aparece na estrutura apenas como configuração local. Ele não deve ser versionado.

## 6. Estrutura recomendada do `pom.xml`

A versão completa deve ter um `pom.xml` semelhante a este. Use a versão do Spring Boot que já está funcionando no projeto, se ela for compatível.

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">

    <modelVersion>4.0.0</modelVersion>

    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>3.5.6</version>
        <relativePath/>
    </parent>

    <groupId>com.medicenter</groupId>
    <artifactId>medicenter</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>medicenter</name>
    <description>Sistema de gerenciamento da clínica Medicenter</description>

    <properties>
        <java.version>21</java.version>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-validation</artifactId>
        </dependency>
        <dependency>
            <groupId>com.mysql</groupId>
            <artifactId>mysql-connector-j</artifactId>
            <scope>runtime</scope>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
            <scope>runtime</scope>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>
</project>
```

Depois de colocar o `pom.xml`, no IntelliJ:

1. clique com o botão direito no arquivo;
2. escolha **Add as Maven Project** se essa opção aparecer;
3. abra a aba Maven;
4. clique em **Reload All Maven Projects**;
5. confirme o JDK 21 no Maven JVM e no Project SDK.

## 7. Estrutura das entidades principais

### 7.1 Enum de perfil

Arquivo: `src/main/java/com/medicenter/entity/Perfil.java` ou no pacote `enums`, conforme o padrão escolhido.

```java
public enum Perfil {
    ADMIN,
    MEDICO,
    FUNCIONARIO,
    PACIENTE
}
```

Escolha um único pacote e use-o em todos os imports. Não misture `com.medicenter.entity` com outro pacote para a mesma classe.

### 7.2 Usuário

O usuário representa a conta de acesso. Deve ter, no mínimo:

- `id`;
- `nome`;
- `email` único;
- `senha` ou `senhaHash`;
- `perfil`;
- `ativo`.

Exemplo estrutural:

```java
@Entity
@Table(name = "usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(nullable = false, unique = true, length = 150)
    private String email;

    @Column(nullable = false)
    private String senha;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Perfil perfil;

    @Column(nullable = false)
    private Boolean ativo = true;
}
```

A senha deve ser convertida para hash antes de salvar, quando o Spring Security for implementado.

### 7.3 Especialidade

Campos recomendados:

- `id`;
- `nome` único;
- `descricao`.

### 7.4 Médico

Campos recomendados:

- `id`;
- `nomeCompleto`;
- `crm` único;
- `especialidade` com `@ManyToOne`;
- `telefone`;
- `email`;
- `ativo`.

### 7.5 Paciente

Campos recomendados:

- `id`;
- `nomeCompleto`;
- `cpf` único;
- `dataNascimento`;
- `telefone`;
- `email`;
- `ativo`.

### 7.6 Funcionário

Campos recomendados:

- `id`;
- `nomeCompleto`;
- `matricula` única;
- `cargo`;
- `telefone`;
- `email`;
- `ativo`.

### 7.7 Consulta

Campos recomendados:

- `id`;
- `paciente` com `@ManyToOne`;
- `medico` com `@ManyToOne`;
- `dataHoraInicio`;
- `dataHoraFim`;
- `status`;
- `motivo`;
- `observacoes`.

Status recomendados:

```java
public enum StatusConsulta {
    AGENDADA,
    CONFIRMADA,
    REALIZADA,
    CANCELADA,
    NAO_COMPARECEU
}
```

### 7.8 Prontuário

Campos recomendados:

- `id`;
- `consulta` com relação única;
- `paciente`;
- `medico`;
- `dataAtendimento`;
- `queixaPrincipal`;
- `historico`;
- `diagnostico`;
- `conduta`;
- `prescricao`;
- `observacoes`.

A regra de criação deve verificar se a consulta está com status `REALIZADA`.

## 8. Cuidados de compatibilidade com a API atual

Os services presentes no repositório parcial esperam métodos e propriedades específicos. Ao integrar as entidades, confira:

- `Paciente.getNomeCompleto()`;
- `Paciente.isAtivo()`;
- `Paciente.setAtivo(boolean)`;
- `Medico.getNomeCompleto()`;
- `Medico.isAtivo()`;
- `Medico.getEspecialidade()`;
- `Especialidade.getNome()`;
- `Consulta.getPaciente()`;
- `Consulta.getMedico()`;
- `Consulta.getDataHoraInicio()`;
- `Consulta.getDataHoraFim()`;
- `Consulta.getStatus()`;
- os repositories usados pelos services.

Se os nomes das entidades da outra máquina forem diferentes, ajuste os services ou padronize as entidades. Não mantenha duas versões da mesma classe.

## 9. Configuração local do banco

Copie o exemplo:

```powershell
Copy-Item src\main\resources\application.properties.example src\main\resources\application.properties
```

Edite somente o arquivo local:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/medicenter?serverTimezone=America/Sao_Paulo&allowPublicKeyRetrieval=true&useSSL=false
spring.datasource.username=root
spring.datasource.password=SUA_SENHA_LOCAL
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.hibernate.ddl-auto=update
server.port=8080
```

Crie o banco se necessário:

```sql
CREATE DATABASE IF NOT EXISTS medicenter
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;
```

## 10. Testar o backend

No IntelliJ, execute a classe principal. Ou no PowerShell:

```powershell
.\mvnw.cmd clean spring-boot:run
```

Use `docs/requests.http` para testar especialidade, paciente, médico e consulta. Primeiro crie especialidade, depois paciente e médico, e só então a consulta.

Verifique também o conflito de horário. Uma consulta sobreposta deve retornar `409 Conflict`.

## 11. Integrar o frontend

Copie a pasta `frontend` da outra máquina para a raiz do repositório clonado. Depois:

```powershell
cd frontend
npm install
npm run dev
```

O frontend deve acessar a API em `http://localhost:8080` e rodar normalmente em `http://localhost:5173`.

Não versione `frontend/node_modules` nem `frontend/dist`.

## 12. Validar antes do commit

Na raiz do projeto:

```powershell
git status
git diff --check
.\mvnw.cmd clean test
```

Se ainda não houver testes, o comando deve pelo menos compilar a aplicação. Para o frontend:

```powershell
cd frontend
npm run build
```

Depois confira se nenhum arquivo sensível aparece:

```powershell
git status --short
```

Não deve aparecer:

```text
application.properties
.env
.idea/
target/
frontend/node_modules/
```

## 13. Commit da integração

Volte para a raiz do projeto:

```powershell
cd ..
git add .
git commit -m "feat: integra backend completo e frontend"
git push origin main
```

Depois confira a página do GitHub. O repositório deve conter `pom.xml`, entidades, repositories, frontend e documentação, mas não deve expor senha ou dados reais.
