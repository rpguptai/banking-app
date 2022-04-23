# Banking Account APIs
This application is just a sample application for demonstrate spring boot APIs.

## Assumptions and Points

### Functional:

1. An account does not have multiple joint owners.
2. Money transfer can only be done in same bank.
3. Withdraw can only be done by Debit card.
4. Transfer can only be done by account number.
5. I have implemented basic authentication but in practical application it should be done by tokens and Mutual TLS.

### Non-Functional:

1. This code is written without knowledge of any NFRs, one we know the NFRs, design and technical choices might be totally changed.
2. I have used in memory H2 for database, unit and integration test.
3. I am calling service directly but in real world this should be done via discovery and gateway mechanism.

### ER diagram:

![image](https://user-images.githubusercontent.com/55003223/164916711-0a821efc-36ba-4584-960f-01e92336d496.png)

### Swagger UI:

![image](https://user-images.githubusercontent.com/55003223/164890918-f47b35b7-1c74-441e-b3e4-651f45902603.png)
