# Resiliência: #
No dicionario em sentido figurado esta defindo como "a capacidade de se recobrar facilmente ou se adaptar à má sorte ou às mudanças". Para o mundo do desenvolvimento de software podemos pensar como sendo a capacidade de uma aplicação tem de se recuperar após algum problema.

Quando trabalhavamos em aplicações monoliticas muitos desses problemas eram resolvidos com o tratamento de exceções, mas com a migração dos monolitos para arquiteturas de micresserviços alguns problemas novos surgiram e que não podem ser resolvidos com um tratamento de exceção. Exemplo: Se a maquina onde executa o serviço não estiver disponivel.

Quando falamos em sistema distribuído a gama de problemas que podem ocorrer ao longo de um processamento são bem grande, e quanto mais rapido a aplicação está preparada para lidar com esses problemas mais resiliente ela se torna.

Alguns problemas que podem ocorrer:

 - Serviço está fora
 - Base de dados caiu
 - Fila lotada
 - Lentidão na rede
 - Serviço lento ( se seguir batendo podemos derruba-lo)
 - Computador onde o microserviço está em execução falhou
 - entre outros.... 

Para tornar a arquitetura o mais resiliente possivel, existem diversas estratégias:

 - Escalabilidade de serviços
 - Retry
 - Circuit Breaker
 - Rate Limiter
 - Bulkhead
 - Async

 Aqui não vou detalhar as estratégias sitadas acima, porem nos links abaixo é possivel encontrar informações bem detalhadas sobre elas. Nessas POCs meus testes focaram mais nas estratégias que vamos aplicar na app (Circuit Breaker, Retry, Bulkhead, Rate Limiter).

# Dependencias: #
Ambas as POCS:

 - Java 11
 - Spring 2.0
 - Gradle

resilience4j-demo-lib:

 - io.github.resilience4j
 - spring-boot-starter-actuator
 - micrometer-registry-prometheus

resilience4j-demo-spring:

 - spring-cloud-starter-circuitbreaker-resilience4j
 - spring-cloud-starter-circuitbreaker-spring-retry

# Referências: #
 - [Resilience4j](https://resilience4j.readme.io/)
 - [Resilience](https://www.baeldung.com/resilience4j)
 - [Retry](https://www.baeldung.com/spring-retry)
 - [Microsserviços](https://docs.microsoft.com/pt-br/azure/architecture/guide/architecture-styles/microservices)
 - [Resiliência em microsserviços](https://docs.microsoft.com/pt-br/dotnet/architecture/microservices/architect-microservice-container-applications/resilient-high-availability-microservices)
 - [Padrão circuit breaker](https://docs.microsoft.com/pt-br/azure/architecture/patterns/circuit-breaker)
 - [CircuitBreaker](https://martinfowler.com/bliki/CircuitBreaker.html)
 - [Gateway](https://github.com/Romeh/spring-cloud-gateway-resilience4j)

 # Detalhes: #

 Ambas as POCs são referente a um serviço responsavel por nos entregar uma lista de frutas. 

 Para isso devemos dizer de qual distribuidor queremos as frutas.

 Existem 3 possibilidades:

 - CEASA
 - Mercado Publico
 - Atacadão

 Para cada distribuidor o serviço ira solicitar as frutas que ele tem disponivel, isso poderia ser em uma base dados ou um serviço externo.

 Para os testes os serviço externo não foram implementados apenas o Adapter correspondente de cada distribuidor.

 Quando o algum desses serviços por algum motivo não conseguir me entregar as frutas, um cache de frutas será retornado para o usuario que solicitou.

 Estratégia de cache não foi implantada por que não era o objetivo da POC.
 
 Dos distribuidores disponiblizados a CEASA é o único que esta funcionando, os outros por motivos diferentes não estão em funcionamento.

 Serviços podem se acessados:
  - METHOD: get
  - content-type: application/json
  - http://localhost:9080/frutas/{distribuidor}

 distribuidor:

 - ceasa
 - mercadopublico
 - atacadao

# Obs: #
 - Na POC resilience4j-demo-spring foi implementado somente o Circuit Break.
 - na POC resilience4j-demo-lib foi implementado somente o Circuit Break e o retry.
