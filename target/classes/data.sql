DROP TABLE IF EXISTS recommended_purchase_history;
DROP TABLE IF EXISTS stock_check_audit;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS inventory;
DROP TABLE IF EXISTS products;
--DROP TABLE IF EXISTS order_advice;
--DROP SEQUENCE IF EXISTS adviceidseq;

--CREATE SEQUENCE adviceidseq start with 5;
 
CREATE TABLE products (
  product_id INT AUTO_INCREMENT  PRIMARY KEY,
  product_name VARCHAR(250) NOT NULL,
  UNIQUE KEY (product_name)
);
 
INSERT INTO products(product_name) VALUES
  ('a'),
  ('b'),
  ('c'),
  ('d'),
  ('e');

  CREATE TABLE inventory (
  	inventory_id INT AUTO_INCREMENT  PRIMARY KEY,
  	product_id int not null,
  	stock_quantity int not null,
  	reorder_level int not null,
  	reorder_quantity int not null,
  	reorder_additional_volume int not null,
  	is_blocked boolean default false, 
  	UNIQUE KEY (inventory_id, product_id),
  	foreign key (product_id) references products(product_id)
  );
  insert into inventory 
  (product_id, stock_quantity, reorder_level, reorder_quantity, reorder_additional_volume, is_blocked)
  values
  (1, 5, 4, 10, 0, false),
  (2, 8, 4, 10, 0, false),
  (3, 2, 4, 10, 0, true),
  (4, 0, 8, 10, 0, false),
  (5, 1, 4, 10, 0, false);
  
  CREATE TABLE orders (
  	order_id INT AUTO_INCREMENT PRIMARY KEY,
  	order_date date default sysdate, 
  	product_id int not null,
  	order_quantity int not null,
  	foreign key (product_id) references products(product_id)
  );
  
  insert into orders (order_date, product_id, order_quantity) values(sysdate, 4, 15);
  
  CREATE TABLE stock_check_audit (
  	audit_id INT AUTO_INCREMENT PRIMARY KEY,  	
  	checked_date date default sysdate,
  	checked_by Varchar2(250)	  	
  );
  
  CREATE TABLE recommended_purchase_history (
    rph_id INT AUTO_INCREMENT PRIMARY KEY,
    audit_id  int not null,
    product_id int not null,
    stock_quantity int not null,
    reorder_level int not null,
  	reorder_quantity int not null,
  	reorder_additional_volume int not null,
  	is_blocked boolean default false, 
    foreign key (product_id) references products(product_id),
    foreign key (audit_id) references stock_check_audit(audit_id)
  );
