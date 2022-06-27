# Read Me First
A presente API REST foi desenvolvida a partir das especificações presentes no arquivo:

* Especificação Backend.pdf

# Getting Started

##### Configurando o arquivo movieslist.csv
Para a aplicação realizar a leitura dos dados ao iniciar, deve existir na pasta raiz do projeto o arquivo:

> - movielist.csv

Para ser lido corretamente, o arquivo deve possuir a estrutura de dados definida a seguir

> - year;title;studios;producers;winner

##### Executando a aplicação

Para iniciar a aplicação, você deve rodar o comando abaixo na pasta raiz do projeto no terminal do seu sistema operacional:

`mvnw spring-boot:run`

##### Executando os testes de integração

Para executar os testes de integração da API, execute o comando abaixo:

`mvn clean test`

##### Conhecendo as operações da API

Depois de iniciada a aplicação, você pode ver todas as operações e métodos disponíveis na API no link abaixo:

`http://localhost:8080/swagger-ui/`