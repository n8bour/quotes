# Tui Challenge

## Functional Requirements
- [x] The app should be able to return a specific item based on an ID.
- [x] The app should be able to return a list of items by author.
- [x] The app should be able to return all items in that collection.

## Non Functional Requirements
- [x] The app must be able to support 50 requests per second in up to 200 milliseconds for the search functionality by ID
- [ ] The app must be able to support 50 requests per second in up to 200 milliseconds for the search functionality by author
- [x] The app must be able to answer in less than 30 seconds for the functionality to search all items in this collection.

## Running the application
> **NOTE:**
> Make sure you are in the root project folder before running the desired test script

Generate docker image
``` 
gradlew dockerBuild
```

Running the app
```
docker-compose -f docker/docker-compose.yml up -d
```
### Adding index for faster response time on find by author
Make sure you run the following sequence of commands:
```bash
    docker exec -i -t mongodb bash
    mongosh 
    use challenge && db.quotes.createIndex({quoteAuthor: 1})
```

#### Endpoints available
- http://localhost:8080/hello
- http://localhost:8080/quotes all quotes
- http://localhost:8080/quotes/pageable?size=10&page=1&sort=quoteAuthor,ASC all quotes with pagination
- http://localhost:8080/quotes/{id} fetch by id
- http://localhost:8080/quotes/authors/{author} fetch by author

## Running unit tests
``` 
gradlew test
```

## Load Tests
Load test were done by k6, we have a `k6` folder with the test scripts, and to run the tests we just use the following
command:
`k6 run ./k6/scriptName.js`
> **NOTE:**
> Make sure you are in the root project folder before running the desired test script   

## Postman Collection
[Collection](Meta%20coding%20challenge.postman_collection.json)