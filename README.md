# CSIT318-Project 

Welcome to our project! The project overall is an AI assisted educational resource ordering + assisted learning
service that provides users an easy way to access resources for educational purpose, however also suggesting resources
that may be suitable, while also assisting in pinpointing key information within these resources collected.

Altogether we have the following 5 microservices
- ### Account Service
- ### Order Service
- ### Guides Service (Agentic Component)
- ### Resource Service
- ### Suggestion Service (Agentic Component)

Below you should find outlined test cases alongside code to assist in hitting each use case of our system.
# Introduction to our services + test code
## Account Service
1. Creating an account
```
curl -X POST http://localhost:3000/api/example -H "Content-Type: application/json" -d '{"name": "Alice", "age": 30}'
```

```
curl -X GET http://localhost:3000/api/example 
```

## Resource Service

```
curl -X POST http://localhost:3000/api/example -H "Content-Type: application/json" -d '{"name": "Alice", "age": 30}'
```

```
curl -X GET http://localhost:3000/api/example
```

## Order Service

```
curl -X POST http://localhost:3000/api/example -H "Content-Type: application/json" -d '{"name": "Alice", "age": 30}'
```

## Guide Service

```
curl -X POST http://localhost:3000/api/example -H "Content-Type: application/json" -d '{"name": "Alice", "age": 30}'
```

```
curl -X GET http://localhost:3000/api/example
```

## Suggestion Service
1. Get All existing suggestions within the suggestionRepository
```
curl -X GET http://localhost:8084/suggestions
```
2. Get a stored suggestion by ID (substitute ```{suggestionId}``` with the ID of  suggestion collected from step 1)
```
curl -X GET http://localhost:8084/suggestions/{suggestionId}
```
3. Generate a suggestion via manual input filters (you can modify the body to accept any Educational Resource properties, in this case we are using the ```knowledgeLevel``` property)
```
curl -X POST http://localhost:8084/suggestions/generate -H "Content-Type: application/json" -d '{"knowledgeLevel": "Beginner"}'
```
4. Generate a suggestion from a user's order history (Agentic Component) (substitute ```{userId}``` for a userId from step 2 of the account service tests)
```
curl -X POST http://localhost:8084/suggestions/generate/{userId}/orderHistory
```
5. Generate a suggestion from a user's profile preferences (Agentic Component) (substitute ```{userId}``` for a userId from step 2 of the account service tests)
```
curl -X POST http://localhost:8084/suggestions/generate/{userId}/userPreferences
```

# Events Simulation
- Some of the previously completed actions will trigger events handled by apache kafka or the spring application event repository methods. To see these events take place, run the following command at the directory ``../CSIT318-Group-Project/``
```
apache kafka startup cmd
```

# Streaming Simulation
```
example for streaming simulation
```