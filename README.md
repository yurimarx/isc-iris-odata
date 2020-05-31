# InterSystems IRIS OData Server
##### OData Server for InterSystems IRIS SQL Repositories


- This is an OData Web Server to consume any Intersystems IRIS %Persistent class as an OData REST Service.
- OData is an open source market pattern to expose data as REST Service without programming.
- OData specification v4 is mantained by OASIS in: https://www.odata.org/.
- This product uses: Apache Olingo (to implement OData v4), Spring Boot to run as a microservice.


## Clone the project
- Clone from: https://github.com/yurimarx/isc-iris-odata.git


## Build and run
#### Docker alternative
##### After clone this repository go to root path and execute following instruction:
- docker build -t odata:1.0.0 .
- docker run -p 8080:8080 odata:1.0.0

#### Maven alternative
##### After clone this repository go to root path and execute following instruction:
- mvnw spring-boot:run


## Config Intersystems IRIS Connection
- Access http://localhost:8080/
- Set database parameters and submit
- Stop and start again the docker instance or spring application to run with new parameters

## Use OData with Intersystems IRIS connection
- Access http://localhost:8080/odata.svc/ to list entities (only persistent entities will be presented)
- To list entity data access http://localhost:8080/odata.svc/EntityName
- To detail entity data access http://localhost:8080/odata.svc/EntityName(1)
