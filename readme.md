# API RESTful | Spring Boot 3 | Product API

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white) ![Spring Boot](https://img.shields.io/badge/spring--boot-%236DB33F.svg?style=for-the-badge&logo=springboot&logoColor=white) ![Maven](https://img.shields.io/badge/ApacheMaven-C71A36?logo=apachemaven&logoColor=white&style=for-the-badge) ![PostgreSQL](https://img.shields.io/badge/postgresql-%23316192.svg?style=for-the-badge&logo=postgresql&logoColor=white) ![Hibernate](https://img.shields.io/badge/Hibernate-59666C.svg?style=for-the-badge&logo=hibernate&logoColor=white)
![Swagger](https://img.shields.io/badge/Swagger-85EA2D?style=for-the-badge&logo=swagger&logoColor=black) ![JWT](https://img.shields.io/badge/JWT-black?style=for-the-badge&logo=JSON%20web%20tokens) ![JUnit](https://img.shields.io/badge/JUnit-25A162.svg?style=for-the-badge&logo=JUnit&logoColor=white)
![Postman](https://img.shields.io/badge/Postman-FF6C37?style=for-the-badge&logo=postman&logoColor=white)
![Spring Security](https://img.shields.io/badge/Spring%20Security-6DB33F?style=for-the-badge&logo=spring-security&logoColor=white)

## Descrição

Este projeto cria uma API RESTful para o gerenciamento de produtos, utilizando Spring Boot 3 e diversos recursos do Spring Framework. A API tem autenticação baseada em JWT,
e segue boas práticas RESTful. Inclui navegação HATEOAS, validação de dados, e tratamento de erros, além de ser bem documentada. Foi projetada para ser eficiente e escalável, oferecendo suporte a paginação, ordenação e caching.

## Funcionalidades

- **CRUD de Produtos**: Operações para criar, ler, atualizar e deletar produtos.
- **Autenticação e Autorização**: Proteção da API usando Spring Security com JWT Tokens e Roles.
- **Documentação Interativa**: Documentação com Swagger/OpenAPI para facilitar o uso e teste da API.
- **Validação de Dados**: Uso de anotações de validação no DTO para garantir a integridade dos dados.
- **Navegação HATEOAS**: Links para navegação entre recursos.
- **Paginação e Ordenação**: Suporte para paginação e ordenação dos resultados.
- **Caching**: Implementação de caching para melhorar a performance.
- **Testes Automatizados**: Testes unitários e de integração para garantir a qualidade do código.
- **Comprometimento com Padrões RESTful**: Seguir as melhores práticas de design REST, como uso adequado de códigos de status HTTP e URIs bem definidos.
- **Manejo de Erros**: Tratamento de erros centralizado com mensagens de erro claras e consistentes.

## Como Executar o Projeto

1. **Clone o repositório:**
    ```bash
    git clone https://github.com/brenimcode/product-api-spring-boot.git
    ```
2. **Navegue até o diretório do projeto:**
    ```bash
    cd product-api-spring-boot
    ```
3. **Instale as dependências:**
    ```bash
    mvn install
    ```
4. **Configure o banco de dados:**
   - Ajuste as credenciais do banco de dados no arquivo `application.properties`.
5. **Execute a aplicação:**
    ```bash
    mvn spring-boot:run
    ```
6. **Acesse a documentação Swagger:**
   - Acesse `http://localhost:8080/swagger-ui.html` para visualizar e testar os endpoints da API.
