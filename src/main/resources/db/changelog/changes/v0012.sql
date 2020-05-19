ALTER TABLE account_authorities
    RENAME COLUMN account_id TO users_id;

ALTER TABLE account
    RENAME TO "user";

ALTER TABLE cart
    RENAME COLUMN account_id TO users_id;