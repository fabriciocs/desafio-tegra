# Desafio Tegra

Olá Samuel, crei a estrutura do projeto utilizando jhipster, segue o link [https://www.jhipster.tech/documentation-archive/v5.8.1](https://www.jhipster.tech/documentation-archive/v5.8.1).

Para executar o projeto, basta entrar na pasta e digitar:

    ./mvnw -Pprod clean package

Depois, basta executar o war como a seguir

    java -jar target/*.war

O projeto usa a porta 8080 do localhost [http://localhost:8080](http://localhost:8080) in your browser.

## Execução

Aproveitei que o Jhipster cria a aplicação cliente e deixei algumas funcionalidades prontas pra facilitar sua avaliação.
é possível acessar os módulos na página inicial.
Os usuários disponíveis são _admin_ com a senha _admin_ e user cuja senha é _user_.

O Swagger está configurado e roda no menu _configuração->documentação da api._

## Testing

Para executar os testes

    ./mvnw clean test

### Análise de Qualidade

Para executar o sonar (analisar a qualidade do código) basta executar

```
docker-compose -f src/main/docker/sonar.yml up -d
```

e logo em seguida

```
./mvnw -Pprod clean test sonar:sonar
```

## O Uso do docker

Alguns arquivos do docker já estão configurados, podendo ser testados da seguinte forma

    docker-compose -f src/main/docker/mysql.yml up -d

Para para o container, basta executar:

    docker-compose -f src/main/docker/mysql.yml down

Para executar todo o container com docker, execute:

    ./mvnw package -Pprod verify jib:dockerBuild

e então, execute:

    docker-compose -f src/main/docker/app.yml up -d

As páginas de referência do JHIPSTER.

[jhipster homepage and latest documentation]: https://www.jhipster.tech
[jhipster 5.8.1 archive]: https://www.jhipster.tech/documentation-archive/v5.8.1
[using jhipster in development]: https://www.jhipster.tech/documentation-archive/v5.8.1/development/
[using docker and docker-compose]: https://www.jhipster.tech/documentation-archive/v5.8.1/docker-compose
[using jhipster in production]: https://www.jhipster.tech/documentation-archive/v5.8.1/production/
[running tests page]: https://www.jhipster.tech/documentation-archive/v5.8.1/running-tests/
[code quality page]: https://www.jhipster.tech/documentation-archive/v5.8.1/code-quality/
[setting up continuous integration]: https://www.jhipster.tech/documentation-archive/v5.8.1/setting-up-ci/
[node.js]: https://nodejs.org/
[yarn]: https://yarnpkg.org/
[webpack]: https://webpack.github.io/
[angular cli]: https://cli.angular.io/
[browsersync]: http://www.browsersync.io/
[jest]: https://facebook.github.io/jest/
[jasmine]: http://jasmine.github.io/2.0/introduction.html
[protractor]: https://angular.github.io/protractor/
[leaflet]: http://leafletjs.com/
[definitelytyped]: http://definitelytyped.org/
[openapi-generator]: https://openapi-generator.tech
[swagger-editor]: http://editor.swagger.io
[doing api-first development]: https://www.jhipster.tech/documentation-archive/v5.8.1/doing-api-first-development/
