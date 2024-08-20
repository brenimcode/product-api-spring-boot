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

### 1. **Segurança com Spring Security**
  - **Autenticação e Autorização**: Proteja sua API com Spring Security para garantir que apenas usuários autorizados possam acessar e modificar recursos.

  - [ ] **Adicionar dependências do Spring Security**: Inclua as dependências do Spring Security e Security Test no `pom.xml`.
    - Ao adicionar essa dependência, o Spring Security ativa uma camada de autenticação padrão automaticamente. Isso significa que qualquer requisição GET resultará em um erro 401 (Não Autorizado), exigindo que o usuário se autentique.
    - Ao acessar o endpoint `http://localhost:8080/products`, o navegador será redirecionado para `http://localhost:8080/login`, onde uma tela de login padrão será apresentada. O acesso é concedido usando as credenciais fornecidas no terminal (`user` como nome de usuário e uma senha gerada automaticamente).
    - No entanto, o objetivo é substituir essa configuração padrão pelo uso de JWT Tokens e Roles para autenticação e autorização, oferecendo uma solução mais segura e flexível.
   - [ ] Configurar Migrations com Flyway
     - Migrations são como "históricos" de mudanças no banco de dados.
     - Para que servem?: Ajudam a manter o banco de dados atualizado e consistente em todos os ambientes.
     - Cada mudança que você faz no banco (como adicionar uma coluna) é registrada em uma migration. Quando você aplica a migration, o banco de dados é modificado conforme essas mudanças.
    - *Implementar*:
    - Crie pasta `db.migration`, depois crie dentro V{num_de_versao}__{NOME}.sql
    - para cada alteração crie nova migration, com num_de_versao atualizado.
    - Para criar isso, deve criar a classe no java de UserModel.
  - [X] Implementar LOMBOK na classe de produto, para deixar mais consiso a classe.
  - [X] Configurar a classe UserModel.java e suas roles, e as collections.
  - [ ] SecurityConfiguration:
    - Configura a segurança de uma API RESTful com Spring Security.:

    - **Desativa a proteção CSRF:** 
      - CSRF é um tipo de ataque que pode ocorrer em sites. Como estamos trabalhando com uma API RESTful, que geralmente usa tokens para autenticação, essa proteção não é necessária, então ela é desativada.

    - **Configura a aplicação para ser "stateless":** 
        - "Stateless" significa que a API não guarda informações sobre o usuário entre as requisições. Ou seja, cada requisição é independente, sem depender de sessões. Isso é importante para APIs porque torna o sistema mais seguro e fácil de escalar.
        - ***Sem sessões (stateless):*** Cada requisição é tratada de forma isolada, sem armazenar dados do usuário entre as requisições.
    
    - **Define as permissões de acesso:** 
        - O método `authorizeHttpRequests` é usado para configurar as permissões de acesso às rotas da aplicação. No exemplo:
        - Apenas usuários com a função "ADMIN" têm permissão para realizar requisições `POST` no endpoint `/products`.
        - Outras requisições são autenticadas, mas sem restrições específicas de papel.
    - **Constrói o objeto de segurança:** 
      - A configuração final é construída, aplicando todas as regras definidas para segurança e controle de acesso.
 - [ ] **Cria Authentication Controller**:
  
  - **Cria AuthenticationDTO(String login, String password)**:
    - **Motivo:** O `AuthenticationDTO` serve como um Data Transfer Object (DTO), encapsula os dados para autenticar um usuário.

  - **Cria PostMapping"(/login)"**:
    - **Motivo:** O mapeamento POST para o endpoint `/login` é essencial para receber as credenciais do usuário e iniciar o processo de autenticação.
  
  - **Boa prática: Não salva senha no banco de dados como STRING! Faça um hash da senha e depois salve-a**:
    - **Motivo:** Armazenar senhas em texto puro é uma vulnerabilidade crítica. Em vez disso, use um algoritmo de hash, como BCrypt, para proteger as senhas. Isso impede que senhas sejam expostas mesmo que o banco de dados seja comprometido.
    - **Descriptografar para comparar:** Ao autenticar, não é necessário "descriptografar" a senha armazenada. Em vez disso, compare o hash da senha fornecida com o hash armazenado. BCrypt lida automaticamente com essa comparação de forma segura.

  - **Cria o método AuthenticationManager em SecurityConfiguration**:
    - **Motivo:** O `AuthenticationManager` é responsável por realizar a autenticação. Definir esse bean em `SecurityConfiguration` permite que ele seja injetado e usado no `AuthenticationController` para verificar as credenciais do usuário.
  
  - **Cria PostMapping"(/register)"**:
      - **Motivo:** O mapeamento POST para o endpoint `/register` permite que novos usuários se registrem.
      - **Verifica se o usuário já existe antes de registrar**
      - **Criptografa a senha antes de salvar no banco**
      - **Salva o novo usuário no banco de dados**
      - **Retorna a resposta apropriada**
 - [ ] **Configura SecurityConfig novamente**:
     - ...
 - [ ] Instalar JWT para geração de TOKENS.
     - ...





 - [ ] **Testar a segurança usando Postman**: Verifique o funcionamento da autenticação JWT e a proteção dos endpoints usando Postman.

### 2. **Validação de Dados**
   - **DTO Validation**: Adicione anotações de validação nos DTOs para garantir a integridade dos dados.
   - **Manejo de Erros**: Implemente um handler global de exceções para fornecer mensagens de erro úteis.

### 3. **Boas Práticas RESTful**
   - **Status HTTP**: Use códigos de status HTTP apropriados.
   - **Nomenclatura Consistente**: Mantenha nomenclatura consistente para endpoints e recursos.

### 4. **Paginação e Ordenação**
   - **Paginação**: Suporte a paginação para grandes conjuntos de dados.
   - **Ordenação**: Permita ordenação dos resultados.

### 5. **Testes**
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
   - [ ] Criar um README detalhado, incluindo descrição do projeto, funcionalidades, tecnologias utilizadas, instruções de execução, e como contribuir.


