DROP TABLE orders CASCADE CONSTRAINTS;
DROP TABLE customers CASCADE CONSTRAINTS;
DROP TABLE products CASCADE CONSTRAINTS;

CREATE TABLE products
(	id   INTEGER,
	name VARCHAR2(40),
	price NUMBER(10,2),
	on_stock INTEGER,
	critical_level INTEGER,
	CONSTRAINT pk_products PRIMARY KEY (id),
	CONSTRAINT ck_products_onstock CHECK (on_stock >= 0)
);
CREATE TABLE customers
(	id   INTEGER,
	name VARCHAR2(40),
	address VARCHAR2(40),
	CONSTRAINT pk_customers PRIMARY KEY (id)
);
CREATE TABLE orders
(	id   INTEGER,
	id_cust INTEGER,
	id_prod INTEGER,
	orderdate DATE,
	quantity INTEGER,
	CONSTRAINT pk_orders PRIMARY KEY (id_cust, id_prod, orderdate),
	CONSTRAINT fk_orders_idcust FOREIGN KEY (id_cust) REFERENCES customers(id),
	CONSTRAINT fk_orders_idprod FOREIGN KEY (id_prod) REFERENCES products(id),
	CONSTRAINT uk_orders UNIQUE (id, id_prod)
);

INSERT INTO products VALUES(3, 'Asus Zen 8.0',  300.30, 200, 20);
INSERT INTO products VALUES(2, 'Logitec Supermouse', 30.30, 300, 30);
INSERT INTO products VALUES(1, 'Sony Xperia 2.1', 440.40, 400, 40);
INSERT INTO products VALUES(4, 'Amani Beach', 1300.30, 100, 20);
INSERT INTO products VALUES(5, 'Kik Strandanzug', 40.90, 1200, 120);
INSERT INTO products VALUES(6, 'H&M Beach Collection', 140.90, 400, 40);
INSERT INTO products VALUES(7, 'Fisher Slalom Carver', 390.30, 20, 10);
INSERT INTO products VALUES(8, 'Asics Runner',  60.90, 200, 20);
INSERT INTO products VALUES(9, 'Grabner Trimaran', 3500.00, 90, 20);
INSERT INTO products VALUES(10, 'Wolfskin Outdoor', 190.90, 200, 20);

INSERT INTO customers VALUES(1, 'Kernstein Kurt', 'Klagenfurt, Hauptplatz 11');
INSERT INTO customers VALUES(2, 'Wernheimer Hans', 'Wolfsberg, Burgstraﬂe 22');
INSERT INTO customers VALUES(3, 'Goldblum Gerda', 'Graz, Uhrgasse 11');
INSERT INTO customers VALUES(4, 'Ekberg Britta', 'Eisenstadt, Am See 14');
INSERT INTO customers VALUES(5, 'Prinzhofer Peter', 'Paternion, Am Kickel 11');
INSERT INTO customers VALUES(6, 'Mastroiani Marcello', 'Milano, Via Garibaldi 1');

INSERT INTO orders VALUES(1, 3, 7, TO_DATE('13.12.2015','DD.MM.YYYY'),12);
INSERT INTO orders VALUES(1, 3, 9, TO_DATE('13.12.2015','DD.MM.YYYY'),172);
INSERT INTO orders VALUES(1, 3, 10, TO_DATE('13.12.2015','DD.MM.YYYY'),120);
INSERT INTO orders VALUES(2, 5, 7, TO_DATE('16.12.2015','DD.MM.YYYY'),32);
INSERT INTO orders VALUES(2, 5, 1, TO_DATE('16.12.2015','DD.MM.YYYY'),42);
INSERT INTO orders VALUES(2, 5, 9, TO_DATE('16.12.2015','DD.MM.YYYY'),13);
INSERT INTO orders VALUES(3, 1, 1, TO_DATE('13.01.2016','DD.MM.YYYY'),16);
INSERT INTO orders VALUES(3, 1, 7, TO_DATE('13.01.2016','DD.MM.YYYY'),174);
INSERT INTO orders VALUES(3, 1, 3, TO_DATE('13.01.2016','DD.MM.YYYY'),210);
INSERT INTO orders VALUES(4, 3, 2, TO_DATE('04.02.2016','DD.MM.YYYY'),12);
