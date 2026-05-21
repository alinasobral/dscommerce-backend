# DSCommerce Backend

Backend da aplicação DSCommerce desenvolvido com Java e Spring Boot durante o capítulo de Login e Controle de Acesso do curso Java Spring Professional da DevSuperior.

O projeto consiste em uma API REST completa para gerenciamento de produtos, categorias, usuários e pedidos, incluindo autenticação e autorização com OAuth2 e JWT.

## 🚀 Tecnologias
- Java 17+
- Spring Boot
- Spring Security 
- OAuth2 Authorization Server 
- JWT
- Spring Data JPA
- Hibernate
- H2 Database
- Maven
- Postman (para testes)

## 🧠 Conceitos aplicados
- Desenvolvimento de API REST 
- Arquitetura em camadas 
- Padrão DTO 
- CRUD completo 
- Paginação com Pageable 
- Relacionamentos JPA 
- Many-to-One 
- Many-to-Many 
- Spring Data JPA 
- Consultas personalizadas com JPQL 
- Tratamento de exceções 
- Validação de dados 
- Autenticação com OAuth2 
- Autorização com Spring Security 
- Geração e validação de JWT 
- Controle de acesso por perfil (ROLE_ADMIN / ROLE_CLIENT)
- Injeção de dependência 
- Banco de dados H2 
- Versionamento com Git e GitHub

## 🧩 Funcionalidades modeladas
- Listagem de produtos e categorias 
- Consulta de produto por ID 
- Cadastro, atualização e remoção de produtos 
- Controle de acesso por perfil de usuário 
- Login com geração de token JWT 
- Endpoint do usuário autenticado 
- Registro de pedidos 
- Controle de acesso aos pedidos 
- Tratamento de exceções 
- Validações

## 🔑 Autenticação

O projeto utiliza autenticação com OAuth2 + JWT. Após realizar login, o token JWT deve ser enviado no header das requisições privadas:
```bash
Authorization: Bearer SEU_TOKEN
```

## 📦 Estrutura em camadas

O projeto segue arquitetura em camadas:

- Controllers
- Services
- Repositories
- DTOs
- Entities
- Config
- Exceptions
- Projection

## 🌱 Seeding da base de dados

O banco de dados é automaticamente populado com dados iniciais através do arquivo:

- import.sql

Isso garante que, ao iniciar a aplicação, os dados já estejam disponíveis para teste.

## ⚠️ Tratamento de erros
A API implementa tratamento global de exceções, retornando respostas padronizadas:

- Recurso não encontrado (404 Not Found)
- Dados inválidos (422 Unprocessable Entity)
- Acesso negado (403 Forbidden)
- Usuário não autenticado (401 Unauthorized)
- Violação de integridade do banco de dados 
- Erros de validação de campos

## 🧪 Testes

Os testes podem ser realizados utilizando o Postman ou qualquer ferramenta de requisições HTTP.

## ⚙️ Como executar o projeto
### 📌 Pré-requisitos
- Java 17 ou superior

### ▶️ Rodando a aplicação

- Clonar repositório
  git clone https://github.com/alinasobral/dscommerce-backend.git
- Acessar a pasta do projeto dscommerce-backend
-  No terminal, execute:
```bash
./mvnw spring-boot:run
```

## 🗄️ Acessando o banco H2

Após iniciar o projeto, acesse:

👉 http://localhost:8080/h2-console

🔑 Dados de conexão:
- JDBC URL: jdbc:h2:mem:testdb
- User: sa
- Password: (vazio)

## 📄 Licença

Este projeto está sob a licença MIT.

## 👩🏻‍💻 Autor

Desenvolvido por Alina Sobral