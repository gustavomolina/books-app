## Aplicação para acompanhamento de livros

Esta aplicação possui a seguinte stack:
- Base de dados PostgreSQL
- Backend em Java (Spring)
- Frontend em ReactJS

Ao fim do processo descrito abaixo, esta aplicação ficará disponível no endereço: **http://localhost:3000/**

---

### Pré-requisitos

Para rodar este projeto você deve ter em sua máquina instalados: **Docker** e **Docker Compose**.

Instruções para instalar o **Docker** em [Ubuntu](https://docs.docker.com/install/linux/docker-ce/ubuntu/), [Windows](https://docs.docker.com/docker-for-windows/install/), [Mac](https://docs.docker.com/docker-for-mac/install/).

**Docker Compose** Para Ubuntu seguir [essas instruções](https://docs.docker.com/compose/install/).


### Como rodar o projeto?

O projeto pode ser rodado pelos seguintes comandos:

```
$ docker-compose build
```

Em seguida:

```
$ docker-compose up -d
```

Se você quiser parar os docker's containers do projeto, use o seguinte comando:

```
$ docker-compose down
```

---

#### books-database (Base de dados)

A base de dados PostgreSQL possui um único schema com uma única tabela - book

Apos rodar o projeto, a base de dados pode ser acessada com as credenciais:

- Host: *localhost:5432*
- Database: *books*
- User: *books*
- Password: *books*



#### books-app (REST API)

É uma API desenvolvida com a stack Spring (Java) que se conecta com a base de dados e expõe os
endpoints REST para serem consumidos pelo frontend segundo os requisitos funcionais. Portanto, suporta os métodos HTTP
GET, POST, PUT e DELETE.

A lista completa de endpoints pode ser consultada via Swagger pelo link
**http://localhost:8080/api/swagger-ui.html**



#### books-ui (Frontend)

Aplicação Web desenvolvida com ReactJS, consome os endpoints providos pela *books-app*.

Pode ser acessada por meio do link **http://localhost:3000/**

