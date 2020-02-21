# ExchangeRate
This project is to provide exchange rate for Bitcoin to various 
currencies.

Note: 
- Currently it only supports **USD**. 
The mock client written only returns data for a given currency as of now.
- Using `BigDecimal` as the datatype for Currency, we can use datatypes like
`MonetaryMoney`, `Currency` or implement our own datatype to improve the precision. 

### Public APIs

- GET current exchange rate of bitcoin to USD
- GET historical exchange rates