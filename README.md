# Finova 💰

<p align="center">💰 Finova - Smart Finance AI</p>
<p align="center"> <img src="https://img.shields.io/badge/Kotlin-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white" alt="Kotlin" /> <img src="https://img.shields.io/badge/Jetpack%20Compose-4285F4?style=for-the-badge&logo=jetpackcompose&logoColor=white" alt="Jetpack Compose" /> <img src="https://img.shields.io/badge/Google%20Gemini-8E75B2?style=for-the-badge&logo=googlegemini&logoColor=white" alt="Google Gemini" /> <img src="https://img.shields.io/badge/Firebase-FFCA28?style=for-the-badge&logo=firebase&logoColor=black" alt="Firebase" /> </p>

<p align="center"> <a href="https://www.linkedin.com/in/your-profile-link"> <img src="https://img.shields.io/badge/LinkedIn-Connect-blue?style=flat-square&logo=linkedin" alt="LinkedIn" /> </a> <a href="mailto:your.email@example.com"> <img src="https://img.shields.io/badge/Email-Contact-red?style=flat-square&logo=gmail" alt="Email" /> </a> <img src="https://img.shields.io/github/stars/YourUsername/Finova?style=flat-square&color=gold" alt="GitHub Stars" /> <img src="https://img.shields.io/github/forks/YourUsername/Finova?style=flat-square&color=blue" alt="GitHub Forks" /> </p>

Finova is a native Android application built with **Kotlin** and **Jetpack Compose**, designed to revolutionize personal finance management. By integrating **Google Gemini AI**, Finova transcends traditional expense tracking, providing users with a conversational financial coach that offers real-time insights and personalized advice.

---

## 🏗 Architecture & Design
Finova is engineered using **Clean Architecture** principles and the **MVVM (Model-View-ViewModel)** design pattern. This ensures a scalable, testable, and maintainable codebase.

* **UI Layer:** Built entirely with **Jetpack Compose** using **Material 3** design standards for a modern look and feel.
* **Business Logic:** Decoupled from the UI via **ViewModels**, ensuring reactive state management.
* **Data Layer:** Implements the **Repository Pattern** to abstract data sources, providing a clean API for the rest of the app to consume.

---

## 🛠 Tech Stack
* **Language:** Kotlin
* **UI Framework:** Jetpack Compose (Material 3)
* **Navigation:** Jetpack Navigation Compose
* **AI Engine:** Google Gemini (Generative AI SDK) using the `gemini-1.5-flash` model
* **Backend & Database:** * **Firebase Authentication:** Secure user identity and session management.
    * **Cloud Firestore:** Real-time NoSQL database for structured financial data.
* **Networking:** Retrofit & Gson (infrastructure ready for external API extensibility).
* **Image Loading:** Coil.

---

## ✨ Key Features
* **FinovaAI Chat:** A sophisticated financial assistant powered by **Google Gemini**. It uses a custom system prompt to provide budgeting tips and financial guidance.
* **Comprehensive Dashboard:** A central hub showing a complete financial overview of the user's accounts.
* **Transaction Management:** Full CRUD (Create, Read, Update, Delete) capabilities for tracking income and expenses.
* **Savings Goals:** Tools to create, manage, and track progress toward specific financial milestones.
* **User Profiles:** Management of user-specific data, feedback, and security settings.

---

## 📂 Project Structure
The project is organized into logical modules to separate concerns:

* **`ui/`**: Contains over 20+ Composable screens including Login, Dashboard, and Chat.
* **`viewmodels/`**: Manages UI state, including `ChatViewModel.kt` for AI interactions and `LoginViewModel` for auth logic.
* **`data/model/`**: Defines data entities like `User`, `Goal`, and `Transaction`.
* **`data/repository/`**: 
    * `FirestoreRepository`: Handles all database operations.
    * `AuthRepository`: Manages user authentication sessions.
* **`navigation/`**: Centralized routing via `NavGraph.kt` and `Screen.kt`.

---

## 🔒 Security & Privacy
* **Data Isolation:** The Firestore schema is structured under `users/{userId}/`, ensuring users only access their own private data.
* **Authentication:** Powered by Firebase Auth to ensure industry-standard security for logins and sign-ups.

---

## 🚀 Setup & Installation
1. **Clone the Repo:** `git clone https://github.com/YourUsername/Finova.git`
2. **Firebase Setup:** Add your `google-services.json` file to the `/app` directory.
3. **API Keys:** Securely add your **Google Gemini API Key** to the project (e.g., via `local.properties`).
4. **Build:** Open in Android Studio and sync with Gradle.

---

## 👨‍💻 Author
**Asma Channa**

---

## 📩 Contact & Connect
I'm always open to discussing Android development, AI integration, or potential collaborations. 

* **LinkedIn:** [Asma Channa](https://www.linkedin.com/in/iasmachanna/)
* **Email:** [Mail Me](mailto:asmachanna.becsef22@iba-suk.edu.pk)
* **GitHub:** [Asma Channa](https://github.com/asma-13)

---

### License
Distributed under the MIT License. See `LICENSE` for more information.
