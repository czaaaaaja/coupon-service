INSERT INTO coupons ("CODE", country, creation_date, max_usages, usage_count, "VERSION", id)
VALUES ('CODE', 'PL', DATE '2026-05-28', 10, 0, 0, '6c039c4f-e2ae-465c-9467-18f30db3ecf5'),
('USED_CODE', 'PL', DATE '2026-05-28', 10, 10, 0, '9d031d7b-e1ea-4ba1-baf3-253c221734c4');

INSERT INTO usages (coupon_id, user_id, id)
VALUES ('6c039c4f-e2ae-465c-9467-18f30db3ecf5', 'SOME_GUY', '6c039c4f-e2ae-465c-9467-18f30db3ecf5');