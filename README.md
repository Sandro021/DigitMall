✨ Features
👤 User (Consumer) App
Authentication: Secure login and registration.

Shopping Experience: Browse available shops, view detailed item listings, and add products to a personal shopping cart.

Seamless Checkout: Integrated with Google Pay for fast and secure transactions.

Profile Management: Users can customize their experience by editing their nicknames and uploading custom avatars.

🏢 Company (Merchant) App
Store Creation: Register and set up a digital storefront within the mall.

Inventory Management: Add, edit, and manage store items and icons.

Marketing & Engagement: Upload commercial videos directly from the device gallery to promote the store.

📱 Global Features
Commercial Video Feed: A scrolling feed displaying commercial videos uploaded by registered companies to drive user engagement.

🏗️ Architecture & Tech Stack
This project is built with scalability, testability, and maintainability in mind, heavily adhering to modern Android development best practices.

Architecture Pattern: Clean Architecture combined with the MVI (Model-View-Intent) presentation pattern for predictable state management.

Project Structure: Modularization by Feature. The app is divided into independent feature modules to improve build times, enforce separation of concerns, and allow for scalable team development.

Dependency Injection: Dagger Hilt for managing dependencies across modules and layers seamlessly.

🛠️ Tools & Libraries
UI: Jetpack Compose (100% declarative UI).

Authentication: Firebase Authentication.

Database (Real-time): Firebase Firestore (user data, profiles, and state).

Backend & API: MockApi.io for mocking backend responses, uploading stores, items, and assets.

Payments: Google Pay API.

🧪 Testing
The project is designed to be highly testable thanks to Clean Architecture and dependency injection.

Unit Testing: Implemented using Mockk for mocking dependencies, ensuring business logic in Use Cases and ViewModels is fully verified.
