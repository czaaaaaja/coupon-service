<h1>Coupon Service</h1>
This is a simple Spring Boot microservice designed to handle the discount coupons and their lifecycle.

<h3>Tech stack</h3>
* Java 25 (LTS) - the newest version with various performance improvements.
* Spring Boot 4.0.6 - the newest version, suitable for a maintainable microservice architecture.
* Spring Data JPA/Hibernate - efficient ORM and transaction management.
* H2 Database - used in integration tests for fast execution. Due to Spring Boot features can be changed to any popular database for production environments.
* JUnit 6 & AssertJ, Mockito - for testing purposes

<h3>Architecture and implementation details</h3>
* Optimistic locking - prevents multiple clients from modifying the Coupon entity simultaneously. This ensures the data consistency and prevents the lost updates. Assuming that the conflicts are rare, it's perfectly enough to block optimistically.
* Testing strategy - each layer of the application is tested separately in order to increase quality of the application:
  * unit tests for business logic, 
  * data JPA tests for query correctness and data consistency,
  * integration tests for more complex flows.
* Usage of UUIDs for primary keys - ensures safer and unguessable identifiers and makes data merging/migrating easier.
* Separation of domain and entity objects - the domain logic is decoupled from Hibernate/JPA annotations, making the core business logic easier to test and immune to changes in the database schema.
* IP to country mapping with a free-to-use service ipapi.
* Global Error Handling - decoupling error logic from controllers.
* DTO Separation from domain models to prevent internal data exposure and allow API evolution without affecting the persistence layer.
* Transactional Integrity to ensure ACID compliance during coupon redemption processes.
* Input validation at earliest possible stage for a quick return.

<h5>Compile and run the service</h5>
- ./mvnw spring-boot:run
- ./mvnw test