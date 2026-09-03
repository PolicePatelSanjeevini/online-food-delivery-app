# REST API Reference

Base URL: `http://localhost:8080/api`

The demo frontend sends `X-User-Id` for customer-specific endpoints. Passwords are BCrypt-hashed; production authentication should additionally use access tokens and role-based request authorization.

## Authentication

- `POST /auth/register` creates a customer. Body: `{"fullName":"Asha","email":"asha@example.com","password":"secret123","phone":"9000000000"}`
- `POST /auth/login` returns the user profile. Body: `{"email":"customer@fooddelivery.local","password":"customer123"}`
- `POST /auth/owner/register` creates an owner account and its restaurant in one transaction. Body: `{"ownerName":"Ravi","email":"ravi@example.com","password":"secret123","phone":"9000000011","restaurantName":"Ravi's Kitchen","restaurantDescription":"Fresh regional food","cuisineType":"Indian","restaurantImage":"https://example.com/restaurant.jpg","address":"10 Main Road"}`
- `GET /categories` lists menu categories for owner menu management.

## Restaurants and food

- `GET /restaurants?search=indian` lists or searches restaurants.
- `GET /restaurants/{id}` gets one restaurant.
- `GET /restaurants/owner/{ownerId}` lists restaurants owned by an owner.
- `POST /restaurants` creates a restaurant. Body: `{"ownerId":2,"name":"New Kitchen","address":"1 Main Road"}`
- `PUT /restaurants/{id}` updates a restaurant.
- `DELETE /restaurants/{id}` deletes a restaurant.
- `GET /food-items?restaurantId=1` lists available menu items.
- `GET /food-items/search?name=paneer` searches available menu items.
- `POST /food-items` creates an item. Body: `{"restaurantId":1,"categoryId":3,"name":"Dal Tadka","price":180}`
- `PUT /food-items/{id}` updates an item, including `available:false`.
- `DELETE /food-items/{id}` deletes an item.

## Customer workflow

- `GET /addresses` lists addresses. Header: `X-User-Id: 3`.
- `POST /addresses` creates an address. Body: `{"label":"Home","addressLine":"42 Demo Avenue","city":"Bengaluru","state":"Karnataka","postalCode":"560001","defaultAddress":true}`
- `GET /cart` reads the current cart.
- `POST /cart/items` adds an item. Body: `{"foodItemId":1,"quantity":2}`
- `PUT /cart/items/{cartItemId}?quantity=3` changes quantity.
- `DELETE /cart/items/{cartItemId}` removes an item.
- `POST /orders` checks out the cart. Body: `{"addressId":1,"notes":"Ring the bell"}`
- `GET /orders` lists the current user's orders.

## Owner and admin workflow

- `GET /admin/stats` returns counts for users, restaurants, menu items, total orders, placed orders, and delivered orders.
- `GET /orders/restaurant/{restaurantId}` lists incoming orders.
- `GET /orders/all` lists all orders for admin review.
- `PATCH /orders/{id}/status?status=PREPARING` updates status. Valid statuses are `PLACED`, `ACCEPTED`, `PREPARING`, `OUT_FOR_DELIVERY`, `DELIVERED`, and `CANCELLED`.
