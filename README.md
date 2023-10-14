# Catalog Service
This application is part of the Polar Bookshop system and provides the functionality for managing
the books in the bookshop catalog. It's part of the project built in the
[Cloud Native Spring in Action](https://www.manning.com/books/cloud-native-spring-in-action) book
by [Thomas Vitale](https://www.thomasvitale.com).

## REST API
| Endpoint      | HTTP method | Request body | Status | Response body | Description                               |
|---------------|-------------|--------------|--------|---------------|-------------------------------------------|
| /books        | GET         |              | 200    | Book[]        | Get all the books in the catalog.         |
| /books        | POST        | Book         | 201    | Book          | Add a new book to the catalog.            |
|               |             |              | 422    |               | A book with the same ISBN already exists. |
| /books/{isbn} | GET         |              | 200    | Book          | Get the book with the given ISBN.         |
|               |             |              | 404    |               | No book with the given ISBN exists.       |
| /books/{isbn} | PUT         | Book         | 200    | Book          | Update the book with the given ISBN.      |
|               |             |              | 201    | Book          | Create a book with the given ISBN.        |
| /books/{isbn} | DELETE      |              | 204    |               | Delete the book with the given ISBN.      |

## Useful Commands

| Maven Command                    | Description                                   |
|:---------------------------------|:----------------------------------------------|
| `./mvnw spring-boot:run`         | Run the application.                          |
| `./mvnw install`                 | Build the application.                        |
| `./mvnw test`                    | Run tests.                                    |
| `./mvnw spring-boot:repackage`   | Package the application as a JAR.             |
| `./mvnw spring-boot:build-image` | Package the application as a container image. |

After building the application, you can also run it from the Java CLI:

```bash
java -jar .\target\catalog-service-0.0.1-SNAPSHOT.jar
```

## Container tasks

Run Catalog Service as a container

```bash
docker run --rm --name catalog-service -p 8080:8080 catalog-service:0.0.1-SNAPSHOT
```

### Container Commands

|         Docker Command	         |  Description      |
|:-------------------------------:|:-----------------:|
|  `docker stop catalog-service`  |  Stop container.  |
| `docker start catalog-service`  | Start container.  |
| `docker remove catalog-service` | Remove container. |

## Kubernetes tasks

### Create Deployment for application container

```bash
kubectl create deployment catalog-service --image=catalog-service:0.0.1-SNAPSHOT
```

### Create Service for application Deployment

```bash
kubectl expose deployment catalog-service --name=catalog-service --port=8080
```

### Port forwarding from localhost to Kubernetes cluster

```bash
kubectl port-forward service/catalog-service 8000:8080
```

### Delete Deployment for application container

```bash
kubectl delete deployment catalog-service
```

### Delete Service for application container

```bash
kubectl delete service catalog-service
```

## Create a Network
```bash
docker network create catalog-network
```

## Running a PostgreSQL Database

Run PostgreSQL as a Docker container

```bash
docker run -d --rm \
--name polar-postgres \
--net catalog-network \
-e POSTGRES_USER=user \
-e POSTGRES_PASSWORD=password \
-e POSTGRES_DB=polardb_catalog \
-p 5432:5432 \
postgres:16
```

### Container Commands

| Docker Command	                |    Description    |
|:-------------------------------|:-----------------:|
| `docker stop polar-postgres`   |  Stop container.  |
| `docker start polar-postgres`  | Start container.  |
| `docker remove polar-postgres` | Remove container. |

### Database Commands

Start an interactive PSQL console:

```bash
docker exec -it polar-postgres psql -U user -d polardb_catalog
```

| PSQL Command	              | Description                                    |
|:---------------------------|:-----------------------------------------------|
| `\list`                    | List all databases.                            |
| `\connect polardb_catalog` | Connect to specific database.                  |
| `\dt`                      | List all tables.                               |
| `\d book`                  | Show the `book` table schema.                  |
| `\d flyway_schema_history` | Show the `flyway_schema_history` table schema. |
| `\quit`                    | Quit interactive psql console.                 |

From within the PSQL console, you can also fetch all the data stored in the `book` table.

```bash
select * from book;
```

The following query is to fetch all the data stored in the `flyway_schema_history` table.

```bash
select * from flyway_schema_history;
```


### Run catalog-service as a Docker container
```bash
docker container run -d --rm \
--name catalog-service \
--net catalog-network \
-p 9001:9001 \
-e SPRING_DATASOURCE_URL=jdbc:postgresql://polar-postgres:5432/polardb_catalog \
-e SPRING_PROFILES_ACTIVE=testdata \
catalog-service
```

### Stop Containers
```bash
docker rm -f catalog-service polar-postgres
```

### Remove network
```bash
docker network rm catalog-network
```