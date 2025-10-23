# CSIT318-Project 

Welcome to our project! The project overall is an AI assisted educational resource ordering + assisted learning
service that provides users an easy way to access resources for educational purpose, however also suggesting resources
that may be suitable, while also assisting in pinpointing key information within these resources collected.

Altogether we have the following 5 microservices
- **Account Service**
- **Order Service**
- **Guides Service (Agentic Component)**
- **Resource Service**
- **Suggestion Service (Agentic Component)**

Below you should find outlined test cases alongside code to assist in hitting each use case of our system.
# Introduction to our services + test code
## Prerequisites:
> [!CAUTION]
> Failure to complete these steps will prevent the project from executing
- `Google Gemini API Key` (can be created at https://aistudio.google.com/api-keys):
  - Create a new environment variable for both the Guide-Service and Suggestion-Service, it must be formatted `GEMINI_API_KEY={api_key}`
- `Youtube API Key` (a free key is provided):
  - Create a new environment variable for the Guide-Service, it must be formatted `YOUTUBE_API_KEY=AIzaSyAGqvBRtBMeY1XbVMVXrD20wmg1mf7tvHA`
- `Docker Desktop` must be installed on your system
  - Once you have opened docker desktop, run the following command at the root directory (./CSIT381-Group-Project)
```
docker-compose up -d
```

This should then create 2 containers within your docker container registry, ensure both containers have started, then you can proceed with the tests below.
![Docker Container](docker-desktop.png "Docker Container")

## Start up all Microservices in the following order:
> [!IMPORTANT]  
> If you are unable to click "Start Application" for each one of these services, you may need to run ```mvn spring-boot:run``` within the directory of each service in a separate terminal
1. Resource Service
2. Order Service
3. Account Service
4. Guides Service
5. Suggestion Service
# Account Service
1. Creating an account
```
curl -X POST http://localhost:8080/api/users -H "Content-Type: application/json" -d "{\"email\": \"test.user@gmail.com\", \"firstName\": \"Test\", \"lastName\": \"User\"}"
```

2. Viewing all user accounts (keep note of the ```{userId}``` from the result for future tests)
```
curl -X GET http://localhost:8080/api/users 
```

3. Adding a research goal to a user account (provide a ```{userId}``` from a result of step 1)
```
curl -X PUT http://localhost:8080/api/users/{userId}/research-goal -H "Content-Type: application/json" -d "{\"researchGoal\": \"Gain knowledge in any random topic\"}"
```

4. Viewing a specific user account (provide a ```{userId}``` from a result of step 1)
```
curl -X GET http://localhost:8080/api/users/{userId} 
```

5. Modifying a specific user account (provide a ```{userId}``` from a result of step 1)
```
curl -X PUT http://localhost:8080/api/users/{userId} -H "Content-Type: application/json" -d "{\"lastName\": \"User-Modified\"}"
```

6. Setting or updating membership for a user
```
curl -X PUT http://localhost:8080/api/users/{user-id}/membership -H "Content-Type: application/json" -d "{\"type\": \"PREMIUM\", \"startDate\": \"2025-10-22\", \"endDate\": \"2026-10-22\", \"active\": true}"
```

7.  Cancelling a membership
```
curl -X POST http://localhost:8080/api/users/{userId}/cancel-membership
```

8. Create another user, then modify their user preferences (this is key for the suggestion service)
```
curl -X POST http://localhost:8080/api/users -H "Content-Type: application/json" -d "{\"email\": \"test.user2@gmail.com\", \"firstName\": \"Test\", \"lastName\": \"User\"}"
curl -X PATCH http://localhost:8080/api/users/{userId}/update-preferences -H "Content-Type: application/json" -d "{\"knowledgeType\":\"Video\",\"knowledgeLevel\":\"Proficient\",\"userPreferenceString\":\"I have been in the health sector for a couple of months now and I want to start learning more about dieting\",\"genres\":[\"Machine Learning\",\"Health and Medicine\"]}"
```

9. Real-time analysis of orders - (Run after adding orders)


# Resource Service
1. Load all resources into the database, grant the upload_resources.sh script executable permissions and run it
```
chmod +x upload_resources.sh
./upload_resources.sh
```

2. Collect all resources (keep note of a ```{resourceId}``` from the result for future tests)
```
curl -X GET http://localhost:8081/resources
```
if the result is overflowing then piping it to a file will help you get the first id easier
```
curl -X GET http://localhost:8081/resources > resources.json
```

3. Collect a specific resource (provide a ```{resourceId}``` from a result of step 1)
```
curl -X GET http://localhost:8081/resources/{resourceId}
```

# Order Service
1. Get all orders
```
curl -X GET http://localhost:8083/api/orders
```
2. Get a specific order by ID (substitute {orderId} with an ID from step 1)
```
curl -X GET http://localhost:8083/api/orders/{orderId}
```
3. Get orders for a specific user 
```
curl -X GET http://localhost:8083/api/orders/user/{userId}
```
4. Create a standard order (amount < $100)
```
curl -X POST http://localhost:8083/api/orders -H "Content-Type: application/json" -d "{\"userId\": 1, \"items\": [{\"productId\": \"bfd3617e-9081-4d63-b437-08ec93bd983d\", \"productName\": \"Introduction to Java\", \"quantity\": 1, \"price\": 45.00}]}"
```
5. Create a high-value order (amount > $100) - Triggers HIGH-VALUE alert and stream analytics
```
curl -X POST http://localhost:8083/api/orders -H "Content-Type: application/json" -d "{\"userId\": 2, \"items\": [{\"productId\": \"bfd3617e-9081-4d63-b437-08ec93bd983e\", \"productName\": \"Premium Course\", \"quantity\": 1, \"price\": 150.00}]}"
```
6. Create an anomaly order (significantly above average) - Triggers ANOMALY detection
```
curl -X POST http://localhost:8083/api/orders -H "Content-Type: application/json" -d "{\"userId\": 3, \"items\": [{\"productId\": \"bfd3617e-9081-4d63-b437-08ec93bd983f\", \"productName\": \"Enterprise Package\", \"quantity\": 1, \"price\": 500.00}]}"
```
7. Complete an order (substitute {orderId} - triggers OrderCompletedEvent for Suggestion Service)
```
curl -X PUT http://localhost:8083/api/orders/{orderId}/complete -H "Content-Type: application/json"
```
8. Get order history for recommendations (substitute {userId} - used by Suggestion Service)
```
curl -X GET http://localhost:8083/api/orders/history/{userId}
```

# Guide Service
1. Generate a guide for an article (Agentic Component), provide ```{resourceId}``` from step 2 of the resource service tests and ```{userId}``` from step 2 of the account service tests, best results if the research goal matches the resource topic
```
curl -X GET "http://localhost:8082/guide?resourceId={resourceId}&userId={userId}"
```

2. Collect all guides
```
curl -X GET http://localhost:8082/guides
```

3. Collect a specific guide (provide a ```{guideId}``` from a result of step 1)
```
curl -X GET http://localhost:8082/guide/{guideId}
```

4. Delete a cached guide when a resource is updated via Kafka events
```
curl -X PUT "http://localhost:8081/resources/{guideId}" -F "file=@updated-data.txt"
curl -X GET http://localhost:8082/guides
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
curl -X POST http://localhost:8084/suggestions/generate -H "Content-Type: application/json" -d "{\"knowledgeLevel\": \"INTERMEDIATE\"}"
```
4. Generate a suggestion from a user's order history (Agentic Component) (substitute ```{userId}``` for a userId from step 2 of the account service tests)
```
curl -X POST http://localhost:8084/suggestions/generate/{userId}/orderHistory
```
5. Generate a suggestion from a user's profile preferences (Agentic Component) (substitute ```{userId}``` for a userId from step 2 of the account service tests)
```
curl -X POST http://localhost:8084/suggestions/generate/{userId}/userPreferences
```
