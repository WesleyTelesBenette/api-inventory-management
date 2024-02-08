# API de Gerenciamento de Estoque
Um sistema Backend para manipulação de um estoque de produtos (essencialmente de um mercado).

## 🍃 Tecnologias utilizadas
Para criar está solução, foi utilizado as   seguintes tecnologias:
- Java (v.17.0.10)
- Maven (v.3.9.6)

O que deixa implícito que você necessariamente precisa ter essas tecnologias instaladas na sua máquina, caso sua intenção seja executar esse projeto no seu computador

## 🏬 Padrão de Arquitetura
Para a criação da API foi escolhido a [**arquitetura monolítica**](https://microservices.io/patterns/monolithic.html), devido ao sistema não possuir uma grande complexidade ou um alto volume de funcionalidades, o que torna preferível uma arquitetura mais simples e concisa.

<img src="https://github.com/WesleyTelesBenette/my-sources-for-docs/blob/main/api-inventory-management/arquitetura.svg" width="600" />

## 🗃️ Modelagem do Banco de Dados
O banco de dados foi construído com [**PostgreSQL**](https://www.postgresql.org/), não por características exclusivas da solução, mas de fato pelo meu servidor de hospedagem só comportar essa tecnologia, o que também não quer dizer que não seja escolha adequada, o PostgreSQL possui recursos mais do que suficientes para um sistema como essa API.

Numa visão geral, essa base de dados é composta por 3 tabelas: **Produtos**, **Categorias** e **Promoções**. E baseado nelas, a API consegue realizar todas suas funções de gerenciamento de estoque.

### DER
<img src="https://github.com/WesleyTelesBenette/my-sources-for-docs/blob/main/api-inventory-management/der.svg" width="600" />

Ps. Foi analizado e decidido que, no contexto específico dessa aplicação, um [**MER**](https://rfcosta85.medium.com/descobrindo-a-modelagem-de-dados-as-principais-caracter%C3%ADsticas-do-modelo-entidade-relacionamento-4d9eec586334) seria irrelevante, o que resultou apenas no desenvolvimento do [**DER**](https://medium.com/@qwejklsd3/desenvolvimento-de-um-diagrama-entidade-relacionamento-para-controle-de-empr%C3%A9stimo-de-livros-em-uma-a3d58851303a).

## ✉️ Comunicação com a API
### URL para Acessar a API
- "https://api-inventory-management.onrender.com".

### Produtos
- GET: "/product" - Todos os produtos.
- GET: "/product/{id}" - Um produto buscado por ID.
- POST: "/product" - Criar um produto com body de [ProductCreateDto](https://github.com/WesleyTelesBenette/api-inventory-management/blob/master/src/main/java/com/wesleytelesbenette/apiinventorymanagement/dtos/ProductCreateDto.java).
- PUT: "/product" - Atualizar um produto com body de [ProductUpdateDto](https://github.com/WesleyTelesBenette/api-inventory-management/blob/master/src/main/java/com/wesleytelesbenette/apiinventorymanagement/dtos/ProductUpdateDto.java).
- DELETE: "/product/{id}" - Deletar um produto por ID.

### Categorias
- GET: "/category" - Todas as categorias.
- GET: "/category/{id}" - Uma categoria buscada por ID.
- POST: "/category/{newCategory}" - Criar uma categoria passando o nome dela.
- PUT: "/category" - Atualizar uma categoria com body de [CategoryUpdateDto](https://github.com/WesleyTelesBenette/api-inventory-management/blob/master/src/main/java/com/wesleytelesbenette/apiinventorymanagement/dtos/CategoryUpdateDto.java).
- DELETE: "/category/{id}" - Deletar uma categoria por ID.

### Promoções
- GET: "/promotion" - Todas as promoções.
- GET: "/promotion/promotion/{id}" - Uma promoção buscada por ID.
- GET: "/promotion/percentage/{id}" - A porcentagem de desconto de uma promoção buscada por ID.
- GET: "/promotion/value/{id}" - O preço final calculado do produto de uma promoção buscada por ID.
- POST: "/promotion/{newCategory}" - Criar uma promoção com body de [PromotionCreateDto](https://github.com/WesleyTelesBenette/api-inventory-management/blob/master/src/main/java/com/wesleytelesbenette/apiinventorymanagement/dtos/PromotionCreateDto.java).
- PUT: "/promotion" - Atualizar uma promoção com body de [PromotionUpdateDto](https://github.com/WesleyTelesBenette/api-inventory-management/blob/master/src/main/java/com/wesleytelesbenette/apiinventorymanagement/dtos/PromotionUpdateDto.java).
- DELETE: "/promotion/{id}" - Deletar uma promoção por ID.
