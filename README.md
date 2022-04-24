# Banking Account APIs
This application is just a sample application for demonstrate spring boot APIs.

## Assumptions and Points

### Functional:

1. An account does not have multiple joint owners.
2. Money transfer can only be done in same bank.
3. Each credit card is linked with credit account.
3. I have implemented basic authentication but in practical application it should be done by tokens and Mutual TLS.

### Non-Functional:

1. This code is written without knowledge of any NFRs, one we know the NFRs, design and technical choices might be totally changed.
2. I have used in memory H2 for database, unit and integration test.
3. I am calling service directly but in real world this should be done via discovery and gateway mechanism.
4. For more efficient implementation we can implement the same with reactive programming.

### Tools and Technology:

1. API : Spring boot.
2. Database: H2 in memory database.
3. Coverage : JaCoCo.
4. Vulnerability scan : OWASP Dependency Check.

### ER diagram:

![image](https://user-images.githubusercontent.com/55003223/164916711-0a821efc-36ba-4584-960f-01e92336d496.png)

### Swagger UI:http://localhost:8080/swagger-ui.html

![image](https://user-images.githubusercontent.com/55003223/164890918-f47b35b7-1c74-441e-b3e4-651f45902603.png)
