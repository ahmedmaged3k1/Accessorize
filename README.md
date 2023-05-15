# Accessorize

## Description

The project is an e-commerce try-on application developed for Android devices using Kotlin. It allows users to try on face accessories through augmented reality and provides various e-commerce features such as product search and cart operations. The application follows the three-tier architecture pattern and is implemented using the MVVM (Model-View-ViewModel) architecture.

### System Architecture
![Screenshot 2023-05-14 204956](https://github.com/ahmedmaged3k1/Accessorize/assets/60134186/e968b70b-612a-4e42-80b2-1135f2374ef1)

The three-tier architecture separates the application into three layers: the presentation layer, the application logic layer, and the data management layer. This separation promotes code organization, modularity, scalability, reusability, and security. The presentation layer handles the user interface and augmented reality components, the application logic layer manages the business logic and authentication, and the data management layer handles data storage and retrieval from local and remote sources.



### MVVM Architecture 
![mvvm](https://user-images.githubusercontent.com/60134186/185244558-c3916e54-5300-4fdd-9667-bfb946ed0130.png)

The MVVM architecture pattern further enhances the separation of concerns by introducing the ViewModel as a middle layer. The ViewModel is responsible for preparing and managing data for the View, while the View is responsible for displaying data and reacting to user input. The Model represents the data source, which is managed by the data management layer. The Repository pattern abstracts the data access layer, providing a clean API to the ViewModel, and the Observer pattern enables data updates and notifications between the ViewModel and the View.

## Features
- Augmented reality try-on feature for face accessories.
- E-commerce features including product search and cart operations.
- User authentication for secure access to application features.
- Separation of concerns with three-tier architecture: presentation, application logic, and data management layers.
- Modularity for easy feature additions and changes.
- Implementation in Android using Kotlin programming language.
## Installation
- Clone the repository to your local machine.
- Open the project in Android Studio.
- Connect an Android device
- download the backend server and run it according to the following steps https://github.com/Nouran-Elbagory/GraduationProject
- Build and run the application from Android Studio.
## Usage
- Launch the application on your Android device.
- Explore the user interface to browse face accessories and product offerings.
- Use the augmented reality try-on feature to visualize accessories on your face.
- Perform product searches and add desired items to the cart.
- Proceed to the cart for checkout and purchase operations.

## Technologies Used
- Augmented Reality
- MVVM Architecture
- Coroutines
- Room Database
- Navigation Component
- Dependency Injection
- Retrofit
- RESTful API
- Observer Pattern
- Repository Pattern

## Modules

The Accessorize app is divided into three main modules: 
- Admin 
- Seller  
- User
Each module focuses on different operations and functionalities. Here's a brief overview of each module:

### 1. Admin

The admin module is responsible for managing the overall administration of the Acessorize application. It includes operations such as:

- Accessing and analyzing sales and user data.
- Generating reports and insights for business analysis.

Github Repository : https://github.com/ziadtarekfa/AccessorizeAdmin

### 2. Seller

The seller module is designed to facilitate the activities of sellers who wish to showcase and sell their accessories on the Acessorize platform. Key functionalities of the seller module include:

- Adding, editing, and deleting product listings.
- Managing inventory and stock levels.
- Handling order fulfillment and shipping details.
- Tracking sales and revenue.

Github Repository :

### 3. User

The user module which is the app caters to the needs of individuals who browse and purchase accessories on the Acessorize platform. It focuses on delivering a seamless user experience and offers features such as:

- Browsing and searching for accessories.
- Adding items to the shopping cart.
- Accessing order history 
- Search for products 
- Login and Register


### User Interface Design 
the app has an ui design and here is the link for it : https://www.figma.com/file/H5vnl8R6Vu1XHIjHhraYvQ/Ar-Accessories?type=design&node-id=0%3A1&t=tybvDr1a9rEjtsxH-1

## Contributors
- Ahmed Maged
- Noran Mohamed
- Nouran Ibrahim
- Tarek El Shawaf
- Sara Ahmed
- Ziad Tarek
## License

