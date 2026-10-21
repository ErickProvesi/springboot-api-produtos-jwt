# API REST de Produtos com Spring Boot e JWT

Projeto de estudo desenvolvido em Java com Spring Boot para praticar a criação de APIs REST, persistência de dados com PostgreSQL e autenticação baseada em JWT.

> **Status:** projeto em desenvolvimento, criado para fins de aprendizado.

## Tecnologias

- Java 17
- Spring Boot
- Spring Web MVC
- Spring Security
- JSON Web Token (JWT)
- Spring Data JPA e Hibernate
- PostgreSQL
- Maven
- Thunder Client para testar as requisições HTTP

## Funcionalidades

- Cadastro de usuários.
- Estrutura de autenticação com JWT.
- Proteção dos endpoints com token Bearer.
- Cadastro e consulta de produtos.
- Busca de produto por ID.
- Exclusão de produto por ID.
- Persistência de dados usando PostgreSQL e JPA.
- Tratamento global de exceções para recursos não encontrados e erros internos.

## Endpoints

As rotas de `/auth/**` são públicas. As demais rotas exigem autenticação, conforme a configuração atual do Spring Security.

| Método | Endpoint | Autenticação | Descrição |
| --- | --- | --- | --- |
| `POST` | `/auth/register` | Não | Cadastra um usuário |
| `POST` | `/auth/login` | Não | Realiza o login e retorna o token JWT quando as credenciais são aceitas |
| `GET` | `/api/produtos` | Sim | Lista os produtos |
| `GET` | `/api/produtos/{id}` | Sim | Busca um produto pelo ID |
| `POST` | `/api/produtos` | Sim | Cadastra um produto |
| `DELETE` | `/api/produtos/{id}` | Sim | Exclui um produto pelo ID |
| `GET` | `/api/mensagem` | Sim | Retorna uma mensagem de teste |
| `GET` | `/api/hello` | Sim | Retorna uma mensagem de boas-vindas |

### Exemplos de requisição

#### 1. Registrar usuário

`POST /auth/register`

```json
{
  "username": "usuario_teste",
  "password": "senha_de_teste"
}
```

#### 2. Fazer login

`POST /auth/login`

```json
{
  "username": "usuario_teste",
  "password": "senha_de_teste"
}
```

Copie o token JWT retornado pelo endpoint de login. O formato exato da resposta corresponde à implementação atual do controller.

#### 3. Enviar o token nas rotas protegidas

Nas requisições protegidas, adicione o cabeçalho:

```http
Authorization: Bearer SEU_TOKEN_JWT
```

#### 4. Cadastrar produto

`POST /api/produtos`

Cabeçalhos:

```http
Content-Type: application/json
Authorization: Bearer SEU_TOKEN_JWT
```

Corpo:

```json
{
  "nome": "Teclado mecânico",
  "preco": 199.90
}
```

#### 5. Consultar produtos

`GET /api/produtos`

Envie o cabeçalho `Authorization: Bearer SEU_TOKEN_JWT`. Para consultar um produto específico, use `GET /api/produtos/1`, substituindo `1` pelo ID desejado.

## Como executar localmente

### Pré-requisitos

- JDK 17 ou compatível com a versão configurada no projeto.
- PostgreSQL instalado e em execução.
- Git (para clonar o repositório).

### 1. Criar o banco de dados

No PostgreSQL, crie o banco utilizado pela aplicação:

```sql
CREATE DATABASE meubanco;
```

### 2. Configurar o acesso ao banco

Crie localmente o arquivo `src/main/resources/application.properties`. Esse arquivo contém configurações específicas da sua máquina e **não deve ser enviado ao GitHub com credenciais reais**.

Exemplo de configuração:

```properties
spring.datasource.url=${DB_URL:jdbc:postgresql://localhost:5432/meubanco}
spring.datasource.username=${DB_USERNAME:postgres}
spring.datasource.password=${DB_PASSWORD}
spring.datasource.driver-class-name=org.postgresql.Driver

spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

Defina as variáveis de ambiente com as credenciais do seu PostgreSQL. No PowerShell, por exemplo:

```powershell
$env:DB_URL = "jdbc:postgresql://localhost:5432/meubanco"
$env:DB_USERNAME = "postgres"
$env:DB_PASSWORD = "sua_senha_local"
```

Troque `sua_senha_local` pela senha configurada no seu PostgreSQL. Não coloque credenciais reais neste README nem em arquivos versionados.

### 3. Executar a aplicação

Na raiz do projeto, execute pelo Maven Wrapper:

**Windows / PowerShell**

```powershell
.\mvnw.cmd spring-boot:run
```

**Linux / macOS**

```bash
./mvnw spring-boot:run
```

A aplicação utiliza a porta padrão `8080`, a menos que a configuração local seja alterada.

## Como testar

As requisições podem ser testadas com o Thunder Client no VS Code ou outra ferramenta HTTP:

1. Cadastre um usuário em `POST /auth/register`.
2. Faça login em `POST /auth/login` e obtenha o token JWT.
3. Configure o cabeçalho `Authorization: Bearer SEU_TOKEN_JWT`.
4. Teste as rotas protegidas de produtos.

## Estrutura do projeto

```text
src/main/java/com/example/meu_primeiro_springboot/
├── controller/   # Endpoints REST
├── exceptions/   # Exceções e tratamento global de erros
├── model/        # Entidades JPA
├── repository/   # Acesso aos dados
├── security/     # Configuração de segurança e JWT
└── services/     # Regras de negócio
```

## Próximas melhorias

- Adicionar testes unitários e de integração.
- Documentar os endpoints com OpenAPI/Swagger.
- Adicionar validação dos dados de entrada.
- Usar DTOs para controlar os dados recebidos e retornados pela API.
- Evoluir as operações de produtos com atualização (`PUT`/`PATCH`).

---

Projeto desenvolvido para estudo e evolução prática em desenvolvimento backend com Java.
