# Resiliência: #
No dicionário, em sentido figurado, está defindo como "a capacidade de se recobrar facilmente ou se adaptar à má sorte ou às mudanças". Para o mundo do desenvolvimento de software podemos pensar como sendo a capacidade que uma aplicação possui de se recuperar após algum problema.

Quando trabalhávamos em aplicações monolíticas, muitos desses problemas eram resolvidos com o tratamento de exceções, porém, com a migração dos monolitos para arquiteturas de microsserviços, alguns problemas novos surgiram e que não podem ser resolvidos com o mesmo tratamento de exceção. Exemplo: quando...ou se a máquina onde se executa o serviço não estiver disponível.

Quando falamos em sistema distribuído, a gama de problemas que podem ocorrer ao longo de um processamento é bem grande, e quanto mais rápido a aplicação estiver preparada para lidar com esses problemas, mais resiliente ela se tornará.

Alguns problemas que podem ocorrer:

 - Serviço está fora
 - Base de dados caiu
 - Fila lotada
 - Lentidão na rede
 - Serviço lento ( se seguir batendo podemos derrubá-lo)
 - Computador onde o microserviço está em execução falhou
 - entre outros.... 

Para tornar a arquitetura o mais resiliente possivel, existem diversas estratégias, tais como:

 - Escalabilidade de serviços
 - Retry
 - Circuit Breaker
 - Rate Limiter
 - Bulkhead
 - Async

 Aqui não vou detalhar as estratégias citadas acima, porém, nos links abaixo é possivel encontrarmos informações bem detalhadas sobre elas. Nessas POCs meus testes focaram mais nas estratégias que iremos aplicar na app (Circuit Breaker, Retry, Bulkhead, Rate Limiter).

# Dependências: #
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

 Ambas as POCs referem-se a um serviço responsável por nos entregar uma lista de frutas. 

 Para isso, devemos dizer de qual distribuidor queremos as frutas.

 Existem 3 possibilidades:

 - CEASA
 - Mercado Público
 - Atacadão

 Para cada distribuidor o serviço irá solicitar as frutas que ele tem disponível, o que poderia ser em uma base dados ou um serviço externo.

 Para estes testes, o serviço do distribuidor externo não foi implementado, apenas o Adapter correspondente de cada distribuidor.

 Quando algum desses serviços, por algum motivo, não conseguir me entregar as frutas, um cache de frutas será retornado para o usuário que o solicitou.

 Estratégia de cache não foi implantada por que não era o objetivo da POC.
 
 Dos distribuidores disponiblizados, a CEASA é o único que está funcionando, os outros, por motivos diferentes, não estão em funcionamento.

 Serviços podem ser acessados:
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
