# Desafio referencial

Para rodar é preciso: Java 17+ e Node 22 com o Angular CLI.

Assumindo que usa windows:

Entre em `backend` e rode `./mvnw spring-boot:run`. Ele sobe na porta 8080. Os testes rodam com `./mvnw test`.

Para rodar o frontend, entre em `frontend`, rode `npm ci` e depois `ng serve`. Abra http://localhost:4200 e envie o arquivo `instrucoes-desafio/leituras.csv`.
