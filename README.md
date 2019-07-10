# Bank Balance and Dispensing System
A Bank Balance and Dispensing System using Spring Boot

## Running the application

First, run a maven build to ensure all tests pass and create the artifact:

```
$ cd banking-system
$ mvn clean install
```

Once complete, you have two ways to run the application:

### Using Docker

Perform the following command to build a docker image containing the artifact: 

```
$ docker build -t banking-system .
```

Then, you can run the docker image:

```
$ docker run -p 8080:8080 -t banking-system
```

### Using CLI

Run the following command to run the application:

```
$ java -jar target/banking-system-0.0.1-SNAPSHOT.jar
```

The API documentation can then be found at the following URL:


`http://localhost:8080/swagger-ui.html`

## Configurations

Should you want to view the H2 Database console, after running the application, navigate to the
following URL:

`http://localhost:8080/h2-console`

Connect to the database using the following Datasource URL:

`jdbc:h2:mem:mainDb`

