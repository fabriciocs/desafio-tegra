# Desafio Tegra

Olá Samuel, crei a estrutura do projeto utilizando jhipster, segue o link [https://www.jhipster.tech/documentation-archive/v5.8.1](https://www.jhipster.tech/documentation-archive/v5.8.1).

Para executar o projeto, basta entrar na pasta e digitar:

    ./mvnw -Pprod,swagger spring-boot:run

O projeto usa a porta 8080 do localhost [http://localhost:8080](http://localhost:8080).

##Banco de Dados

O banco de dados utilizado é o MySql.
O Host é localhost
A porta é 3306
o banco é teste

username: root
password: 3EDFVWG4

Para executar o migrate basta executar:

./mvnw liquibase:update

Caso deseje alterar os dados basta acessar o arquivo [src/main/resources/application-prod.yml](src/main/resources/application-prod.yml)
para alterar os dados produção.
Existe também o perfil de DEV [src/main/resources/application-dev.yml](src/main/resources/application-dev.yml)

## Execução

Aproveitei que o Jhipster cria a aplicação cliente e deixei algumas funcionalidades prontas pra facilitar sua avaliação.
é possível acessar os módulos na página inicial.
Os usuários disponíveis são

admin com a senha admin
e user cuja senha é user

O Swagger está configurado e roda no menu

configuração -> documentação da api

## O Uso do docker

Alguns arquivos do docker já estão configurados, podendo ser testados da seguinte forma

    docker-compose -f src/main/docker/mysql.yml up -d

Para para o container, basta executar:

    docker-compose -f src/main/docker/mysql.yml down

Para executar todo o container com docker, execute:

    ./mvnw package -Pprod verify jib:dockerBuild

e então, execute:

    docker-compose -f src/main/docker/app.yml up -d
