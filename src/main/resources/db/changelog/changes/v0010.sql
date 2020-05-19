CREATE TABLE authority
(
    id        SERIAL NOT NULL,
    authority TEXT,
    PRIMARY KEY (id)
);

CREATE TABLE account_authority (
                              account_id INTEGER   NOT NULL ,
                              authority_id INTEGER   NOT NULL   ,
                              PRIMARY KEY(account_id, authority_id)    ,
                              FOREIGN KEY(account_id)
                                  REFERENCES account(id),
                              FOREIGN KEY(authority_id)
                                  REFERENCES authority(id));

DROP TABLE role, account_role;