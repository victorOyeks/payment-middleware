# Payment Gateway Middleware

This project consists of two microservices designed to simulate a payment gateway middleware. The two microservices are:

1. **Bank Service (`bankservice`)**
2. **Bank Middleware (`bankmiddleware`)**

## Bank Service

The `bankservice` microservice handles various banking operations such as authentication, client account and card creation, transaction initiation, status retrieval, and webhook handling.

### Endpoints

- **Authentication**
    - `POST /auth/login`: Authenticates a user.

- **Client Operations**
    - `POST /api/accounts`: Creates a new client account.
    - `POST /api/accounts/{accountId}/cards`: Creates a new card for a specified account.

- **Transaction Operations**
    - `POST /api/core/initiate`: Initiates a transaction with encrypted messages.
    - `GET /api/transactions/{transactionId}/status`: Retrieves the status of a transaction.
    - `POST /api/transactions/webhook`: Handles transaction status updates via webhook.
    - `GET /api/transactions/by-account/{accountId}`: Retrieves transactions for a specific account.
    - `GET /api/transactions/by-client/{clientId}`: Retrieves transactions for a specific client.
    - `GET /api/transactions/all`: Retrieves all transactions.

## Bank Middleware

The `bankmiddleware` microservice handles payment initiation and securely communicates with the `bankservice` for payment processing. It encrypts the request before sending it to the `bankservice`.

### Endpoint

- **Payment Operations**
    - `POST /api/initiate-payment`: Initiates a payment request. The request is encrypted and sent to the `bankservice`.

## Encryption/Decryption

Both microservices use the `EncryptionDecryptService` for encrypting and decrypting messages to ensure secure communication between them.
