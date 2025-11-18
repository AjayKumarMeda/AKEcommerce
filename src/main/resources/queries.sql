INSERT INTO cart_items(cart_id, product_id, quantity)
VALUES(?, ?, ?) ON CONFLICT quantity = quantity + ?