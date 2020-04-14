CREATE TABLE memory (
  id SERIAL  NOT NULL ,
  volume INTEGER      ,
PRIMARY KEY(id));




CREATE TABLE category (
  id SERIAL  NOT NULL ,
  name TEXT      ,
PRIMARY KEY(id));




CREATE TABLE model (
  id SERIAL  NOT NULL ,
  category_id INTEGER NOT NULL,
  name TEXT      ,
PRIMARY KEY(id),
  FOREIGN KEY(category_id)
    REFERENCES category(id));




CREATE TABLE role (
  id SERIAL  NOT NULL ,
  name TEXT      ,
PRIMARY KEY(id));




CREATE TABLE color (
  id SERIAL  NOT NULL ,
  name TEXT      ,
PRIMARY KEY(id));




CREATE TABLE account (
  id SERIAL  NOT NULL ,
  password TEXT NOT NULL,
  role_id INTEGER   NOT NULL ,
  email TEXT   NOT NULL ,
  first_name TEXT    ,
  last_name TEXT    ,
  phone BIGINT   NOT NULL ,
  address TEXT    ,
  phone_country_code INTEGER      ,
PRIMARY KEY(id)  ,
  FOREIGN KEY(role_id)
    REFERENCES role(id));


CREATE INDEX account_FKIndex1 ON account (role_id);


CREATE INDEX IFK_Rel_04 ON account (role_id);


CREATE TABLE cart (
  id SERIAL  NOT NULL ,
  account_id INTEGER   NOT NULL ,
  delivery_address TEXT    ,
  delivery_status TEXT      ,
  bill DOUBLE PRECISION ,
  creation_time TIMESTAMP,
  payment_time TIMESTAMP,
PRIMARY KEY(id)  ,
  FOREIGN KEY(account_id)
    REFERENCES account(id));


CREATE INDEX client_order_FKIndex1 ON cart (account_id);


CREATE INDEX IFK_Rel_05 ON cart (account_id);


CREATE TABLE product (
  id SERIAL  NOT NULL ,
  color_id INTEGER   NOT NULL ,
  memory_id INTEGER   NOT NULL ,
  category_id INTEGER   NOT NULL ,
  name TEXT    ,
  description TEXT    ,
  price DOUBLE PRECISION    ,
  count INTEGER    ,
  image TEXT      ,
PRIMARY KEY(id)        ,
  FOREIGN KEY(category_id)
    REFERENCES category(id),
  FOREIGN KEY(memory_id)
    REFERENCES memory(id),
  FOREIGN KEY(color_id)
    REFERENCES color(id));


CREATE INDEX product_FKIndex1 ON product (category_id);
CREATE INDEX product_FKIndex2 ON product (memory_id);
CREATE INDEX product_FKIndex3 ON product (color_id);


CREATE INDEX IFK_Rel_12 ON product (category_id);
CREATE INDEX IFK_Rel_13 ON product (memory_id);
CREATE INDEX IFK_Rel_14 ON product (color_id);


CREATE TABLE product_cart (
  product_id INTEGER   NOT NULL ,
  cart_id INTEGER   NOT NULL   ,
PRIMARY KEY(product_id, cart_id)    ,
  FOREIGN KEY(product_id)
    REFERENCES product(id),
  FOREIGN KEY(cart_id)
    REFERENCES cart(id));


CREATE INDEX product_has_cart_FKIndex1 ON product_cart (product_id);
CREATE INDEX product_has_cart_FKIndex2 ON product_cart (cart_id);


CREATE INDEX IFK_Rel_10 ON product_cart (product_id);
CREATE INDEX IFK_Rel_11 ON product_cart (cart_id);


