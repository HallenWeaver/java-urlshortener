# Projeto Encurtador de URLs

Este é o repositório do projeto de Encurtador de URLs.


## Resumo
Este projeto é uma aplicação Java que converte uma URL qualquer em um formato encurtado, que permite facilitar o compartilhamento de endereços.

## Instalação

Clone o projeto, abra-o com sua IDE de preferência (utilizei o Eclipse) e configure o maven para rodar os comandos abaixo (se já não estiverem configurados):

    clean verify install
Em seguida, execute o projeto. Em caso de utilização do maven por linha de comando, execute o comando acima com o mvn. 
## Gerando o Pacote e Executando
Caso esteja usando linha de comando, gere o pacote com 

    mvn package
E em seguida execute o jar gerado na pasta target. Caso esteja utilizando sua IDE, rode o projeto como uma aplicação Spring Boot.
Por fim, em paralelo, inicialize uma instância de Redis na sua máquina utilizando o comando

    redis-server
Supondo que você tenha o redis instalado na máquina.

## Métodos Disponíveis na API
Todos os métodos abaixo foram testados com o aplicativo [Postman](https://www.postman.com/), porém qualquer outro aplicativo ou linha de comando servem para utilização
### POST /
O método de POST é o que envia a URL a ser convertida, e retorna uma URL encurtada. Basta enviar um objeto JSON da forma:

    {
	    "url": "http://www.minhaurl.com.br/endereco"
    }
### GET /{id}
O método de GET com um id retorna a URL associada àquele ID. Se você fizer uma chamada GET no Postman, ele retornará o conteúdo da página; caso chame a URL no seu navegador, você será redirecionado a ela.

### GET /metrics/{id}
O método de GET metrics é utilizado para retornar a quantidade de chamadas feitas a uma URL em particular; o id utilizado é mesmo ID da URL encurtada.
## Créditos
O projeto foi desenvolvido com base num tutorial escrito por Denim Mazuki, que você pode encontrar [aqui](https://hackernoon.com/url-shortening-service-in-java-spring-boot-and-redis-d2a0f8848a1d).
A maior parte do código base é baseado no passo-a-passo do tutorial; no entanto, a correção de alguns bugs do código original, bem como a implementação do *endpoint* de métricas e a execução do testes unitários foram feitos por conta própria.