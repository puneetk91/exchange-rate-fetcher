# ExchangeRate
This project is to provide exchange rate for Bitcoin to various 
currencies.

Note: 
- Currently it only supports **USD** and **BITCOIN**. 
The mock client written only returns data for a given currency as of now. 
We can also integrate this to some other real client to fetch this data.
- Using `BigDecimal` as the datatype for Currency, we can use datatypes like
`MonetaryMoney`, `Currency` or implement our own datatype to improve the precision. 

### Public APIs

- GET current exchange rate of bitcoin to USD
- GET historical exchange rates


##### Assumptions made:
- client(external service) only provides API to fetch Exchange Rates for a single source and target 
currency and for one given time.

### How to run
1. Install MySql and Java
2. Download this repo and run below commands in the repo directory

    - Run `mvn clean install`
    - Create database named : `exchange-rate`
    - Run liquibase migrations to update the database:
`mvn liquibase migrations.xml`
    - Run `java -jar target/ExchangeRate-1.0-SNAPSHOT.jar server src/main/java/config/exchangeRate.yml`

### API Documentation
