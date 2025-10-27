# Bank Account API

API REST para gestão de clientes, contas correntes, cartões e movimentações, construída com Spring Boot. Endpoints seguem padrão Controller/Service e usam autenticação por token em cabeçalho `token` para operações de escrita.

## 🚀 Deploy

A aplicação está disponível em produção:

**URL:** https://conta-bancaria-api.onrender.com/

**Swagger UI:** https://conta-bancaria-api.onrender.com/swagger-ui/index.html

**Docker Hub:** https://hub.docker.com/r/matheusluciano99/conta-bancaria-api

## Stack
- Java 24
- Spring Boot 3.5.5
- Spring Data JPA
- H2 Database (in-memory)
- MySQL Connector (opcional)
- jBCrypt (hash de senha)
- Springdoc OpenAPI (Swagger)
- Maven Wrapper (`./mvnw`)
- Docker

## Rodando o projeto

### Opção 1: Localmente com Maven
Pré-requisitos: Java 24 (ou um JDK compatível), bash.

```bash
# Compilar
./mvnw compile

# Executar a API (porta 8080)
./mvnw spring-boot:run
```

Se preferir, há atalhos com `just`:
```bash
just compile
just run
```

### Opção 2: Docker

```bash
# 1. Build do JAR
./mvnw clean package -DskipTests

# 2. Build da imagem Docker
docker build -t conta-bancaria-api .

# 3. Executar o container
docker run -p 8080:8080 conta-bancaria-api
```

Ou use a imagem do Docker Hub:
```bash
docker pull matheusluciano99/conta-bancaria-api:latest
docker run -p 8080:8080 matheusluciano99/conta-bancaria-api:latest
```

### Opção 3: Deploy no Docker Hub

Para fazer deploy da sua própria versão:
```bash
./deploy-dockerhub.sh
```

## Autenticação
- Registre um usuário e faça login para obter um token.
- Envie o token nos endpoints protegidos via header `token` (sem Bearer).
- GETs são abertos; POST/PUT/DELETE exigem `token`.

## Fluxo rápido (Postman ou cURL)

**Nota:** Substitua `http://localhost:8080` por `https://conta-bancaria-api.onrender.com` para testar em produção.

1) Registrar usuário
```bash
curl -X POST http://localhost:8080/usuarios \
  -H 'Content-Type: application/json' \
  -d '{"email":"teste@example.com","password":"123456"}'
```

2) Login (recebe um token como texto/UUID)
```bash
curl -X POST http://localhost:8080/usuarios/login \
  -H 'Content-Type: application/json' \
  -d '{"email":"teste@example.com","password":"123456"}'
```
Copie o token retornado (ex.: `5fb2af8e-3b6d-413b-8555-c81dff3631f9`).

3) Criar cliente (protegido)
```bash
curl -X POST http://localhost:8080/clientes \
  -H 'Content-Type: application/json' \
  -H 'token: <SEU_TOKEN_AQUI>' \
  -d '{
    "cpf":"12345678901",
    "nome":"João Silva",
    "dataNascimento":"1990-01-01",
    "salario":3000.0
  }'
```

4) Listar clientes (aberto)
```bash
curl http://localhost:8080/clientes
```

5) Criar conta (protegido)
```bash
curl -X POST http://localhost:8080/contas \
  -H 'Content-Type: application/json' \
  -H 'token: <SEU_TOKEN_AQUI>' \
  -d '{
    "agencia":"001",
    "numero":"12345",
    "saldo":1000.0,
    "limite":500.0,
    "cliente":{
      "cpf":"12345678901",
      "nome":"João Silva",
      "dataNascimento":"1990-01-01",
      "salario":3000.0
    }
  }'
```

6) Saque e depósito (protegidos)
```bash
curl -X POST 'http://localhost:8080/contas/12345/saque?valor=100' -H 'token: <SEU_TOKEN_AQUI>'
curl -X POST 'http://localhost:8080/contas/12345/deposito?valor=200' -H 'token: <SEU_TOKEN_AQUI>'
```

7) Listar movimentações e cartões da conta (abertos)
```bash
curl http://localhost:8080/contas/12345/movimentacoes
curl http://localhost:8080/contas/12345/cartoes
```

8) Cartão (protegido para criar/deletar)
```bash
# Criar cartão
curl -X POST http://localhost:8080/cartoes \
  -H 'Content-Type: application/json' \
  -H 'token: <SEU_TOKEN_AQUI>' \
  -d '{"numeroCartao":"1234567890123456","tipo":"CREDITO","validade":"2026-12-31"}'

# Verificar se ativo (aberto)
curl http://localhost:8080/cartoes/1234567890123456/ativo

# Cancelar
curl -X DELETE http://localhost:8080/cartoes/1234567890123456 -H 'token: <SEU_TOKEN_AQUI>'
```

## Dicas de Postman
- Crie uma variável de ambiente `token` no Postman e use `{{token}}` nos headers.
- Script de Tests (na request de login) para salvar token automaticamente:
```javascript
let tok = null;
try { tok = pm.response.json().token; } catch (e) {}
if (!tok) tok = pm.response.text();
pm.environment.set('token', (tok||'').replace(/"/g,'').trim());
```

## Estrutura dos principais endpoints
- `POST /usuarios` – cadastra usuário
- `POST /usuarios/login` – retorna token (texto)
- `GET /clientes` – lista clientes
- `POST /clientes` – cria cliente (header `token`)
- `PUT /clientes/{cpf}` – edita cliente (header `token`)
- `DELETE /clientes/{cpf}` – remove cliente (header `token`)
- `GET /contas` – lista contas
- `POST /contas` – cria conta (header `token`)
- `POST /contas/{numero}/saque?valor=...` – saque (header `token`)
- `POST /contas/{numero}/deposito?valor=...` – depósito (header `token`)
- `GET /contas/{numero}/movimentacoes` – lista movimentações
- `GET /contas/{numero}/cartoes` – lista cartões
- `POST /cartoes` – cria cartão (header `token`)
- `GET /cartoes/{numeroCartao}` – busca cartão
- `GET /cartoes/{numeroCartao}/ativo` – verifica se está ativo
- `DELETE /cartoes/{numeroCartao}` – cancela (header `token`)

## Recursos Adicionais

### Swagger UI
Documentação interativa da API disponível em:
- Local: http://localhost:8080/swagger-ui/index.html
- Produção: https://conta-bancaria-api.onrender.com/swagger-ui/index.html

### H2 Console
Console do banco de dados H2 (desenvolvimento):
- URL: http://localhost:8080/h2-console
- JDBC URL: `jdbc:h2:mem:testdb`
- Username: `sa`
- Password: (vazio)

## Observações
- Banco de dados H2 em memória – dados se perdem ao reiniciar.
- Senhas são armazenadas com hash BCrypt.
- O token é um UUID mantido em memória e verificado por `UsuarioService.validarToken`.
- A aplicação usa JPA/Hibernate com `ddl-auto=update`.

## Testes
```bash
./mvnw test
```

---
Feito para estudos de Arquitetura de Objetos.
