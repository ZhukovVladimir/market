CREATE TABLE account_role (
                              account_id INTEGER   NOT NULL ,
                              role_id INTEGER   NOT NULL   ,
                              PRIMARY KEY(account_id, role_id)    ,
                              FOREIGN KEY(account_id)
                                  REFERENCES account(id),
                              FOREIGN KEY(role_id)
                                  REFERENCES role(id));

ALTER TABLE account DROP COLUMN role_id;