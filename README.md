# stockcheck


Hello, 

A solution for the Comcarde Java Technical Test.

The entire codebase is in SpringBoot with an in memory H2 database used for persistence. It needs a JVM with Java V8 to run and access to download the SpringBoot libraries with maven.

The REST URLs, both GET and POST methods used to run the solution (assuming its run on localhost)

* Get -> Stock Advice for all products  
http://localhost:8085/stockcheck/forAllProducts

* Get -> Stock Advice for a specific product 
http://localhost:8085/stockcheck/forProduct/{productName}

* Get -> Stock Advice for all products to Order
http://localhost:8085/stockcheck/forProductToOrder

* Post -> Mark a specific product as BLOCKED
http://localhost:8085/stockcheck/blockProduct
--> Json payload { "productName" : "<name of the product>" }

* Post -> Set additional Volume for a product
http://localhost:8085/stockcheck/additionalVolumeForProduct
--> Json payload { "productName" : "<name of the product>" , "additionalVolumeToOrder" : "<quantity>" }

* Post -> Set Reorder (minimum stock) Level for a product
http://localhost:8085/stockcheck/additionalVolumeForProduct
--> Json payload { "productName" : "<name of the product>" , "reorderLevel" : "<quantity>" }

Data is persisted to the database, the H2 database is accessible in the URL http://localhost:8085/h2-console with username 'sa' and password 'password'

The Optional requirement to store the audit data of the output of stock-check and recommended purchase history is 
persisted to the database and can viewed from the H2 console. Db tables : stock_check_audit and recommended_purchase_history. 

The H2 database will be Torn down when the JVM exits.
