# API RESTful com Spring Boot 3 - API de Produtos

## Descrição
Este projeto é uma API RESTful para gerenciamento de produtos, construída usando Spring Boot 3. Ele inclui funcionalidades básicas de CRUD (Create, Read, Update, Delete) e utiliza Spring Data JPA para persistência de dados, Spring Validation para validação de entradas, e Spring HATEOAS para navegação de recursos, usa Spring Security para fazer autenticação, e segurança, controlando o acesso atraves de Tokens gerados, onde só o ADMIN pode adicionar produtos. 


## Tecnologias
- **Spring Boot 3**: Framework para criar e configurar a aplicação.
- **Spring WEB MVC**: Por ser WEB e para estrutura de controle e gerenciamento de endpoints RESTful.
- **Spring Data JPA**: Para acesso e manipulação de dados no banco de dados.
- **Spring Validation**: Para validação de dados.
- **Spring HATEOAS**: Para fornecer links e navegação entre recursos na API.

## Tasklist:

### 1. Estrutura Inicial
- [X] Iniciar o projeto com Spring Initializer e baixar o arquivo.
- [X] Criar a estrutura inicial.

### 2. Conectar ao Banco de Dados PostgreSQL
- [X] Criar um servidor no PostgreSQL.
- [X] Definir nome e senha do servidor (ex.: 123).
- [X] Configurar o Host name/address: localhost ou IP.

### 3. Modelagem da Entidade `ProductModel`
- [X] Criar a entidade com as seguintes anotações:
  - `@Entity`: Mapeia a classe para o banco de dados.
  - `@Table(name = "nome_tabela")`: Define o nome da tabela.
  - `implements Serializable`: Permite conversão para bytes.
  - `@Id`: Define o campo como chave primária.
  - `@GeneratedValue(strategy = GenerationType.AUTO)`: Geração automática de IDs.
  - `UUID`: Geração de IDs globais únicos, ideal para sistemas distribuídos.

### 4. Criar `ProductRepository`
- [X] Estender `JpaRepository<Entidade, ChavePrimaria>`:
  - Métodos CRUD predefinidos.
  - Consultas personalizadas.
  - Integração com Spring Data JPA e gerenciamento de transações.
  - `@Repository` (opcional): Define a classe como repositório e trata exceções.

### 5. Criar `RestController`
- [X] Gerenciar requisições HTTP (GET, POST, PUT, DELETE).
- [X] Injeção de dependência com `@Autowired` para `ProductRepository`.

### 6. Implementação de Métodos na API
- [X] **POST**: 
  - Criar endpoint para cadastrar produtos com `@PostMapping("/products")`.
  - Validar com `@Valid` e utilizar `BeanUtils.copyProperties`.
  - Retorno: `ResponseEntity<ProductModel>`.
- [X] **GET**: 
  - Consultar por ID com `@PathVariable(value = "id")`.
- [X] **DELETE**: 
  - Implementar método para remover produtos.

### 7. Implementar HATEOAS
- [X] Adicionar HATEOAS para hipermídia e navegação entre recursos.

### 8. Segurança com Spring Security
- [X] Adicionar dependências de Spring Security e Security Test no `pom.xml`.
- [X] Configurar Spring Security para autenticação via JWT.
- [X] Implementar `SecurityConfiguration`:
  - Desativar CSRF.
  - Configurar autenticação Stateless com tokens.
  - Definir permissões por role (ex.: `ADMIN` para POST em `/products`).
- [X] Criar `AuthenticationController` e DTOs:
  - Login (`/login`) e registro de usuários (`/register`).
  - Hash de senhas com BCrypt.
- [X] Implementar geração e verificação de JWT Tokens:
  - `TokenService` para gerenciamento de tokens.
  - `LoginResponseDTO` para retornar o token JWT.
  - `SecurityFilter` para validação de tokens nas requisições.
- [X] Testar autenticação e proteção de endpoints com Postman.

### 9. Validação de Dados
- [X] Adicionar validações nos DTOs.
- [X] Implementar handler global para tratamento de erros.

### 10. Configurações de Lógica de Negócio e Tratamento de Erros
- [X] Criar `GlobalExceptionsHandler` e configurar exceções globais.
- [X] Impedir a criação de produtos com nomes duplicados.
- [X] Restrição de acesso: apenas `USER` pode fazer GET de produtos.
- [X] Melhorar o retorno de erros de tokens no corpo da requisição.
- [X] Desativei o Flyway DB

### 11. Testes com JUnit
- [ ] Escrever testes unitários e de integração.
   - **JUnit**: Um framework que facilita o desenvolvimento de testes unitários em aplicações Java. Ele ajuda a garantir que as funcionalidades da aplicação estão funcionando conforme esperado e que novas alterações não introduzam bugs.
   - **Benefícios**: Permite testar isoladamente componentes da aplicação (unitários) e simular o comportamento de sistemas externos e interações de componentes (integração).
   - **Exemplo**: Crie testes para serviços, repositórios e validações de entidades.
   - **Dependências**: Como seu projeto usa Spring Boot, o framework já inclui a dependência `spring-boot-starter-test`, que integra JUnit e outras bibliotecas úteis (Mockito, Hamcrest).
- [ ] Criar o teste para a classe repository, apenas para entender como funciona
  - @DataJpaTest, indica ao srping que é classe que vai testar o RepositoryJPA.
  - Preciso configurar o Banco de dados. Pois, o Repository faz a comunicação com o BANCO, porém esse banco deve ser um banco para testes.
  - Usar para o ambiente de testes Banco H2 Database que é em memória, apenas para simular um banco.

### 12. Documentação da API
- [ ] Adicionar documentação interativa com Swagger

### 13. Diagrama das classes e etc.
- [ ] ...

### 13. Configuração de Ambiente
- [ ] Usar profiles do Spring para separar configurações de desenvolvimento, teste e produção.

### 14. Docker
- [ ] Configurar a aplicação em um docker para padronizar.

### 18. Deploy
- [ ] Realizar deploy do backend em plataforma.
- [ ] Configurar variáveis de ambiente e banco de dados na plataforma.
- [ ] Testar a API em ambiente de produção.

### 19. Fazer um post apresentando a API de Produtos
- [ ] ...






