ALTER TABLE cart
    ALTER COLUMN delivery_status TYPE INTEGER USING delivery_status::integer;
ALTER TABLE cart
    ALTER COLUMN delivery_status SET DEFAULT 0;