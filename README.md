# CSIT318-Project 

Welcome to our project! The project overall is an AI assisted educational resource ordering + assisted learning
service that provides users an easy way to access resources for educational purpose, however also suggesting resources
that may be suitable, while also assisting in pinpointing key information within these resources collected.

Altogether we have the following 5 microservices\
- ### Account Service
- ### Order Service
- ### Guides Service (Agentic Component)
- ### Resource Service
- ### Suggestion Service (Agentic Component)
\
Below you should find outlined test cases alongside code to assist in hitting each use case of our system.
# Introduction to our services + test code
## Account Service
### 1. Creating an account
```declarative
curl -X POST http://localhost:3000/api/example \
-H "Content-Type: application/json" \
-d '{"name": "Alice", "age": 30}'
```

```declarative
curl -X GET http://localhost:3000/api/example 
```

## Resource Service

```declarative
curl -X POST http://localhost:3000/api/example \
-H "Content-Type: application/json" \
-d '{"name": "Alice", "age": 30}'
```

```declarative
curl -X GET http://localhost:3000/api/example
```

## Order Service

```declarative
curl -X POST http://localhost:3000/api/example \
-H "Content-Type: application/json" \
-d '{"name": "Alice", "age": 30}'
```

## Guide Service

```declarative
curl -X POST http://localhost:3000/api/example \
-H "Content-Type: application/json" \
-d '{"name": "Alice", "age": 30}'
```

```declarative
curl -X GET http://localhost:3000/api/example
```

## Suggestion Service

```declarative
curl -X POST http://localhost:3000/api/example \
-H "Content-Type: application/json" \
-d '{"name": "Alice", "age": 30}'
```

```declarative
curl -X GET http://localhost:3000/api/example
```

# Events Simulation
- Some of the previously completed actions will trigger events handled by apache kafka or the spring application event repository methods. To see these events take place, run the following command at the directory ``../CSIT318-Group-Project/``
```declarative
apache kafka startup cmd
```

# Streaming Simulation
```declarative
example for streaming simulation
```