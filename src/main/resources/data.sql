INSERT INTO companies (tax_id, business_name, join_date) VALUES ('20443441575', 'Company A', '2025-03-10');
INSERT INTO companies (tax_id, business_name, join_date) VALUES ('20443441574', 'Company B', '2025-02-20');

INSERT INTO transfers (company_tax_id, debit_account, credit_account, amount, transfer_date)
VALUES ('20443441575', '123', '456', 1000.00, '2025-02-28 10:35:16.881');

INSERT INTO transfers (company_tax_id, debit_account, credit_account, amount, transfer_date)
VALUES ('20443441574', '789', '012', 500.00, '2025-02-28 11:00:00.000');