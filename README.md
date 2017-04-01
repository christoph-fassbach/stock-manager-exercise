# Stock Manager Exercise

The task is to develop a stock management as a Rest-Microservice in your favourite programming language. A stock management maintains the current stock amount for a product. The stock can be increased (refill) or decreased (client buys a product). The stock should be maintained on a per-product basis. If you are quick you can also implement a "reserve product" mechanism, that blocks a product in the stock until the client actually buys it (similar to the seat reservation system for flight tickets). However this is optional. This shouldn't take you longer than 4 hours and you should submit the solution within 3 business days. 

The project should be checked into a public code repository of your choice like Github, Bitbucket or Gitlab. It must contain tests and run out of the box, i.e. should not require manual installation of other components. Use a build system of your choice (e.g. Maven, SBT, Gradle).

## Short manual:


### Building:

mvn clean install 
potentially with -Dmaven.test.skip=true - the tests do delete the "DB" otherwise!

### Running:

mvn spring-boot:run
potentially with -Dmaven.test.skip=true - the tests do delete the "DB" otherwise!

## API:

buy: http://localhost:8080/stock/buy/<product name>/<amount>
refill: http://localhost:8080/stock/refill/<product name>/<amount>
reserve: http://localhost:8080/stock/reserve/<product name>/<amount>
(returns: reservation key)
buy reserved: http://localhost:8080/stock/reserve/<product name>/<reservation key>/<amount>

## Persistence:

stock.json in main folder.
