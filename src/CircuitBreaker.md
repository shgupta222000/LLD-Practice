# Circuit Breaker Design Pattern

The **Circuit Breaker** is a resilience pattern used in distributed systems and microservices to prevent cascading failures when a downstream dependency (API, database, or service) is slow or unavailable.

---

## Why Circuit Breaker Was Introduced

Before the pattern, typical problems were:

- A service keeps calling a failing dependency
- Threads block on long timeouts
- Resource pools are exhausted
- Services crash due to overload
- A small outage becomes a **system-wide failure**

**Slowness is more dangerous than failure**, because slow calls block threads longer.

Circuit Breaker solves this by **stopping repeated calls to an already failing system**, improving availability and stability.

---

## How Circuit Breaker Works

Circuit Breaker has **three states**:

| State | Meaning |
|---|---|
| **Closed** | Normal call flow |
| **Open** | Stop calling downstream immediately (fast fail) |
| **Half-Open** | Send limited test calls to check recovery |

### Workflow

1. Service starts in **Closed**
2. If failures exceed a threshold, breaker goes **Open**
3. After a cool-off period, breaker moves to **Half-Open**
4. A successful test closes it to **Closed** again
5. If test fails, it returns to **Open**

---

## Where Circuit Breaker Is Used

Most common in:

- Microservices architecture
- Payment gateways
- Authentication services
- Third-party APIs (SMS, ML, weather, maps)
- Databases with intermittent connections
- Message brokers or streaming platforms

### Technology ecosystem:

- **Spring Boot + Resilience4j**
- **Netflix Hystrix** (legacy)
- **Reactive stacks**
- **AWS Lambda and cloud microservices**

---

## Implementation Example (Spring Boot + Resilience4j)

### Dependency

```gradle
implementation 'io.github.resilience4j:resilience4j-spring-boot3'
```
```java
@CircuitBreaker(
    name = "paymentService",
    fallbackMethod = "paymentFallback"
)
public PaymentResponse processPayment(Request req) {
    return paymentClient.callGateway(req);
}

public PaymentResponse paymentFallback(Request req, Throwable t) {
    return new PaymentResponse("FAILED", "Payment service is unavailable");
}
```
```yaml
resilience4j.circuitbreaker:
  instances:
    paymentService:
      failureRateThreshold: 50
      slidingWindowSize: 20
      waitDurationInOpenState: 30s
```
#Benefits
	•	Protects from cascading failures
	•	Saves threads and resources
	•	Provides fast failure instead of waiting for timeouts
	•	Allows graceful fallback behavior
	•	Improves uptime and user experience
	•	Enables automatic recovery logic

⸻

#Other Related Resilience Patterns (Must Know)

⸻

1. Retry Pattern
Temporarily retry a request when transient network issues occur.
```java
@Retry(name="paymentService", fallbackMethod="fallback")
```
2. Timeout Pattern

Avoid blocking requests indefinitely.
```java
@TimeLimiter(name = "paymentService")
```
3. Bulkhead Pattern

Isolate resource pools so that failure of one dependency does not starve the entire system.

Like compartments in a ship — one flooded compartment doesn’t sink the whole vessel.

⸻

4. Fallback Pattern
   Return a default or cached value when dependency is down.

⸻

5. Rate Limiter Pattern

•Control sudden bursts of traffic and protect services from overload.
	• Prevent overuse or abuse of APIs
	•	Protects system from sudden spikes

#How These Patterns Fit Together

Typical microservice call chain:
Call → Retry → Timeout → Circuit Breaker → Fallback
When under load:
Bulkhead + Rate Limiter
Together they form resilience tooling for modern distributed architecture.

🧩 Why These Patterns Matter (Interview Level)
	•	Core topic in SDE-2 & Distributed systems interviews
	•	Needed to design high availability microservices
	•	Architecture discussion for:
	•	Netflix
	•	Uber
	•	Amazon
	•	Swiggy
	•	Payment gateways

⸻

#Real-World Scenario

-If a payment gateway (e.g., PayTM) goes down:

#Without Circuit Breaker
	•	System keeps calling
	•	Threads block
	•	Backend crashes
	•	Whole app unavailable

#With Circuit Breaker
	•	Detect repeated failures
	•	Open breaker to stop calls
	•	Fast return fallback
	•	System stays alive

⸻

#Summary
	•	Circuit Breaker protects from cascading failures
	•	It has Closed → Open → Half-Open behavior
	•	Used widely in microservices & distributed systems
	•	Best implemented using Resilience4j
	•	Related must-know resilience patterns:
	•	Retry
	•	Timeout
	•	Bulkhead
	•	Fallback
	•	Rate Limiter
