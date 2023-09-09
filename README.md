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

| Docker Command	              | Description       |
|:-------------------------------:|:-----------------:|
| `docker stop catalog-service`   | Stop container.   |
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