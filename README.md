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
## Prerequisites:
- `Google Gemini API Key` (can be created at https://aistudio.google.com/api-keys):
  - Create a new environment variable for both the Guide-Service and Suggestion-Service, it must be formatted `GEMINI_API_KEY={api_key}`
- `Apache Kafka` must be installed on your system:
  - Download a binary package of Apache Kafka (e.g., kafka_2.13-3.7.0.tgz) from https://kafka.apache.org/downloads and unzip it to a directory, e.g., C:\kafka—Windows does not like a complex path name (!).
  - Use the following two commands in the Windows CMD (one in each window) to start Kafka:
```
C:\kafka\bin\windows\zookeeper-server-start.bat C:\kafka\config\zookeeper.properties
```
```
C:\kafka\bin\windows\kafka-server-start.bat C:\kafka\config\server.properties
```
# Account Service
1. Viewing all user accounts
```
curl -X GET http://localhost:8080/api/users 
```

2. Creating an account
```
curl -X POST http://localhost:8080/api/users -H "Content-Type: application/json" -d '{"email": "test.user@gmail.com", "firstName": "Test", "lastName": "User"}'
```

3. Viewing a specific user account (provide a ```{userId}``` from a result of step 1)
```
curl -X GET http://localhost:8080/api/users/{userId} 
```

4. Modifying a specific user account (provide a ```{userId}``` from a result of step 1)
```
curl -X PUT http://localhost:8080/api/users/{userId} -H "Content-Type: application/json" -d '{"lastName": "User-Modified"}'
```

6. Cancelling a membership
```
curl -X POST http://localhost:8080/api/users/{userId}/cancel-membership
```
# Resource Service

1. Collect all resources
```
curl -X GET http://localhost:8081/resources
```

2. Collect a specific resource (provide a ```{resourceId}``` from a result of step 1)
```
curl -X GET http://localhost:8081/resources/{resourceId}
```

# Order Service

```
curl -X POST http://localhost:3000/api/example -H "Content-Type: application/json" -d '{"name": "Alice", "age": 30}'
```

# Guide Service

```
curl -X POST http://localhost:3000/api/example -H "Content-Type: application/json" -d '{"name": "Alice", "age": 30}'
```

```
curl -X GET http://localhost:3000/api/example
```

# Suggestion Service
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
If you haven't already, ensure Apache Kafka is running on your machine:\
[Go to Prerequisites](#prerequisites) and follow the Apache Kafka cmd steps
# Streaming Simulation
```
example for streaming simulation
```