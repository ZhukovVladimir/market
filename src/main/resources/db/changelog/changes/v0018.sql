INSERT INTO public."user"(id, password, email, first_name, last_name, phone, address, phone_country_code, deleted)
VALUES (0, 'root', 'admin@mail.ru', 'admin', 'admin', 9209999999, 'address', 7, false);
INSERT INTO public.authority(id, authority)
VALUES (0, 'ROLE_ADMIN');
INSERT INTO public.authority(id, authority)
VALUES (1, 'ROLE_USER');
INSERT INTO public.user_authorities(users_id, authorities_id)
VALUES (0, 0);