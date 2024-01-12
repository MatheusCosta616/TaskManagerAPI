
# Task Manager API

## Descrição
Este é um projeto de API para gerenciamento de tarefas (Task Manager) desenvolvido em Java usando o framework Spring Boot.

## Requisitos
- Java 17
- PostgreSQL (Certifique-se de ter um banco de dados PostgreSQL instalado e configurado)

## Configuração do Banco de Dados
1. Crie um banco de dados PostgreSQL com o nome `task_manager`.
2. Atualize as configurações do banco de dados no arquivo `application.properties` no diretório `src/main/resources`.

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/task_manager
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
```

## Como Executar
Certifique-se de ter o Java 17 instalado e configurado no seu ambiente.

Clone o repositório:
```bash
https://github.com/MatheusCosta616/TaskManagerAPI
```

Navegue até o diretório do projeto:
```bash
cd TaskManagerAPI
```

Execute o projeto usando o Maven:
```bash
./mvnw spring-boot:run
```
Ou, se estiver no Windows:
```bash
mvnw spring-boot:run
```
A aplicação estará disponível em http://localhost:8080.

## Endpoints da API
- POST /users: Cria um novo usuário.
- POST /tasks: Cria uma nova tarefa para um usuário específico.
Para obter detalhes sobre os campos necessários para cada endpoint, consulte o código fonte na pasta `src/main/java`.

## Tecnologias Utilizadas
- Spring Boot
- Spring Data JPA
- Hibernate Validator
- PostgreSQL
- Lombok


desenvolvido por Matheus José de Lima Costa

LINKEDIN: https://www.linkedin.com/in/matheus-costa-b7a46425b/
GITHUB: https://github.com/MatheusCosta616
