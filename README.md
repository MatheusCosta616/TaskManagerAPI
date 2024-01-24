## Task Manager API v3

## Descrição
Este é um projeto de API para gerenciamento de tarefas (Task Manager) desenvolvido em Java usando o framework Spring Boot.

## Requisitos
- **Java 17**
- **PostgreSQL** (Certifique-se de ter um banco de dados PostgreSQL instalado e configurado)
- **PostgreSQL Admin 4** (pgAdmin4)
- **JDK 17**
- **Maven**

## Configuração do Banco de Dados
1. Crie um banco de dados PostgreSQL com o nome `task_manager`.
2. Atualize as configurações do banco de dados no arquivo `application.properties` no diretório `src/main/resources`.

## properties
spring.datasource.url=jdbc:postgresql://localhost:5432/task_manager
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha


1. Execute o seguinte comando no seu banco de dados PostgreSQL para ajustar o tipo da coluna id_task na tabela tb_task:

ALTER TABLE tb_task ALTER COLUMN id_task TYPE uuid USING id_task::uuid;

## Como Executar

Certifique-se de ter o Java 17 instalado e configurado no seu ambiente.

Clone o repositório:

https://github.com/MatheusCosta616/TaskManagerAPI

Navegue até o diretório do projeto:

cd TaskManagerAPI

Execute o projeto usando o Maven:

./mvnw spring-boot:run

Ou, se estiver no Windows:

mvnw spring-boot:run

A aplicação estará disponível em http://localhost:8080.


## NECESSÁRIO TER POSTMAN OU INSOMNIA INSTALADO PARA EXECUTAR OS COMANDOS

## COMO UTILIZAR

** UTILIZAR OS SEGUINTES COMANDOS (ENDPOINTS):

Para criar um usuário (POST) no banco de dados: http://localhost:8080/taskmanager-api/user

precisa enviar um JSON contendo as informações do exemplo:

{
  "userName": "Lukmat",
  "userPass": 123456,
  "userEmail": "Lukmat@gmail.com",
  "tasks": []
}


Para realizar o GET do usuário utilize o link: http://localhost:8080/taskmanager-api/user

Caso queira realizar o GET de apenas um usuário utilze o comando: http://localhost:8080/taskmanager-api/user/(id do usuário)

Para realizar alguma edição com o método PUT basta utilizar o comando: http://localhost:8080/taskmanager-api/user/(id do usuário)
e realizar sua edição via JSON

Já para deletar esse usuário, método DELETE, utilize da requisção http://localhost:8080/taskmanager-api/user/(id do usuário)

Para criar uma tarefa (POST) utilize o seguinte comando: http://localhost:8080/taskmanager-api/task/{id do usuário)

abaixo está um exemplo de JSON de tarefa:

{
  "taskName": "Nome da Tarefa",
  "begunDate": "2024-01-19", 
  "finishDate": "2024-01-25", // Data de término no formato "yyyy-MM-dd"
  "status": "PENDENTE", // Pode ser PENDENTE, EM_ANDAMENTO ou FINALIZADO
  "priority": "ALTA", // Pode ser BAIXA, MEDIA, ALTA ou URGENTE
  "category": "Categoria da Tarefa",
  "user": (id do usuário que receberá esta tarefa)
}


Caso você queira o retorno de todas tarefas cadastradas utilize o comando (GET): http://localhost:8080/taskmanager-api/task

Caso você queira o retorno de apenas uma tarefa utilize o comando (GET): http://localhost:8080/taskmanager-api/task/(id da tarefa)

Para editar a tarefa basta utilizar a requisção PUT com a URL http://localhost:8080/taskmanager-api/task/(id da tarefa) e fazer suas alterações

Já para deletar essa tarefa utilize o método DELETE com a URL http://localhost:8080/taskmanager-api/task/(id da tarefa)


## Tecnologias Utilizadas

* Spring Boot
* Spring Data JPA
* Hibernate Validator
* PostgreSQL
* Lombok


## Contato
Desenvolvido por Matheus José de Lima Costa

* LinkedIn: https://www.linkedin.com/in/matheus-costa-b7a46425b/
* GitHub: https://www.linkedin.com/in/matheus-costa-b7a46425b/

## LEMBRE-SE DE UTILIZAR O PGADMIN4 PARA UMA MELHOR EXPERIÊNCIA NO BANCO DE DADOS E UTILIZE O COMANDO ABAIXO PARA UM MELHOR FUNCIONAMENTO:

ALTER TABLE tb_task ALTER COLUMN id_task TYPE uuid USING id_task::uuid;


## O COMANDO DEVERÁ SER REALIZADO NO BANCO DE DADOS


](https://github.com/MatheusCosta616/TaskManagerAPI)https://github.com/MatheusCosta616/TaskManagerAPI
