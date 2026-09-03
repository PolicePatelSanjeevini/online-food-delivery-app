USE food_delivery;

INSERT INTO users (full_name, email, password, phone, role, active, created_at)
VALUES
    ('System Admin', 'admin@fooddelivery.local', '$2a$10$grVoVH4Y1K2uCeKyFW5yc.T3ChfwHQIXR2xGlVSzpW6ML2m3Hvuvy', '9000000000', 'ADMIN', TRUE, CURRENT_TIMESTAMP),
    ('Demo Owner', 'owner@fooddelivery.local', '$2a$10$qNdR7jagfQ1xqFB6D9KX9eHEKWoRLGv5GLPJDVxGYbQptOb1OvSV2', '9000000001', 'RESTAURANT_OWNER', TRUE, CURRENT_TIMESTAMP),
    ('Demo Customer', 'customer@fooddelivery.local', '$2a$10$6kpn8jkJyhyi1Kfnle978.NklLu7YMCkF2L6cf8LlnrCehGSrPl5.', '9000000002', 'CUSTOMER', TRUE, CURRENT_TIMESTAMP)
ON DUPLICATE KEY UPDATE full_name = VALUES(full_name), password = VALUES(password), active = VALUES(active);

INSERT INTO categories (name, description)
VALUES
    ('Pizza', 'Freshly baked pizzas'),
    ('Burgers', 'Classic and specialty burgers'),
    ('Indian', 'Popular Indian dishes'),
    ('Desserts', 'Sweet treats and desserts'),
    ('Healthy', 'Fresh bowls, wraps, and salads')
ON DUPLICATE KEY UPDATE description = VALUES(description);

INSERT INTO restaurants (owner_id, name, description, address, phone, cuisine_type, rating, is_open, created_at)
SELECT id, 'Spice Garden', 'Comforting Indian meals made fresh.', '12 Market Street', '9000000010', 'Indian', 4.50
    , TRUE, CURRENT_TIMESTAMP
FROM users WHERE email = 'owner@fooddelivery.local'
AND NOT EXISTS (SELECT 1 FROM restaurants WHERE name = 'Spice Garden');

INSERT INTO restaurants (owner_id, name, description, address, phone, cuisine_type, rating, is_open, created_at)
SELECT id, 'Burger Bay', 'Smash burgers, crisp fries, and thick shakes.', '8 River Road', '9000000011', 'Burgers & Fast Food', 4.40, TRUE, CURRENT_TIMESTAMP
FROM users WHERE email = 'owner@fooddelivery.local'
AND NOT EXISTS (SELECT 1 FROM restaurants WHERE name = 'Burger Bay');

INSERT INTO restaurants (owner_id, name, description, address, phone, cuisine_type, rating, is_open, created_at)
SELECT id, 'Tandoori Tales', 'Clay-oven classics and slow-cooked North Indian comfort.', '19 Palace Lane', '9000000012', 'North Indian', 4.70, TRUE, CURRENT_TIMESTAMP
FROM users WHERE email = 'owner@fooddelivery.local'
AND NOT EXISTS (SELECT 1 FROM restaurants WHERE name = 'Tandoori Tales');

INSERT INTO restaurants (owner_id, name, description, address, phone, cuisine_type, rating, is_open, created_at)
SELECT id, 'Green Bowl', 'Bright, balanced meals for busy days.', '3 Garden Street', '9000000013', 'Healthy Food', 4.50, TRUE, CURRENT_TIMESTAMP
FROM users WHERE email = 'owner@fooddelivery.local'
AND NOT EXISTS (SELECT 1 FROM restaurants WHERE name = 'Green Bowl');

INSERT INTO restaurants (owner_id, name, description, address, phone, cuisine_type, rating, is_open, created_at)
SELECT id, 'Pizza Planet', 'Wood-fired pizzas and Italian favourites.', '77 Station Avenue', '9000000014', 'Pizza & Italian', 4.30, TRUE, CURRENT_TIMESTAMP
FROM users WHERE email = 'owner@fooddelivery.local'
AND NOT EXISTS (SELECT 1 FROM restaurants WHERE name = 'Pizza Planet');

INSERT INTO restaurants (owner_id, name, description, address, phone, cuisine_type, rating, is_open, created_at)
SELECT id, 'Sweet Cravings', 'Small-batch cakes, brownies, and celebration bakes.', '5 Blossom Road', '9000000015', 'Desserts', 4.80, TRUE, CURRENT_TIMESTAMP
FROM users WHERE email = 'owner@fooddelivery.local'
AND NOT EXISTS (SELECT 1 FROM restaurants WHERE name = 'Sweet Cravings');

INSERT INTO food_items (restaurant_id, category_id, name, description, price, available, created_at)
SELECT r.id, c.id, 'Paneer Butter Masala', 'Paneer in a rich tomato and butter gravy.', 220.00
    , TRUE, CURRENT_TIMESTAMP
FROM restaurants r CROSS JOIN categories c
WHERE r.name = 'Spice Garden' AND c.name = 'Indian'
AND NOT EXISTS (SELECT 1 FROM food_items WHERE name = 'Paneer Butter Masala');

INSERT INTO food_items (restaurant_id, category_id, name, description, price, available, created_at)
SELECT r.id, c.id, 'Gulab Jamun', 'Soft milk-solid dumplings in sugar syrup.', 90.00
    , TRUE, CURRENT_TIMESTAMP
FROM restaurants r CROSS JOIN categories c
WHERE r.name = 'Spice Garden' AND c.name = 'Desserts'
AND NOT EXISTS (SELECT 1 FROM food_items WHERE name = 'Gulab Jamun');

INSERT INTO food_items (restaurant_id, category_id, name, description, price, available, created_at)
SELECT r.id, c.id, 'Classic Chicken Burger', 'Juicy chicken patty with lettuce and house sauce.', 199.00, TRUE, CURRENT_TIMESTAMP
FROM restaurants r CROSS JOIN categories c WHERE r.name = 'Burger Bay' AND c.name = 'Burgers'
AND NOT EXISTS (SELECT 1 FROM food_items WHERE name = 'Classic Chicken Burger');

INSERT INTO food_items (restaurant_id, category_id, name, description, price, available, created_at)
SELECT r.id, c.id, 'Crispy Veg Burger', 'Crunchy vegetable patty with fresh slaw.', 149.00, TRUE, CURRENT_TIMESTAMP
FROM restaurants r CROSS JOIN categories c WHERE r.name = 'Burger Bay' AND c.name = 'Burgers'
AND NOT EXISTS (SELECT 1 FROM food_items WHERE name = 'Crispy Veg Burger');

INSERT INTO food_items (restaurant_id, category_id, name, description, price, available, created_at)
SELECT r.id, c.id, 'Chicken Tikka', 'Charred yoghurt-marinated chicken pieces.', 260.00, TRUE, CURRENT_TIMESTAMP
FROM restaurants r CROSS JOIN categories c WHERE r.name = 'Tandoori Tales' AND c.name = 'Indian'
AND NOT EXISTS (SELECT 1 FROM food_items WHERE name = 'Chicken Tikka');

INSERT INTO food_items (restaurant_id, category_id, name, description, price, available, created_at)
SELECT r.id, c.id, 'Dal Makhani', 'Slow-cooked black lentils finished with butter.', 180.00, TRUE, CURRENT_TIMESTAMP
FROM restaurants r CROSS JOIN categories c WHERE r.name = 'Tandoori Tales' AND c.name = 'Indian'
AND NOT EXISTS (SELECT 1 FROM food_items WHERE name = 'Dal Makhani');

INSERT INTO food_items (restaurant_id, category_id, name, description, price, available, created_at)
SELECT r.id, c.id, 'Paneer Power Bowl', 'Paneer, grains, greens, and roasted vegetables.', 249.00, TRUE, CURRENT_TIMESTAMP
FROM restaurants r CROSS JOIN categories c WHERE r.name = 'Green Bowl' AND c.name = 'Healthy'
AND NOT EXISTS (SELECT 1 FROM food_items WHERE name = 'Paneer Power Bowl');

INSERT INTO food_items (restaurant_id, category_id, name, description, price, available, created_at)
SELECT r.id, c.id, 'Veggie Wrap', 'Fresh vegetables, hummus, and herbs in a warm wrap.', 179.00, TRUE, CURRENT_TIMESTAMP
FROM restaurants r CROSS JOIN categories c WHERE r.name = 'Green Bowl' AND c.name = 'Healthy'
AND NOT EXISTS (SELECT 1 FROM food_items WHERE name = 'Veggie Wrap');

INSERT INTO food_items (restaurant_id, category_id, name, description, price, available, created_at)
SELECT r.id, c.id, 'Margherita Pizza', 'Tomato, mozzarella, and basil on a crisp base.', 249.00, TRUE, CURRENT_TIMESTAMP
FROM restaurants r CROSS JOIN categories c WHERE r.name = 'Pizza Planet' AND c.name = 'Pizza'
AND NOT EXISTS (SELECT 1 FROM food_items WHERE name = 'Margherita Pizza');

INSERT INTO food_items (restaurant_id, category_id, name, description, price, available, created_at)
SELECT r.id, c.id, 'Farmhouse Pizza', 'Peppers, onions, mushrooms, and sweet corn.', 329.00, TRUE, CURRENT_TIMESTAMP
FROM restaurants r CROSS JOIN categories c WHERE r.name = 'Pizza Planet' AND c.name = 'Pizza'
AND NOT EXISTS (SELECT 1 FROM food_items WHERE name = 'Farmhouse Pizza');

INSERT INTO food_items (restaurant_id, category_id, name, description, price, available, created_at)
SELECT r.id, c.id, 'Chocolate Brownie', 'Warm fudgy brownie with a rich chocolate centre.', 129.00, TRUE, CURRENT_TIMESTAMP
FROM restaurants r CROSS JOIN categories c WHERE r.name = 'Sweet Cravings' AND c.name = 'Desserts'
AND NOT EXISTS (SELECT 1 FROM food_items WHERE name = 'Chocolate Brownie');

INSERT INTO food_items (restaurant_id, category_id, name, description, price, available, created_at)
SELECT r.id, c.id, 'Red Velvet Cake', 'Velvety cocoa sponge with cream cheese frosting.', 179.00, TRUE, CURRENT_TIMESTAMP
FROM restaurants r CROSS JOIN categories c WHERE r.name = 'Sweet Cravings' AND c.name = 'Desserts'
AND NOT EXISTS (SELECT 1 FROM food_items WHERE name = 'Red Velvet Cake');

INSERT INTO addresses (user_id, label, address_line, city, state, postal_code, is_default)
SELECT id, 'Home', '42 Demo Avenue', 'Bengaluru', 'Karnataka', '560001', TRUE
FROM users WHERE email = 'customer@fooddelivery.local'
AND NOT EXISTS (SELECT 1 FROM addresses a JOIN users u ON a.user_id = u.id WHERE u.email = 'customer@fooddelivery.local');

INSERT INTO cart (user_id, updated_at)
SELECT id, CURRENT_TIMESTAMP FROM users
WHERE email = 'customer@fooddelivery.local'
AND NOT EXISTS (SELECT 1 FROM cart c WHERE c.user_id = users.id);
