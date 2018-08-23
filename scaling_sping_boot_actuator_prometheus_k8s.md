# Scaling your Spring Boot Application an Custom Metrics in K8s
The default way to scale Pods in Kubernetes is scaling by CPU Usage. It is easy to do so by just specifying a target CPU Utilization by which the HPA scales and call it a day. But what if your problem can't be reduced to a CPU Usage problem or you want to scale by some other metric, like requests or latency?

I will explain to you, how you can use any metric you want to scale by using Spring Boot and Prometheus.

## History
As always lets have a small look into how things where handled and what changed.
### 2016 - Kubernetes Version 1.2
With the release of K8s v1.2.0 the *autoscaling/v1* API was released and with it the HPA was put out of beta. To scale your application on specific metrics you could use an annotation based system.
### 2017 - Kubernetes Version 1.6
With K8s v1.6.0 the whole HPA was reworked. Instead of using Heapster to collect metrics from the cluster a new metrics-server was added with a new API. This added the ability to scale on multiple metrics and also, much more important for this post, unified the way to provide Custom Metrics for Pods.

As you can see in *Image current API Metrics diagram*, there are multiple Endpoints, that are fetched. Each of those endpoints is created by a different API Server, the ```metrics.k8s.io``` f.e is created by K8s metrics-server. To scale the application I will host Prometheus, let it scrape our pods and collect all the Spring Boot Actuator metrics. After that I will host the prometheus-k8s-adapter *Link in links and futher reading* to provide the metrics to K8s.

## Deploy
*Steps to take to deploy*
1. Namespace
2. Prometheus
3. Adapter
  - config can be tricky
4. sample App
  - annotations

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


**IMAGE: HPA -> metrics-server -> Pods / -> adapter -> Prometheus -> Pods**

## Links and further reading
Thanks a lot to the Work of all the people listed here. I highly recommend checking out all of the links.
1. *kubernetes HPA documentation*
2. *K8s Adapter github Link*
