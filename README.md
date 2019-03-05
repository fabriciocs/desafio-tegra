# Desafio Tegra

Olá Samuel, crei a estrutura do projeto utilizando jhipster, segue o link [https://www.jhipster.tech/documentation-archive/v5.8.1](https://www.jhipster.tech/documentation-archive/v5.8.1).

Para executar o projeto, basta entrar na pasta e digitar:

    ./mvnw -Pprod clean package

Depois, basta executar o war como a seguir

    java -jar target/*.war

O projeto usa a porta 8080 do localhost [http://localhost:8080](http://localhost:8080).

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
