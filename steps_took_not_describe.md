# Notes
1. Spring Boot App mit dependencies
  - io.prometheus
  - micrometer-registry
  - actuator
  - application.properties expose prometheus Actuator Endpoint
2. Bau SB App und Push to Registry
  - Bau JAR ``mvn clean install``
  - Bau Container via Dockerfile  
  ```Dockerfile
  FROM openjdk:10
  ADD target/SpringBootActuatorScalingK8s-0.0.1-SNAPSHOT.jar app.jar
  EXPOSE 8080
  ENTRYPOINT java -jar /app.jar
  ```
3. Deploy App to K8s
  - Beachten: Annotations im Deployment
    1. io.prometheus.path: /actuator/prometheus
    2. io.prometheus.port: 8080
    3. io.prometheus.scrape: true
  - Validate running App, einmal aufrufen
4. create Namespace
  - ``kubectl cretae namespace custom-metrics``
5. deploy prometheus
  - entweder prometheus Operator (nicht gemacht)
  - einzelne YAML, prometheus.yml
  - Check Prom scraped pods in prom dashboard
6. deploy adapter
  - Config kompliziert -> potentieller Fehlergrund
  - Zertifikate erstellen -> cfssl
  - Adapter yaml
7. Check ob alles geht ohne HPA
  - ``kubectl get --raw "/apis/custom.metrics.k8s.io/v1beta1" | jq``
  - Sollte Metriknamen zurück geben
  - falls nicht -> **Logs Adapter können helfen**
  

**NOTE: Alles auch als HELM Chart, aber manuell übt besser :P**
