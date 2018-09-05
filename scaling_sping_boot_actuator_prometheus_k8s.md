# Scaling your Spring Boot Application on Custom Metrics in K8s
The default way to scale Pods in Kubernetes is scaling by CPU Usage. It is easy to do so by just specifying a target CPU Utilization by which the HPA scales and call it a day. But what if your problem can't be reduced to a CPU Usage problem or you want to scale by some other metric, like requests or latency?

I will explain to you, how you can use any metric you want to scale by using Spring Boot and Prometheus.

**IMAGE: HPA -> metrics-server -> Pods / -> adapter -> Prometheus -> Pods**

As you can see in *Image current API Metrics diagram*, there are multiple Endpoints, that are fetched. Each of those endpoints is created by a different API Server, the ```metrics.k8s.io``` f.e is created by K8s metrics-server. To scale the application we will host Prometheus, let it scrape our pods and collect all the Spring Boot Actuator metrics. After that we will host the prometheus-k8s-adapter *Link in links and futher reading* to provide the metrics to K8s.

## Prerequisit
To do this walkthrough, you need the following tools installed and working.
1. **[minikube](https://github.com/kubernetes/minikube)** *(or any other way to run K8s, but I only tested it with minikube)* including **kubectl**
2. **[maven](https://maven.apache.org/)** or **[Gradle](https://gradle.org/)** to build the Spring Boot Application
4. **[go](https://golang.org/)** to get some of the needed tools like **cfssl**
3. **[cfssl](https://github.com/cloudflare/cfssl)** to create some certificates needed for the prometheus-k8s-adapter

I highly recommend using the following Tools:
1. **[hey](https://github.com/rakyll/hey)** HTTP load generator that is simple but very efficient
2. **[jq](https://stedolan.github.io/jq/)** Easy to use JSON processor, since K8s APIs are not really formatted all the time

Last but not least you will need an Spring Boot App with some Dependencies.

## Spring Boot Sample App
To scrape metrics from your App with Prometheus, there are some Dependencies needed. Fortunately the rest is auto configured ones those dependencies are added.

```xml
<!-- Prometheus, allows to publish metrics in Prometheus format -->
<dependency>
  <groupId>io.prometheus</groupId>
  <artifactId>simpleclient</artifactId>
  <version>0.5.0</version>
</dependency>

<!-- Spring Boot Metrics collector and management -->
<dependency>
  <groupId>io.micrometer</groupId>
  <artifactId>micrometer-registry-prometheus</artifactId>
  <version>1.0.6</version>
</dependency>

<!-- Provides basic Metrics like Health state -->
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```
In my Sample App (which you can also find in the repository) I added one REST Endpoint, which generates some request metrics, on which i will scale. Of course you could add those dependencies to any Spring Boot App and get your Actuator metrics.

The most important part about this is hidden in the auto configuration. Spring Boot will provide all the Actuator Metrics in the format used by Prometheus. The Endpoint for this is */actuator/prometheus*.

*warning sign* **Do not forget to expose the actual endpoint within your application.properties**

## Deploy
Let me describe all the needed steps to get this up and running. To make it easy I provide one deploy.yml file within my repository. I still recommend reading and doing all the needed steps ones.

### Namespace
This one is easy, everything I deploy lands in the namespace custom-metrics or default. As the namespace default is already there, I only have to create the namespace custom-metrics. Either use ```kubectl create namespace custom-metrics``` or the following YAML

```yml
kind: Namespace
apiVersion: v1
metadata:
  name: custom-metrics
  labels:
    name: custom-metrics
```
### Prometheus

### Prometheus-K8s-Adapter
### Sample App

## Testing if it works
*Steps to take to check if everything worked*
1. Check pods deployed
2. check if prometheus picks up Kube-pods job
3. Check if Spring boot actuator provides metrics and query them in Prom
4. Check kubectl get --raw <api endpoint>
5. Create HPA, check if HPA is getting metrics and scaled

## Pitfalls
*Things that can go wrong / will go wrong*
1. HPA not picking up metrics
  - Check hpa-use-rest-endpoints
  - check kubectl get --raw
2. Metrics not catched by adapter
  - ADAPTER LOGS, whre does it query, what are the results
  - check Filters in adapter, it does not match all metrics but only a selection

## Conclusion
*dificulty up, possibilities WAY UP*

## Links and further reading
Thanks a lot to the Work of all the people listed here. I highly recommend checking out all of the links.
1. *kubernetes HPA documentation*
2. *K8s Adapter github Link*
3. *github Link to Repos for Artifacts*
4. *Prometheus link*
