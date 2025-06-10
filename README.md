# 🎾 Tennis Scoreboard

A web application for managing tennis matches in real-time. Users can create new matches, score points interactively, and view finished matches with pagination and filtering.

---

## 🧩 Tech Stack

- **Java 21**
- **Jakarta Servlets & JSP**
- **Hibernate ORM (JPA)**
- **H2 In-Memory Database**
- **JSTL** for templating logic
- **Maven** for build management
- **Logback** for logging
- **Tomcat 11** as the servlet container

---

## 🚀 Features

- 🎮 Start new matches and track the score dynamically.
- 🧮 Match logic includes DEUCE, ADVANTAGE, TIE-BREAK rules.
- 📋 Save completed matches to a persistent store.
- 🔍 View past matches with **pagination** and **filtering by player name**.
- 📄 Error handling and logging with stack trace support.
- 🧪 Built-in validation for user input and match rules.

---

## 🧪 How It Works

### 🏁 Start a Match

- Go to `/new-match`
- Enter player names → Creates an `OngoingMatch` and redirects to `/match-score?uuid=...`

### 🎯 Score Points

- Press “Score” for a player → triggers AJAX `POST` to `/match-score`
- JavaScript updates scoreboard live via `fetch` with returned JSON

### 🏆 Save Finished Match

- Once a match finishes (2 sets), it’s saved via Hibernate to `Matches` table and removed from memory.

---

## 📊 Pagination & Filtering

- `/matches?page=X&filter_by_player_name=Name`
- Server-side logic calculates page count, applies name filter
- Pagination bar displays `1 2 3 ... N` format
- Preserves filters and resets gracefully

---

## 🔎 Useful Endpoints

| URL                         | Description                       |
|----------------------------|-----------------------------------|
| `/`                        | Home page                         |
| `/new-match`               | Create a new match                |
| `/match-score?uuid=...`    | View and score ongoing match      |
| `/matches?page=X&filter_by_player_name=Y` | View finished matches |

---

## 🧰 How to Run

1. **Import in IntelliJ or any IDE**
2. **Install Tomcat 11+**
3. **Build with Maven**
4. **Deploy as WAR or Run via Tomcat inside IDE**

