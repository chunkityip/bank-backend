CREATE TABLE customers (
                           id SERIAL PRIMARY KEY,
                           name VARCHAR(255) NOT NULL,
                           email VARCHAR(255) UNIQUE NOT NULL,
                           password VARCHAR(255) NOT NULL,
                           customer_identification_number VARCHAR(255) UNIQUE NOT NULL
);

CREATE TABLE bank_accounts (
                               id SERIAL PRIMARY KEY,
                               account_number VARCHAR(255) UNIQUE NOT NULL,
                               bank_type VARCHAR(255) NOT NULL,
                               sort_code BIGINT NOT NULL,
                               name VARCHAR(255),
                               opening_balance DECIMAL(19, 2),
                               balance DECIMAL(19, 2),
                               status VARCHAR(255),
                               customer_id BIGINT,
                               created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                               FOREIGN KEY (customer_id) REFERENCES customers(id)
);

CREATE TABLE transactions (
                              id SERIAL PRIMARY KEY,
                              type VARCHAR(255),
                              bank_account_id BIGINT,
                              description VARCHAR(255),
                              from_account BIGINT,
                              from_account_sort_code BIGINT,
                              to_account BIGINT,
                              to_account_sort_code BIGINT,
                              amount DECIMAL(19, 2),
                              transaction_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                              FOREIGN KEY (bank_account_id) REFERENCES bank_accounts(id)
);


-- Insert Customers
INSERT INTO customers (id, name, email, password, customer_identification_number)
VALUES (DEFAULT, 'Mike Clarke', 'mike@example.com', 'securepassword', 'CUST12345');

INSERT INTO customers (id, name, email, password, customer_identification_number)
VALUES (DEFAULT, 'Mr. Clarke', 'clarke@example.com', 'securepassword', 'CUST67890');

--All incorrect
-- Insert Bank Accounts
INSERT INTO bank_accounts (id, account_number, bank_type, sort_code, name, opening_balance, balance, status, customer_id)
VALUES (DEFAULT, '123456789', 'CHASE', 1234, 'Example Current Account', 0.00, 123.99, 'ACTIVATED', 0);

INSERT INTO bank_accounts (id, account_number, bank_type, sort_code, name, opening_balance, balance, status, customer_id)
VALUES (DEFAULT, '987654321', 'CITI', 43210, 'Savings', 100.00, 100.00, 'ACTIVATED', 0);

-- Insert Transactions
INSERT INTO transactions (type, bank_account_id, description, from_account, from_account_sort_code, to_account, to_account_sort_code, amount)
VALUES ('TRANSFER', 1, 'Transfer to savings', 123456789, 1234, 987654321, 4444, 150.00);

INSERT INTO transactions (type, bank_account_id, description, from_account, from_account_sort_code, to_account, to_account_sort_code, amount)
VALUES ('DEPOSIT', 1, 'Deposit to account', NULL, NULL, 123456789, 1234, 10.00);

INSERT INTO transactions (type, bank_account_id, description, from_account, from_account_sort_code, to_account, to_account_sort_code, amount)
VALUES ('WITHDRAWAL', 1, 'Withdraw from account', 123456789, 1234, NULL, NULL, 5.00);