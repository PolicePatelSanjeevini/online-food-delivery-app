# Online Food Delivery Application

A full-stack Java and MySQL food delivery application.

See [API.md](API.md) for the REST endpoint reference and example request bodies.

## Current setup

The initial Spring Boot backend is in `backend/` and exposes:

```text
GET http://localhost:8080/api/health
```

Expected response:

```json
{
  "status": "UP",
  "application": "online-food-delivery"
}
```

## Requirements

- Java 17 or newer
- Maven 3.9 or newer
- MySQL 8 or newer

## Run the backend

### One-command startup

From the project root, run:

```powershell
powershell -ExecutionPolicy Bypass -File .\start-zestora.ps1
```

This starts any missing backend/frontend server and opens the Zestora home page.

From the `backend` directory:

```text
mvn spring-boot:run
```

If PowerShell says `mvn` is not recognized, Maven is installed but not on your PATH. Run this from the project root:

```powershell
$env:Path += ";C:\Users\sanje\.maven\maven-3.9.16\bin"
cd backend
mvn spring-boot:run
```

Alternatively, run Maven without changing PATH:

```powershell
& "C:\Users\sanje\.maven\maven-3.9.16\bin\mvn.cmd" -f "backend\pom.xml" spring-boot:run
```

Database credentials can be supplied through `DB_URL`, `DB_USERNAME`, and `DB_PASSWORD` environment variables. The default database is `food_delivery` on local MySQL.

## Set up MySQL

Run the following commands from the project root, replacing `YOUR_PASSWORD` with the password for your local MySQL user:

```text
mysql -u root -p < backend/src/main/resources/schema.sql
mysql -u root -p < backend/src/main/resources/data.sql
```

The scripts create the `food_delivery` database, all relational tables, foreign keys, indexes, and demo records. Demo login records are included for development only:

```text
admin@fooddelivery.local / admin123
owner@fooddelivery.local / owner123
customer@fooddelivery.local / customer123
```

Passwords are stored as BCrypt hashes. These demo credentials are for local development only.

## Open the frontend

Start a second PowerShell terminal from the project root and run:

```powershell
python -m http.server 5500 --directory frontend
```

Open this URL in your browser:

```text
http://localhost:5500/index.html
```

The frontend calls the backend at `http://localhost:8080/api`. Do not open the HTML file directly with `file://`; use the local server command above.

## Restaurant owner workflow

1. Open `http://localhost:5500/restaurant-register.html`.
2. Register the owner and restaurant details.
3. Open `http://localhost:5500/restaurant-login.html`.
4. Sign in to reach the owner dashboard.
5. Add menu items with database-backed prices and categories.

The dashboard uses `POST /api/auth/owner/register`, `GET /api/restaurants/owner/{ownerId}`, `GET /api/categories`, and `POST /api/food-items`.

## Public deployment

The application is currently local-only. A permanent public URL requires deploying the Spring Boot backend and MySQL database to a hosting provider, then changing the frontend `API` URL in `frontend/js/app.js` to the deployed backend URL. Do not expose the local MySQL password or use the development credentials in production.
