# RESTful Spring Boot Microservice

A Spring Boot web application, which was designed 
to resolve problems of imaginary stakeholder -
tour operator called "Explore California". 

## Docker

* Start MySql Container 

``` sh
docker run  --detach --name ec-mysql -p 6604:3306 -e MYSQL_ROOT_PASSWORD=password -e MYSQL_DATABASE=explorecali -e MYSQL_USER=cali_user -e MYSQL_PASSWORD=cali_pass -d mysql
```
* Interact with Database
``` sh
docker run -it --link ec-mysql:mysql --rm mysql sh -c 'exec mysql -h"$MYSQL_PORT_3306_TCP_ADDR" -P"$MYSQL_PORT_3306_TCP_PORT" -uroot -p"$MYSQL_ENV_MYSQL_ROOT_PASSWORD"'
```