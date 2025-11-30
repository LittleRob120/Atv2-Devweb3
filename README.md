# Automanager - Microserviço Spring Boot

Projeto Spring Boot para gerenciamento de clientes, com HATEOAS e atualização seletiva de entidades.

## Estrutura do Projeto

- Aplicação principal: [`com.autobots.automanager.Runner`](atvii-autobots-microservico-spring/automanager/src/main/java/com/autobots/automanager/Runner.java)
- Controller de clientes: [`com.autobots.automanager.controles.ClienteControle`](atvii-autobots-microservico-spring/automanager/src/main/java/com/autobots/automanager/controles/ClienteControle.java)
- Entidades:
  - [`com.autobots.automanager.entidades.Cliente`](atvii-autobots-microservico-spring/automanager/src/main/java/com/autobots/automanager/entidades/Cliente.java)
  - [`com.autobots.automanager.entidades.Documento`](atvii-autobots-microservico-spring/automanager/src/main/java/com/autobots/automanager/entidades/Documento.java)
  - [`com.autobots.automanager.entidades.Endereco`](atvii-autobots-microservico-spring/automanager/src/main/java/com/autobots/automanager/entidades/Endereco.java)
  - [`com.autobots.automanager.entidades.Telefone`](atvii-autobots-microservico-spring/automanager/src/main/java/com/autobots/automanager/entidades/Telefone.java)
- Repositório:
  - [`com.autobots.automanager.repositorios.ClienteRepositorio`](atvii-autobots-microservico-spring/automanager/src/main/java/com/autobots/automanager/repositorios/ClienteRepositorio.java)
- Modelos (HATEOAS, atualizadores e utilitários):
  - [`com.autobots.automanager.modelos.AdicionadorLink`](atvii-autobots-microservico-spring/automanager/src/main/java/com/autobots/automanager/modelos/AdicionadorLink.java)
  - [`com.autobots.automanager.modelos.AdicionadorLinkCliente`](atvii-autobots-microservico-spring/automanager/src/main/java/com/autobots/automanager/modelos/AdicionadorLinkCliente.java)
  - [`com.autobots.automanager.modelos.ClienteAtualizador`](atvii-autobots-microservico-spring/automanager/src/main/java/com/autobots/automanager/modelos/ClienteAtualizador.java)
  - [`com.autobots.automanager.modelos.ClienteSelecionador`](atvii-autobots-microservico-spring/automanager/src/main/java/com/autobots/automanager/modelos/ClienteSelecionador.java)
  - [`com.autobots.automanager.modelos.DocumentoAtualizador`](atvii-autobots-microservico-spring/automanager/src/main/java/com/autobots/automanager/modelos/DocumentoAtualizador.java)
  - [`com.autobots.automanager.modelos.EnderecoAtualizador`](atvii-autobots-microservico-spring/automanager/src/main/java/com/autobots/automanager/modelos/EnderecoAtualizador.java)
  - [`com.autobots.automanager.modelos.StringVerificadorNulo`](atvii-autobots-microservico-spring/automanager/src/main/java/com/autobots/automanager/modelos/StringVerificadorNulo.java)
  - [`com.autobots.automanager.modelos.TelefoneAtualizador`](atvii-autobots-microservico-spring/automanager/src/main/java/com/autobots/automanager/modelos/TelefoneAtualizador.java)
- Configurações:
  - [application.properties](atvii-autobots-microservico-spring/automanager/src/main/resources/application.properties)
  - [pom.xml](atvii-autobots-microservico-spring/automanager/pom.xml)

## Requisitos

- Java 17+
- Maven (usa o wrapper incluso)

## Build

Use o Maven Wrapper:

```sh
./mvnw clean package
# Windows:
mvnw.cmd clean package
```

## Execução

```sh
./mvnw spring-boot:run
# ou
java -jar target/automanager-*.jar
```

Por padrão, a aplicação inicia em $http://localhost:8080$.

## Endpoints

Observando [`com.autobots.automanager.controles.ClienteControle`](atvii-autobots-microservico-spring/automanager/src/main/java/com/autobots/automanager/controles/ClienteControle.java), os endpoints típicos incluem:

- GET clientes (listar)
- GET cliente por id
- POST cliente (criação)
- PUT/PATCH cliente (atualização parcial via atualizadores)
- DELETE cliente

Os links HATEOAS são adicionados por [`com.autobots.automanager.modelos.AdicionadorLinkCliente`](atvii-autobots-microservico-spring/automanager/src/main/java/com/autobots/automanager/modelos/AdicionadorLinkCliente.java).

## Testes

```sh
./mvnw test
```

Testes base em [AutomanagerApplicationTests.java](atvii-autobots-microservico-spring/automanager/src/test/java/com/autobots/automanager/AutomanagerApplicationTests.java).

## Configuração

Edite [application.properties](atvii-autobots-microservico-spring/automanager/src/main/resources/application.properties) para portas, banco de dados, etc.
