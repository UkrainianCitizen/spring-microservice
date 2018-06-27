# RESTful Spring Boot Microservice with JPA

A Spring Boot web application, which was designed 
to resolve problems of imaginary stakeholder -
tour operator called "Explore California". 
It exposes the Explore California tours as RESTful API using 
both Spring Data REST and Spring MVC.

## Spring Data RESTful API

This API is implemented using Spring Data Repositories.
These are the methods exposed by this API, 
which provide means to interact with packages 
of tours.
+ *CREATE:*

|     **POST/tourPackages/**     |
|:------------------------------:|
| Content-Type: application/json |
+ *READ:*

| **GET/tourPackages** |
|:--------------------:|
+ *UPDATE:*

| **PUT/tourPackages/{id}** |
|:---:|
| **PATCH/tourPackages/{id}** |
| Content-Type: application/json |
+ *DELETE:*

| **DELETE/tourPackages/{id}** 	|
|:----------------------------:	|
## Spring MVC RESTful API
This API is implemented using Spring MVC Web RestController.
These are the methods exposed by this API, 
which provide means to rate the tour, view all ratings,
view the average score and delete rating.
+ *CREATE:*

| **POST/tours/{tourId}/ratings** |
|:-------------------------------:|
| Content-Type: application/json  |
+ *READ:*

|     **GET/tours/{tourId}/ratings**     |
|:--------------------------------------:|
| **GET/tours/{tourId}/ratings/average** |
+ *UPDATE:*

|  **PUT/tours/{tourId}/ratings**  |
|:--------------------------------:|
| **PATCH/tours/{tourId}/ratings** |
|  Content-Type: application/json  |
+ *DELETE:*

| **DELETE/tours/{tourId}/ratings/{customerId}** |
|:----------------------------------------------:|







