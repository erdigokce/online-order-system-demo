# online-order-system-demo
A demo project for online order system built with spring boot

## Get started

First we need JDK 17 is installed. After installation run following command:

```
docker-compose -f docker-compose.yml up
```

This will create a `mysql:8.0.27` container and a `openjdk:17.0.1` container. 

As soon as java container starts, it will try to connect to database. Wait for the mysql to be all ready.

In order to access API documentation follow:

`
http://localhost:8080/api/v1/swagger-ui.html
`

or import Postman collection below:

`
https://github.com/erdigokce/online-order-system-demo/blob/main/OOS%20API.postman_collection.json
`

## Notes

- Token store must be an in-memory datastore. Currently tokens are stored in MySQL.
