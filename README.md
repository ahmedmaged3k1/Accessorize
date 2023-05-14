# Accessorize

## Description

The project is an e-commerce try-on application developed for Android devices using Kotlin. It allows users to try on face accessories through augmented reality and provides various e-commerce features such as product search and cart operations. The application follows the three-tier architecture pattern and is implemented using the MVVM (Model-View-ViewModel) architecture.

### System Architecture
![Screenshot 2023-05-14 204956](https://github.com/ahmedmaged3k1/Accessorize/assets/60134186/e968b70b-612a-4e42-80b2-1135f2374ef1)

The three-tier architecture separates the application into three layers: the presentation layer, the application logic layer, and the data management layer. This separation promotes code organization, modularity, scalability, reusability, and security. The presentation layer handles the user interface and augmented reality components, the application logic layer manages the business logic and authentication, and the data management layer handles data storage and retrieval from local and remote sources.



### MVVM Architecture 
![mvvm](https://user-images.githubusercontent.com/60134186/185244558-c3916e54-5300-4fdd-9667-bfb946ed0130.png)

The MVVM architecture pattern further enhances the separation of concerns by introducing the ViewModel as a middle layer. The ViewModel is responsible for preparing and managing data for the View, while the View is responsible for displaying data and reacting to user input. The Model represents the data source, which is managed by the data management layer. The Repository pattern abstracts the data access layer, providing a clean API to the ViewModel, and the Observer pattern enables data updates and notifications between the ViewModel and the View.

Features
-Augmented reality try-on feature for face accessories.
-E-commerce features including product search and cart operations.
-User authentication for secure access to application features.
-Separation of concerns with three-tier architecture: presentation, application logic, and data management layers.
-Modularity for easy feature additions and changes.
-Implementation in Android using Kotlin programming language.
## Installation
-Clone the repository to your local machine.
-Open the project in Android Studio.
-Connect an Android device
-Build and run the application from Android Studio.
## Usage
-Launch the application on your Android device.
-Explore the user interface to browse face accessories and product offerings.
-Use the augmented reality try-on feature to visualize accessories on your face.
-Perform product searches and add desired items to the cart.
-Proceed to the cart for checkout and purchase operations.

## Technologies Used
-Augmented Reality
-MVVM Architecture
-Coroutines
-Room Database
-Navigation Component
-Dependency Injection
-Retrofit

RESTful API
Observer Pattern
Repository Pattern
Contributors
-Ahmed Maged
-Noran Mohamed
-Nouran Ibrahim
-Tarek El Shawaf
-Sara Ahmed
-Ziad Tarek
## License
This project is licensed un
