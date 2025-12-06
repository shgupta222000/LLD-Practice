🏗 HLD (High-Level Design) Questions

1️⃣ Design a Payment Service (Paytm / Razorpay)

Focus:
	•	Circuit Breaker, Retry, Timeouts
	•	Message queues for async payment confirmation
	•	Idempotency keys
	•	Distributed transactions & saga pattern
	•	Webhooks or event sourcing

⸻

2️⃣ Design a Ride Sharing System (Uber)

Focus:
	•	Location services
	•	Matching algorithm
	•	Redis or Kafka
	•	Event queues
	•	Failover & geo partitioning

⸻

3️⃣ Design a Food Delivery System (Swiggy)

Focus:
	•	Order processing workflow
	•	Rate limiter on restaurant APIs
	•	Cache menus & ratings
	•	Load balancing
	•	Bulkhead between delivery & order teams

⸻

4️⃣ Design a Notification System (Email + SMS)

Focus:
	•	Circuit Breaker for SMS gateways
	•	Retry + backoff
	•	Queue-based async delivery
	•	Fallback channel (send email if SMS fails)

⸻

5️⃣ Design a URL Shortener (TinyURL)

Focus:
	•	Hashing & collision avoidance
	•	Analytics with Kafka
	•	Redis cache
	•	High availability

⸻

6️⃣ Design a Scalable Chat System (WhatsApp)

Focus:
	•	Websockets
	•	Kafka / Pulsar for message queues
	•	Message ordering & delivery semantics
	•	Offline storage
	•	Sharding of user messages

⸻

7️⃣ Design an E-commerce System (Amazon)

Focus:
	•	Cart service
	•	Product service
	•	Inventory consistency
	•	Saga / event sourcing
	•	Retry + Circuit Breaker between microservices

⸻

8️⃣ Design a Logging System (ELK)

Focus:
	•	Distributed ingestion
	•	Indexing & analytics
	•	Scaling Elasticsearch
	•	Failure isolation
	•	Backpressure & bulkheads

⸻

9️⃣ Design a Social Feed (Instagram)

Focus:
	•	News feed fanout
	•	Push vs pull
	•	Redis caching
	•	Microservices with retries
	•	Consistency models

⸻

🔟 Design a Video Streaming Platform (YouTube)

Focus:
	•	CDN
	•	Caching tiers
	•	HLS adaptation
	•	Metadata store
	•	Bulkhead across upload & streaming

⸻

⚙️ LLD (Low-Level Design) Questions

These test actual coding + design skills.

1️⃣ Design a Rate Limiter
	•	Sliding window / token bucket
	•	Redis or in-memory
	•	Thread safe
	•	Unit testing
	•	Per user & per endpoint limits

⸻

2️⃣ Design a Cache System
	•	LRU / LFU
	•	Thread safe eviction
	•	Metrics tracking
	•	Timeouts and stale reads

⸻

3️⃣ Design Circuit Breaker (Custom Implementation)
	•	Closed → Open → Half-Open
	•	Configurable thresholds
	•	Fallback interface
	•	Metrics store

⸻

4️⃣ Design a Thread Pool
	•	Producer-consumer queue
	•	Worker threads
	•	Graceful shutdown
	•	Task timeout

⸻

5️⃣ Design a Kafka-like Message Queue
	•	Append-only logs
	•	Consumers & offsets
	•	Retention policies
	•	Backpressure
	•	Threading

⸻

6️⃣ Design an Elevator System
	•	Scheduling
	•	Multi elevator coordination
	•	Sensor events
	•	FSM (finite-state machine)

⸻

7️⃣ Design a Restaurant Booking System
	•	Slot allocation
	•	Optimistic locking
	•	Overbooking prevention

⸻

8️⃣ Design a Game Lobby Matchmaking System
	•	Queue of players
	•	Skill-based matching
	•	Failure scenarios
	•	Distributed queue

⸻

9️⃣ Design a Task Scheduler (like Cron)
	•	Priority queues
	•	Thread safe timers
	•	Persistent schedule

⸻

🔟 Design an API Gateway
	•	Routing
	•	Auth
	•	Rate limiter
	•	Circuit breaker
	•	Request correlation IDs

⸻

⭐ LLD + Coding + Resilience COMBO Questions (Very High Value)

These are absolute gold for SDE-2:
	•	Design a Circuit Breaker library (no annotations)
	•	Design a Bulkhead with separate thread pools
	•	Design a Message Retry + Dead Letter Queue
	•	Design an Idempotency Token Store
	•	Design a Cache with write-through and TTL
	•	Design a Distributed Lock Manager using Redis
	•	Design a Task Workflow Engine (Saga pattern)

These test platform engineering + distributed system fundamentals.

⸻

🌟 What Interviewers Look For

For each design problem, you should demonstrate:
	1.	API contracts & class design
	2.	Concurrency control
	3.	Fault tolerance
	4.	Caching, indexing, sharding
	5.	Backpressure, rate limiting
	6.	Thread safety
	7.	Resilience patterns
	8.	Monitoring + metrics
	9.	Testing strategy (JUnit + Mockito)
	10.	Graceful degradation

⸻

🥇 BEST Next Step 

🔹 HLD (architecture diagram + APIs + scaling)

🔹 LLD (classes + interfaces + threading)

🔹 JUnit tests + mocks

🔹 Spring Boot microservice implementation

🔹 Circuit Breaker + Retry + Rate Limiter integration

Suggested problems:
	•	Payment service
	•	Notification service
	•	Rate limiter
	•	Message queue
	•	Cache
	•	Task scheduler
	•	Circuit breaker library
