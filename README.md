# ExchangeRate
This project is to provide exchange rate for Bitcoin to various 
currencies.

Note: 
- Currently it only supports **USD** and **BITCOIN**. 
The mock com.exchangerate.client written only returns data for a given currency as of now. 
We can also integrate this to some other real com.exchangerate.client to fetch this data.
- Using `BigDecimal` as the datatype for Currency, we can use datatypes like
`MonetaryMoney`, `Currency` or implement our own datatype to improve the precision. 

### Public APIs

- GET current exchange rate of bitcoin to USD
- GET historical exchange rates


##### Assumptions made:
- com.exchangerate.client(external com.exchangerate.service) only provides API to fetch Exchange Rates for a single source and target 
currency and for one given time.

### How to run
1. This project requires MySql8 and Java8. Please make sure it's there in your system.
2. Download this repo and run below commands in the repo directory

    - Run `mvn clean install`
    - Create database named : `exchange-rate`
    - For initial setup, you can use the DB dump in this project `dump.sql`(We can also run the changesets otherwise).
    by running `mysql -u username -p exchange-rate < file.sql`.
    You can find DB details in `exchange-rate`.
    - Finally you can run the server with: `java -jar target/ExchangeRate-1.0-SNAPSHOT.jar server exchangeRate.yml`

### API Documentation
For API Documentation, you can refer to `exchange-rate.postman_collection.json` postman collection in this project.
There are 3 APIs:
1. Get Latest Exchange rate for given source and target currencies.
2. Get Historical Data given a time range and given source and target currencies.
3. Backfill data given a time range and given source and target currencies.

Along with this, there is a job running which refreshes the latest exchange rate value. 
This can be enabled/disabled or you can change it's frequency by updating the config in `exchangeRate.yml`

### DB Modelling

There is currently only one table which stores the exchange rates and it looks like this:



| id | source_currency_code | target_currency_code | value | value_at                   | created_at                 | updated_at                 |
|----|----------------------|----------------------|-------|----------------------------|----------------------------|----------------------------|
|  1 | BITCOIN              | USD                  | 21594 | 2020-02-26 16:23:25.297843 | 2020-02-26 16:23:25.461000 | 2020-02-26 16:23:25.461000 |



We can also add a table to keep a track of backfilling.
