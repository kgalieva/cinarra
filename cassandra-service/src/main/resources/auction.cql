CREATE TABLE auction.product_transactions (
	product_id text,
	transaction_date bigint,
	transaction_id text,
	win_price decimal,
	PRIMARY KEY((product_id, transaction_date), transaction_id)
);

CREATE TABLE auction.products (
	transaction_date bigint,
	product_id text,
	PRIMARY KEY(transaction_date, product_id)
);
