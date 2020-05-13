INSERT INTO public.category(
    id, name)
VALUES (0, 'iPad'),
       (1, 'iPhone'),
       (2, 'Watch'),
       (3, 'Music'),
       (4, 'Mac');

INSERT INTO public.model(
    id, category_id, name)
VALUES (0, 4, 'Mac Book Pro 13'),
       (1, 4, 'Mac Book Air 2018'),
       (2, 2, 'Apple Watch 5'),
       (3, 0, 'iPad 2020');

INSERT INTO public.color(
    id, name)
VALUES (0, 'white'),
       (1, 'black'),
       (2, 'gray');

INSERT INTO public.memory(
    id, volume)
VALUES (0, 32),
       (1, 64),
       (2, 128),
       (3, 256);