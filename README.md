# salario-service

Microsserviço de **folha de pagamento** — registro de holerites, proventos e descontos por ano.

## Stack

| Item | Versão |
|---|---|
| Java | 21 |
| Spring Boot | 4.1.1 |
| Spring Cloud | 2025.1.3 |

| Porta | Context path | Perfil exigido |
|---|---|---|
| 9009 | `/salario-service/` | `ROLE_SALARIO` |

## Endpoints

### `/folha`

| Método | Rota | Descrição |
|---|---|---|
| `GET` | `/folha` | lista as folhas |
| `POST` | `/folha` | cadastra uma folha |
| `GET` | `/folha/{id}` | busca uma folha com seus itens |
| `GET` | `/folha/ano/{ano}` | folhas de um ano |

### `/ano`

| Método | Rota | Descrição |
|---|---|---|
| `GET` | `/ano` | anos disponíveis |

### `/tipo-folha`

| Método | Rota | Descrição |
|---|---|---|
| `GET` | `/tipo-folha` | lista os tipos de folha |
| `POST` | `/tipo-folha` | cadastra um tipo de folha |
| `GET` | `/tipo-folha/{id}` | busca por id |

### `/tipo-item`

| Método | Rota | Descrição |
|---|---|---|
| `GET` | `/tipo-item/P` | apenas **proventos** |
| `GET` | `/tipo-item/D` | apenas **descontos** |
| `GET` | `/tipo-item/{id}` | busca por id |
| `POST` | `/tipo-item` | cadastra um tipo de item |
| `PUT` | `/tipo-item/{id}` | atualiza |

## Autenticação

As requisições precisam do token JWT emitido pelo `autenticacao-service`, no cabeçalho:

```
Authorization: Bearer <token>
```

O serviço apenas **valida** o token — ele não emite nenhum. A chave de validação vem de `JWTSecret`, em `secret/application` no Vault, e precisa ser a mesma usada pelo emissor.

> **Atenção ao segredo:** desde a migração para o jjwt 0.13, `JWTSecret` precisa ser uma string **Base64** que decodifique para **no mínimo 64 bytes** — exigência do HS512. Gere um com `openssl rand -base64 64`. Se o valor não atender, a aplicação falha no startup com mensagem explícita, em vez de aceitar uma chave fraca em silêncio.

## Banco de dados

PostgreSQL, com schema versionado por **Flyway** (migrations em `src/main/resources/db/migration`):

- `V001__Inicial.sql`
- `V002__add_column_tipo_item.sql`
- `V003__new_table_ano.sql`
- `V004__add_column_itens.sql`

As migrations rodam automaticamente no startup.

> No Spring Boot 4 a autoconfiguração do Flyway passou a viver no módulo `spring-boot-flyway`. Sem essa dependência o Flyway é ignorado **em silêncio** — a aplicação sobe normalmente e nenhuma migration é aplicada. Ela está declarada no `pom.xml`; não remova.

**Entidades:** `Ano`, `Folha`, `Item`, `TipoFolha`, `TipoItem`.

## Configuração

A aplicação não guarda configuração própria: ela busca tudo no arranque, via `spring.config.import`.

| Origem | O que vem de lá |
|---|---|
| **Vault** (`secret/application`) | segredos compartilhados: `JWTSecret`, credenciais de e-mail, AWS, Eureka |
| **Vault** (`secret/<nome-do-serviço>`) | segredos próprios, como as credenciais do banco |
| **Config Server** | `server.port`, `context-path`, datasource e demais propriedades |

### Variável de ambiente obrigatória

| Variável | Para que serve |
|---|---|
| `VAULT_TOKEN` | token de acesso ao Vault |

`VAULT_TOKEN` **não tem valor padrão**. Sem ela, o Spring envia a string literal `${VAULT_TOKEN}` ao Vault, recebe `403` e — como `spring.cloud.vault.fail-fast` vem desligado — o erro só aparece bem depois, disfarçado de placeholder não resolvido (`${...} is malformed`). Se quiser que a falha apareça na hora, ligue `spring.cloud.vault.fail-fast: true`.

Também são necessários `VAULT_HOST`, `VAULT_PORT` e `VAULT_SCHEME` quando o Vault não está em `localhost:8200` via `http`, e `CONFIG_SERVER_USER` / `CONFIG_SERVER_PASS` nos serviços que leem do Config Server.

## Como executar

```bash
# build
./mvnw clean package

# execução
VAULT_TOKEN=<seu-token> java -jar target/salario-service-*.jar --spring.profiles.active=dev
```

> **Dependências no ar:** este serviço só sobe com o **Vault**, o **Config Server** e o **Eureka** disponíveis, além do seu banco PostgreSQL.

A aplicação sobe em `http://localhost:9009/salario-service/`.

### Docker

O `Dockerfile` espera o jar já na raiz do projeto, com o nome `sistema-salario-service.jar`:

```bash
./mvnw clean package
cp target/salario-service-*.jar sistema-salario-service.jar

docker build \
  --build-arg VAULT_HOST=<host> \
  --build-arg VAULT_TOKEN=<token> \
  --build-arg CONFIG_SERVER_USER=<usuario> \
  --build-arg CONFIG_SERVER_PASS=<senha> \
  -t salario-service .
```
