To test the agent cd to the service directory and run:
```bash
./mvnw spring-boot:run
```

Then in another terminal window at the service directory run:
```bash
curl -X POST http://localhost:8082/guideAgent -F "file=@mockpaper.md" -F "researchGoal=Analyse the impact of AI"
```
