# 📊 Maths Game API & App
A full-stack maths quiz game with an interactive front-end and a powerful backend service that manages leaderboard and performance data, hosted on Azure with Swagger UI support.

---

## 🧠 Project Overview
This project is a maths quiz game with backend services that manage two core data tables:
- Leaderboard Table – Stores top scores per level.
- Level Summary Table – Tracks overall performance stats per level.

If no data is present on initialization, the service creates:
- 40 placeholder entries in the leaderboard (10 per difficulty level: Easy, Medium, Hard, Very Hard)
- 4 level summary entries (one per level)

---

## 📱 App Screens
- **Home Screen** → Start quiz  
- **Pick Level Screen** → Choose difficulty (Easy, Medium, Hard, Very Hard)  
- **Quiz Screen** → 10 random math questions per level  
- **Answer Feedback** → Instant correct/incorrect messages  
- **Leaderboard Entry Prompt** → If score qualifies, prompt user to enter name  
- **Leaderboard Screen** → Displays all entries, grouped by level, sorted by:
  - Highest score  
  - If scores are equal → lowest time  

### Internationalization 🌍
- App is available in **English** and **Spanish**.  

---

## 📸 Screenshots
<p align="center">
  <img src="https://github.com/user-attachments/assets/261e1be0-05b1-4450-b409-5dde19ce6d43" width="200" />
  <img src="https://github.com/user-attachments/assets/7e65ff72-cd14-40f0-84f8-4bf287b401ad" width="200" />
  <img src="https://github.com/user-attachments/assets/9166e04c-0f06-4e1c-a6a1-de7a77807d13" width="200" />
  <img src="https://github.com/user-attachments/assets/16c7d990-523c-4ac2-b8d5-82300b6494ea" width="200" />
</p>


---

## ✅ Features Summary
- Dynamic **leaderboard system** with auto-seeding.  
- **Level progression** with increasing difficulty.  
- **Real-time feedback** on answers.  
- **Leaderboard validation** (only high scores enter).  
- **Internationalization support**.  
- **Automated testing pipeline**.  
