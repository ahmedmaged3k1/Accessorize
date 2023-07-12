# Accessorize

## Description

The project is an e-commerce try-on application developed for Android devices using Kotlin. It enables users to try on face accessories through augmented reality, recommends products based on previous user data, and allows users to capture photos while wearing the accessories and share them via WhatsApp. The application provides various e-commerce features including product search and cart operations. It follows the three-tier architecture pattern and is implemented using the MVVM (Model-View-ViewModel) architecture.

### System Architecture
![sys](https://github.com/ahmedmaged3k1/Accessorize/assets/60134186/a4c03f3b-c9c1-4850-a596-e5307a0d39ef)


The three-tier architecture separates the application into three layers: the presentation layer, the application logic layer, and the data management layer. This separation promotes code organization, modularity, scalability, reusability, and security. The presentation layer handles the user interface and augmented reality components, the application logic layer manages the business logic and authentication, and the data management layer handles data storage and retrieval from local and remote sources.



### MVVM Architecture 


![mvvm](https://github.com/ahmedmaged3k1/Accessorize/assets/60134186/4bcda4dd-ecef-4e9b-b31e-8af65520754e)

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
- In the Credentials class change the ip address to your local host ip address
- ![cred](https://github.com/ahmedmaged3k1/Accessorize/assets/60134186/e024877c-0ed3-4a58-8b0b-b5ef9be9d19a)
- Build and run the application from Android Studio.
- Make sure you run from a physical device
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

GitHub Repository: https://github.com/ziadtarekfa/Accessorize-Admin-W 

### 2. Seller

The seller module is designed to facilitate the activities of sellers who wish to showcase and sell their accessories on the Acessorize platform. Key functionalities of the seller module include:

- Adding, editing, and deleting product listings.
- Managing inventory and stock levels.
- Handling order fulfillment and shipping details.
- Tracking sales and revenue.

GitHub Repository: https://github.com/ziadtarekfa/Seller-Accessorize

### 3. User

The user module which is the app caters to the needs of individuals who browse and purchase accessories on the Acessorize platform. It focuses on delivering a seamless user experience and offers features such as:

- Browsing and searching for accessories.
- Trying on the accessory
- Recommending another product
- Share product photos via whats app
- Take a shot with the accessory on
- Adding items to the shopping cart
- Adding items to the favorites
- Accessing history of tried-on products
- Search for products 
- Login and Register

## Demo

https://github.com/ahmedmaged3k1/Accessorize/assets/60134186/f38d06df-83ee-4ff7-9d74-76a4a7445298

## Screenshots 
<img src="https://github.com/ahmedmaged3k1/Accessorize/assets/60134186/5d5fb875-9a1c-43dd-94d8-262de353e688" width="300" height="600">
<img src="https://github.com/ahmedmaged3k1/Accessorize/assets/60134186/1a76a7a7-02cd-42c4-b71d-9dd064acb594" width="300" height="600">
<img src="https://github.com/ahmedmaged3k1/Accessorize/assets/60134186/fc23bd7f-7b73-480f-ad22-fb8b122a0205" width="300" height="600">
<img src="https://github.com/ahmedmaged3k1/Accessorize/assets/60134186/6f10a081-363b-4699-bcd1-1a3cb5525ff2" width="300" height="600">
<img src="https://github.com/ahmedmaged3k1/Accessorize/assets/60134186/023cab26-e7ac-4114-97ce-a182ed0dba8f" width="300" height="600">
<img src="https://github.com/ahmedmaged3k1/Accessorize/assets/60134186/8cdb87b8-89e4-4bdb-8247-b0506542ef51" width="300" height="600">
<img src="https://github.com/ahmedmaged3k1/Accessorize/assets/60134186/f52638c7-4442-4f65-a1e1-247100727f14" width="900" height="600">


## Contributors
- Ahmed Maged
- Noran Zaky
- Nouran Ibrahim
- Tarek El Shawaf
- Sara Ahmed
- Ziad Tarek


