CREATE SCHEMA bank;

CREATE TABLE bank.card (
    id VARCHAR(100) NOT NULL PRIMARY KEY,
    card_type VARCHAR(20) NOT NULL,
    card_number VARCHAR(20) NOT NULL,
    card_cvv INTEGER NOT NULL,
    created timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
    CHECK (card_type IN ('CREDIT','DEBIT')),
    UNIQUE (card_number)
);

CREATE TABLE bank.customer (
    id VARCHAR(100) NOT NULL PRIMARY KEY,
    customer_name VARCHAR(20) NOT NULL,
    customer_no VARCHAR(20) NOT NULL,
    customer_address VARCHAR(200) NOT NULL,
    email_id VARCHAR(100) NOT NULL,
    created timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
    UNIQUE (customer_no)
);

CREATE TABLE bank.account (
    id VARCHAR(100) NOT NULL PRIMARY KEY,  
    account_type VARCHAR(50) NOT NULL,
    account_no VARCHAR(50) NOT NULL,
    current_balance NUMERIC(10,2) NOT NULL,
    card_id VARCHAR(100) NOT NULL REFERENCES bank.card(id),
    customer_id VARCHAR(100) NOT NULL REFERENCES bank.customer(id),
    created timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
    CHECK (current_balance>=0),
    CHECK (account_type IN ('SAVING','CREDIT')),
    UNIQUE (account_no)
);

CREATE TABLE bank.transaction (
    id VARCHAR(100) NOT NULL PRIMARY KEY,
    source_account_id VARCHAR(100) NOT NULL REFERENCES bank.account(id),
    target_account_id VARCHAR(100) REFERENCES bank.account(id),
    amount NUMERIC(10,3) NOT NULL,
    charges NUMERIC(10,3) DEFAULT 0.00,
    transaction_type VARCHAR(50),
    transaction_date timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
    reference VARCHAR(255),
    CHECK (transaction_type IN ('TRANSFER','WITHDRAL'))
);