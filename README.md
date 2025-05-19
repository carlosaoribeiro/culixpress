# 🍽️ CuliXpress

An Android app for discovering, viewing, saving, and sharing cooking recipes. CuliXpress is designed for culinary enthusiasts who want a modern, smooth, and organized way to manage their favorite recipes and find inspiration for new dishes.

---

## 🎯 Inspiration Behind the Name

**CuliXpress** is a fusion of “Culinary” and “Express,” representing the app’s goal: to deliver fast, accessible, and delightful cooking experiences. Whether you’re a beginner or a chef, CuliXpress helps you find, save, and share your favorite recipes with ease.

---

## 🩹 Problem We're Solving

Cooking inspiration is everywhere, but often scattered and hard to save or revisit. People rely on screenshots, social media posts, or unorganized links to collect recipes.  
**CuliXpress** solves this by offering:

- 📚 A clean recipe catalog with search and filters  
- 🔐 Secure login and account creation  
- 💾 Local storage of favorite recipes  
- 📤 Easy recipe sharing  
- 📱 A modern, intuitive interface built with best practices  

---

## ✅ Features

- 🔐 User authentication (sign-up, login, and password recovery)  
- 📋 Recipe list fetched from external API  
- 🍽️ Detailed recipe view with ingredients and preparation steps  
- 🧾 Local storage with Room Database  
- 📤 Share recipes via external apps  
- 🌙 Clean UI with modern architecture (MVVM + Navigation Component)  
- 💬 Uses LiveData, ViewModel, and Coroutines for reactive and smooth state management  

---

## 🧪 Requirements

- Android Studio (recommended: Hedgehog or newer)  
- Minimum Android SDK: 24  
- Firebase Authentication (or your own backend)  
- API providing recipe data  
- Required permissions:
  - `INTERNET`
  - `ACCESS_NETWORK_STATE`

---

## 🖼️ Screenshots

<div align="center">
  <img src="https://github.com/user-attachments/assets/28e17e71-0330-47cf-ba9d-3fc296f84806" width="180"/>
  <img src="https://github.com/user-attachments/assets/36e1aab3-3319-4f1d-993b-fa16a918668f" width="180"/>
  <img src="https://github.com/user-attachments/assets/49166c13-31d0-4c1e-9e95-109f0fe36073" width="180"/>
</div>

---

## 🚀 How to Use

1. Launch the app and log in or sign up  
2. Browse the recipe catalog or search for specific dishes  
3. Tap a recipe to view detailed instructions  
4. Mark it as favorite to save it locally  
5. Tap share to send it via WhatsApp, email, or other apps  

---

## 🧱 Main Screens

- `SplashActivity`: App startup screen  
- `LoginFragment`: User login interface  
- `RegisterFragment`: Sign-up form  
- `ForgotPasswordFragment`: Password recovery  
- `HomeFragment`: Recipe list view  
- `RecipeDetailsFragment`: Full recipe details and preparation steps  
- `FavoritesFragment`: Locally saved recipes  
- `ShareIntent`: Share recipe with others  

---

## 🛠️ Technologies Used

- Kotlin  
- MVVM Architecture  
- Retrofit & OkHttp  
- Room Database  
- Coroutines  
- LiveData & ViewModel  
- Navigation Component  
- Hilt (for dependency injection)  
- Material Design  

---

## 📁 Local Storage Structure (Room)

- `RecipeEntity`  
  - Fields: `id`, `title`, `image`, `ingredients`, `instructions`, `isFavorite`  

- Recipes marked as favorite are saved locally for offline access

---

## 🔗 References

- [Android Architecture Components](https://developer.android.com/topic/libraries/architecture)  
- [Retrofit](https://square.github.io/retrofit/)  
- [Room Database](https://developer.android.com/training/data-storage/room)  
- [Kotlin Coroutines](https://developer.android.com/kotlin/coroutines)

---

## 📥 Installation & Setup

```bash
git clone https://github.com/<your-username>/culixpress.git
cd culixpress
