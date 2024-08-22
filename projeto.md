# API RESTful com Spring Boot 3 - API de Produtos

## Descrição
Este projeto é uma API RESTful para gerenciamento de produtos, construída usando Spring Boot 3. Ele inclui funcionalidades básicas de CRUD (Create, Read, Update, Delete) e utiliza Spring Data JPA para persistência de dados, Spring Validation para validação de entradas, e Spring HATEOAS para navegação de recursos.

## Tecnologias
- **Spring Boot 3**: Framework para criar e configurar a aplicação.
- **Spring WEB MVC**: Por ser WEB e para estrutura de controle e gerenciamento de endpoints RESTful.
- **Spring Data JPA**: Para acesso e manipulação de dados no banco de dados.
- **Spring Validation**: Para validação de dados.
- **Spring HATEOAS**: Para fornecer links e navegação entre recursos na API.

## Tasks Iniciais:

- [X] Iniciar o projeto com Spring Initializer e baixar o arquivo.
- [X] Estrutura Inicial.
- [X] Conectar ao banco de dados PostgreSQL:
  - [X] Criar um servidor no PostgreSQL.
  - [X] Definir o nome e a senha do servidor (ex.: 123).
  - [X] Configurar o Host name/address: localhost ou IP.
- [X] Modelar e mapear a entidade `ProductModel`:
  - **Criar** a entidade com anotações:
    - `@Entity`: Mapeia a classe para o banco de dados.
    - `@Table(name = "nome_tabela")`: Define o nome da tabela.
    - `implements Serializable`: Permite conversão para bytes.
    - `@Id`: Marca o campo como chave primária.
    - `@GeneratedValue(strategy = GenerationType.AUTO)`: Geração automática de IDs.
    - `UUID`:
      - **Unicidade Garantida**: Identificador global único.
      - **Independência**: Geração de IDs sem banco de dados.
      - **Escalabilidade**: Ideal para sistemas distribuídos.
- [X] Criar `ProductRepository`:
  - **Objetivos**:
    - **Abstrair** acesso a dados.
    - **Organizar** persistência.
  - **Estender `JpaRepository<Entidade, Chave primaria>`**:
    - **Métodos CRUD Predefinidos**.
    - **Consultas Personalizadas**.
    - **Integração com Spring Data JPA**.
    - **Gerenciamento de Transações**.
  - **Anotação `@Repository`** (opcional):
    - **Classe como Repositório**.
    - **Tratamento de Exceções**.
- [X] Criar `RestController`:
  - **Responsabilidades**:
    - Gerenciar requisições HTTP: GET, POST, PUT, DELETE.
    - Processar e retornar respostas HTTP.
    - Manipular JSON.
    - Usar `@RestController`.
  - **Injeção de Dependência**:
    - `@Autowired`: Injeta automaticamente `ProductRepository`.
- [X] Criar `DTO`:
  - **Imutabilidade**.
  - **Sintaxe concisa** (getters, hash, equals, construtor).
- [X] Método POST:
  - **Retorno**: `ResponseEntity<ProductModel>` para resposta HTTP e corpo com `ProductModel`.
  - **Anotação**: `@PostMapping("/products")` cria endpoint POST para `/products`.
  - **Parâmetro**: `@RequestBody` para converter JSON em `ProductRecordDto`.
  - **Validação**: `@Valid` valida `productRecordDto`.
  - **Conversão**: `BeanUtils.copyProperties` copia propriedades entre objetos.
- [X] Método GET:
  - **Parâmetro**: `@PathVariable(value ="id")` para obter ID da URL.
- [X] Método DELETE:
  - **Adicionar** método DELETE.
- [X] Adicionar HATEOAS e criar hipermídia:
  - **Integrar** HATEOAS para links e navegação.


---
# Tasks futuras:
### Documentação de Tasks - Projeto Spring Security

### 1. **Segurança com Spring Security**

- **Objetivo**: Proteger a API utilizando Spring Security para assegurar que apenas usuários autenticados e autorizados possam acessar e modificar recursos.

---

#### [X] **Adicionar dependências do Spring Security**
   - **Descrição**: Incluímos as dependências de Spring Security e Security Test no `pom.xml`.
   - **Motivo**: Ao adicionar estas dependências, o Spring Security ativa uma camada de autenticação padrão, resultando em erros 401 (Não Autorizado) para requisições não autenticadas. Esta configuração será substituída pelo uso de JWT Tokens para uma autenticação mais segura e flexível.

---

#### [X] **Configurar Migrations com Flyway**
   - **Descrição**: Implementação de migrations para controlar o histórico de alterações no banco de dados.
   - **Motivo**: Manter o banco de dados atualizado e consistente em todos os ambientes, facilitando a aplicação de alterações no schema.
   - **Implementação**:
     - Criada a pasta `db.migration`.
     - Criados arquivos SQL para cada versão e alteração do banco.
     - Exemplo: Adição da classe `UserModel` no Java para refletir as mudanças no banco.

---

#### [X] **Implementar Lombok**
   - **Descrição**: Implementação do Lombok na classe de produto e user.
   - **Motivo**: Tornar o código mais conciso, reduzindo boilerplate e aumentando a legibilidade.

---

#### [X] **Configurar a Classe `UserModel`**
   - **Descrição**: Configuração da classe `UserModel`, incluindo roles e collections.
   - **Motivo**: Definir a estrutura de usuários e suas permissões para a aplicação.

---

#### [X] **Configurar `SecurityConfiguration`**
   - **Descrição**: Configuração de segurança da API com Spring Security.
   - **Detalhes**:
     - **Desativação do CSRF**: Como a API usa tokens, a proteção CSRF é desnecessária.
     - **Stateless**: Configuração para que a API não guarde informações de sessão, tornando-a mais segura e escalável, apenas usando tokens.
     - **Permissões de Acesso**: Configuração de permissões de acesso, como restrição de POST no endpoint `/products` para usuários com a role `ADMIN`.
   - **Motivo**: Garantir uma segurança robusta e adequada ao contexto da aplicação RESTful.

---

#### [X] **Criar `AuthenticationController` e DTOs**
   - **Descrição**: Criação do controlador de autenticação e `AuthenticationDTO`.
   - **Motivo**: Facilitar a autenticação de usuários e encapsular dados de login.

   - **Detalhes**:
     - **PostMapping `/login`**: Recebe credenciais de usuários e inicia o processo de autenticação.
     - **Armazenamento Seguro de Senhas**: Hash das senhas antes de salvar no banco de dados usando BCrypt.
     - **PostMapping `/register`**: Permite o registro de novos usuários, verificando a existência de duplicatas e criptografando a senha.

---

#### [X] **Implementar JWT para Geração de Tokens**
   - **Descrição**: Instalação e configuração de JWT para geração e verificação de tokens.
   - **Motivo**:
     1. **Autenticação Stateless**: Elimina a necessidade de sessões no servidor.
     2. **Segurança**: Tokens podem ser assinados e criptografados, garantindo integridade e confidencialidade.
     3. **Desempenho**: Tokens compactos e auto-suficientes para cada requisição.
     4. **Controle de Acesso**: Simplifica a gestão de permissões e acesso.

---

#### [X] **Configurar `TokenService`**
   - **Descrição**: Implementação de métodos para geração, verificação e expiração de tokens JWT.
   - **Motivo**: Gerenciar tokens de autenticação com segurança e eficiência.

---

#### [X] **Criar `LoginResponseDTO`**
   - **Descrição**: DTO para retornar o token JWT no corpo do `ResponseEntity` após login.
   - **Motivo**: Padronizar e facilitar a resposta da API ao usuário autenticado.

---

#### [X] **Criar `SecurityFilter`**
   - **Descrição**: Implementação de um filtro de segurança para interceptar e validar as requisições que utilizam tokens JWT.
   - **Motivo**: Garantir que apenas requisições autenticadas com tokens válidos possam acessar os recursos da API.

#### [X] **Testar a segurança usando Postman**: Verifique o funcionamento da autenticação JWT e a proteção dos endpoints usando Postman. Corretamente implementado Spring Security, JWT Tokens e Roles.

---

 



### 2. **Validação de Dados**
   - **DTO Validation**: Adicione anotações de validação nos DTOs para garantir a integridade dos dados.
   - **Manejo de Erros**: Implemente um handler global de exceções para fornecer mensagens de erro úteis.

### 3. **Boas Práticas RESTful**
   - **Status HTTP**: Use códigos de status HTTP apropriados.
   - **Nomenclatura Consistente**: Mantenha nomenclatura consistente para endpoints e recursos.

### 4. **Paginação e Ordenação Para os Produtos**
   - **Paginação**: Suporte a paginação para grandes conjuntos de dados.
   - **Ordenação**: Permita ordenação dos resultados.

### 5. **Testes com JUnit**
   - **Testes Unitários**: Escreva testes para serviços e repositórios.
   - **Testes de Integração**: Verifique a interação entre componentes.

### 6. **Documentação da API**
   - **Swagger/OpenAPI**: Adicione documentação interativa com Swagger ou OpenAPI.

### 7. **Tratamento de Erros**
   - **Mensagens de Erro**: Forneça mensagens de erro claras e consistentes.

### 8. **Cache**
   - **Caching**: Implemente caching para melhorar a performance da API.

### 9. **Performance**
   - **Profiling**: Use ferramentas de profiling para identificar problemas de desempenho.
   - **Async**: Considere métodos assíncronos para operações em segundo plano.

### 10. **Configuração de Ambiente**
   - **Profiles**: Use profiles do Spring para separar configurações de desenvolvimento, teste e produção.

### 11. **Melhorias no DTO**
   - **DTOs e Projeções**: Use DTOs ou projeções para otimizar as respostas da API.

### 12. **Logging**
   - **Logging**: Adicione logging detalhado para monitorar e depurar a API.

### 13. **Deploy**
   - **Deploy em Plataforma Gratuita**: Realize o deploy do backend em uma plataforma gratuita, como Heroku ou Railway.
   - [ ] Configurar variáveis de ambiente e banco de dados na plataforma escolhida.
   - [ ] Testar a API em ambiente de produção.

### 14. **Criação do README**
   - [X] Criar um README detalhado, incluindo descrição do projeto, funcionalidades, tecnologias utilizadas, instruções de execução, e como contribuir.
