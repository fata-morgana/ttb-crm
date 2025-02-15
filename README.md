# TTB-CRM
An application to manage customer data using Java21, spring-boot, and MS-SQL server. This application provide RESTful endpoint to enable CRUD operations.

## Prerequisite
- Java 21 or higher
- Maven
- MS-SQL server

## Quick start
- Clone this repository
- Create a table in database with this command
```
create table customers (
   customer_id BIGINT IDENTITY(1,1) PRIMARY KEY,
   firstname NVARCHAR(100) NOT NULL,
   lastname NVARCHAR(100) NOT NULL,
   customer_date DATETIME2 NOT NULL,
   is_vip BIT NOT NULL DEFAULT 0,
   status_code NVARCHAR(50) NOT NULL,
   created_on DATETIME2 NOT NULL DEFAULT SYSDATETIME(),
   modified_on DATETIME2 NOT NULL DEFAULT SYSDATETIME()
)

```
- Run `mvn clean install`
- Run `mvn spring-boot:run`

## Overview
You can access this application via port `8080` and context path `/api/v1/customers`. ie. `http://localhost:8080/api/v1/customers`

# Instructions

- view all customers
`GET http://localhost:9999/api/v1/customers`

- view a specific customer
`GET http://localhost:9999/v1/customers/{customerId}`

- insert a new task
`POST http://localhost:9999/api/v1/customer` This is an example data
```
{
  "firstname": "Barack",
  "lastname": "Obama",
  "statusCode": "Active",
  "isVip": "true",
  "customerDate": "2025-01-07T11:11:11"
}
```

- edit a task
`PUT http://localhost:9999/api/v1/customers/{customerId}` This is an example data
```
{
  "firstname": "Bill",
  "lastname": "Clinton",
  "statusCode": "Active",
  "isVip": "true",
  "customerDate": "2025-02-25T05:15:25"
}
```

- delete a task
`DELETE http://localhost:9999/ap1/v1/customers`.
