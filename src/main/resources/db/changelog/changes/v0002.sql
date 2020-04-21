CREATE TABLE image (
  id SERIAL  NOT NULL ,
  path TEXT      ,
PRIMARY KEY(id));

ALTER TABLE product
    ADD COLUMN image_id INTEGER  ,
    ADD FOREIGN KEY (image_id)
        REFERENCES image(id);
