# Guides Service

To test the agent cd to the service directory and run:
```bash
./mvnw spring-boot:run -Dspring-boot.run.arguments="--youtube.api.key=AIzaSyAGqvBRtBMeY1XbVMVXrD20wmg1mf7tvHA"
```

Then in another terminal window at the service directory run:
```bash
curl -X POST http://localhost:8082/guideAgent -F "file=@mockpaper.md" -F "researchGoal=Analyse the impact of AI"
```

## Youtube API Key

The service uses a youtube API key to fetch relevant videos. A free key was intentionally provided in the example above as instructed by the tutor.
