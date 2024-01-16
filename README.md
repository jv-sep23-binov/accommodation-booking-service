# <p align="center"><img src="https://cdn.icon-icons.com/icons2/3761/PNG/512/house_building_home_icon_231030.png" width="65"/>BINOV BOOKING<img src="https://cdn.icon-icons.com/icons2/3761/PNG/512/house_building_home_icon_231030.png" width="65"/></p>
Embark on a housing revolution with our BINOV Booking! 🌟 Our goal? Streamline property management, empower customers, and redefine how you experience housing rentals.

Picture this: Effortless booking, real-time property availability checks, and a seamless interface for both customers and managers. Add, view, and modify accommodations with ease. The best part? Secure, cashless transactions through Stripe, making payments a breeze. 💳💨

But wait, there's more! Stay in the loop with our Telegram Notifications Service. Get instant updates on bookings, cancellations, and successful payments. Say goodbye to the old and welcome the future of hassle-free, modern housing rentals! 🚀🏠

P.S. You can easily read the documentation and test our project [HERE](http://ec2-54-174-166-23.compute-1.amazonaws.com/api/swagger-ui/index.html).😉

![img_1.png](pictures/img_1.png)

### <p align="center"><img src="https://media4.giphy.com/media/hJl9v892gjwLEdHoZv/giphy.gif?cid=790b7611l43ui7nrwxl8x9ywd5p44aufwveucp8jd1p8yjj5&ep=v1_stickers_search&rid=giphy.gif&ct=s" width="45"/>We used such technologies and tools:<img src="https://media4.giphy.com/media/hJl9v892gjwLEdHoZv/giphy.gif?cid=790b7611l43ui7nrwxl8x9ywd5p44aufwveucp8jd1p8yjj5&ep=v1_stickers_search&rid=giphy.gif&ct=s" width="45"/></p>
<details>
  <summary><b>Spring Boot</b></summary>

*A framework for building and deploying Java applications with an embedded server, simplifying configuration and accelerating development.*
</details>
<details>
  <summary><b>Spring Data JPA</b></summary>

*Part of the Spring Data project, providing an abstraction for working with databases through JPA (Java Persistence API), simplifying interaction with relational databases.*
</details>
<details>
  <summary><b>Spring MVC</b></summary>

*Model-View-Controller framework for developing web applications, enabling easy creation of websites and web services.*
</details>
<details>
  <summary><b>Spring Security</b></summary>

*Framework for securing Spring applications, adding authentication and authorization to protect resources.*
</details>
<details>
  <summary><b>AWS (Amazon Web Services)</b></summary>

*A comprehensive and widely-used cloud computing platform offering a variety of services such as computing power, storage, databases, machine learning, and more.*
</details>
<details>
  <summary><b>Docker</b></summary>

*Platform for automating deployment and managing containerized applications, simplifying work with isolated environments.*
</details>
<details>
  <summary><b>JUnit</b></summary>

*A popular testing framework for Java that provides annotations to identify test methods, assertions for testing expected results, and test runners for executing test cases.*
</details>
<details>
  <summary><b>Liquibase</b></summary>

*Tool for version control of database schemas, allowing controlled schema changes.*
</details>
<details>
  <summary><b>Lombok</b></summary>

*Library that automates code generation to reduce boilerplate cLombok: ode, such as getters, setters, and equals/hashCode.Lombok: ode, such as getters, setters, and equals/hashCode.*
</details>
<details>
  <summary><b>Mapstruct </b></summary>

*Library for automatic code generation of mappings between Java objects, simplifying conversion between different models.*
</details>
<details>
  <summary><b>Maven</b></summary>

*Tool for managing project dependencies, compilation, building, and publishing of Java programs.*
</details>
<details>
  <summary><b>Mockito</b></summary>

*A mocking framework for Java that allows the creation of mock objects in automated tests, facilitating the isolation of code units for more effective testing.*
</details>
<details>
  <summary><b>PostgreSQL </b></summary>

*Relational database that uses the SQL language for managing and interacting with data.*
</details>
<details>
  <summary><b>Stripe API</b></summary>

*A set of tools and APIs for building online payment solutions, allowing developers to integrate payment processing into their applications.*
</details>
<details>
  <summary><b>Swagger</b></summary>

*Tool for automatically generating API documentation, allowing developers to interactively engage with and understand the structure of the API.*
</details>
<details>
  <summary><b>Telegram API</b></summary>

*A set of APIs provided by Telegram Messenger for building chatbots, integrations, and other applications on the Telegram platform.*
</details>

![img_1.png](pictures/img_1.png)

### <p align="center"><img src="https://media4.giphy.com/media/hJl9v892gjwLEdHoZv/giphy.gif?cid=790b7611l43ui7nrwxl8x9ywd5p44aufwveucp8jd1p8yjj5&ep=v1_stickers_search&rid=giphy.gif&ct=s" width="45"/>Project structure:<img src="https://media4.giphy.com/media/hJl9v892gjwLEdHoZv/giphy.gif?cid=790b7611l43ui7nrwxl8x9ywd5p44aufwveucp8jd1p8yjj5&ep=v1_stickers_search&rid=giphy.gif&ct=s" width="45"/></p>

<p align="center"><img src="pictures/img.png"></p>

![img_1.png](pictures/img_1.png)

### <p align="center"><img src="https://media4.giphy.com/media/hJl9v892gjwLEdHoZv/giphy.gif?cid=790b7611l43ui7nrwxl8x9ywd5p44aufwveucp8jd1p8yjj5&ep=v1_stickers_search&rid=giphy.gif&ct=s" width="45"/>The project has such controllers:<img src="https://media4.giphy.com/media/hJl9v892gjwLEdHoZv/giphy.gif?cid=790b7611l43ui7nrwxl8x9ywd5p44aufwveucp8jd1p8yjj5&ep=v1_stickers_search&rid=giphy.gif&ct=s" width="45"/></p>
📝🔑**Authentication Controller** - endpoints with open access for new users who want to register and for registered users who want to log in.

| HTTP method |       Endpoint        |           Description           |
|:-----------:|:---------------------:|:-------------------------------:|
|    POST     | `/auth/registration ` |    Register a new customer.     |
|    POST     |    `/auth/login `     | Login as a registered customer. |

🧑‍💼 **Customer Controller** - endpoints for managing customers.

| HTTP method |         Endpoint         |   Role   |                               Description                                |
|:-----------:|:------------------------:|:--------:|:------------------------------------------------------------------------:|
|     PUT     | `/customers/{id}/role" ` | MANAGER  | Enables managers to update customers roles, providing role-based access. |
|     GET     |     `/customers/me`      | CUSTOMER | Retrieves the profile information for the currently logged-in customer.  |
|     PUT     |     `/customers/me`      | CUSTOMER |          Allows customers to update their profile information.           |

🏠 **Accommodation Controller** - endpoints for managing accommodations.

| HTTP method |            Endpoint            |   Role   |                          Description                           |
|:-----------:|:------------------------------:|:--------:|:--------------------------------------------------------------:|
|    POST     |       `/accommodations `       | MANAGER  |          Permits the addition of new accommodations.           |
|     GET     |       `/accommodations`        | CUSTOMER |          Provides a list of available accommodations.          |
|     GET     |     `/accommodations/{id}`     | CUSTOMER | Retrieves detailed information about a specific accommodation. |
|     PUT     |     `/accommodations/{id}`     | MANAGER  |            Allows updates to accommodation details.            |
|     PUT     | `/accommodations/{id}/address` | MANAGER  |            Allows updates to accommodation address.            |
|   DELETE    |     `/accommodations/{id}`     | MANAGER  |             Enables the removal of accommodations.             |

🗓️ **Booking Controller** - endpoints for managing bookings.

| HTTP method |        Endpoint         |   Role   |                        Description                        |
|:-----------:|:-----------------------:|:--------:|:---------------------------------------------------------:|
|    PATCH    | `/bookings/{id}/status` | MANAGER  |         Allows manager to change booking status.          |
|    POST     |       `/bookings`       | CUSTOMER |    Permits the creation of new accommodation bookings.    |
|     GET     |     `/bookings/my`      | CUSTOMER |               Retrieves customer bookings.                |
|     GET     |    `/bookings/{id}`     | CUSTOMER |      Provides information about a specific booking.       |
|     PUT     |    `/bookings/{id}`     | CUSTOMER |     Allows customers to update their booking details.     |
|   DELETE    |    `/bookings/{id}`     | CUSTOMER |           Enables the cancellation of bookings.           |
|     GET     |   `/bookings//search`   | MANAGER  | Retrieves bookings based on customer ID and their status. |

💳 **Payment Controller (Stripe)** - endpoints for managing payments.

| HTTP method |      Endpoint       |  Role   |                       Description                       |
|:-----------:|:-------------------:|:-------:|:-------------------------------------------------------:|
|     GET     |     `/payments`     |   ALL   |        Get payments history of certain customer.        |
|    POST     |     `/payments`     | MANAGER |          Checkout payment for user's booking.           |
|     GET     | `/payments/success` |    -    |  Redirection after successfully processing of payment.  |
|     GET     | `/payments/cancel`  |    -    | Redirection after unsuccessfully processing of payment. |

![img_4.png](pictures/img_4.png)

![img_1.png](pictures/img_1.png)

### <p align="center"><img src="https://media4.giphy.com/media/hJl9v892gjwLEdHoZv/giphy.gif?cid=790b7611l43ui7nrwxl8x9ywd5p44aufwveucp8jd1p8yjj5&ep=v1_stickers_search&rid=giphy.gif&ct=s" width="45"/>Notifications Service (Telegram):<img src="https://media4.giphy.com/media/hJl9v892gjwLEdHoZv/giphy.gif?cid=790b7611l43ui7nrwxl8x9ywd5p44aufwveucp8jd1p8yjj5&ep=v1_stickers_search&rid=giphy.gif&ct=s" width="45"/></p>
Receive instant updates on:

- 📅 New bookings 
- 🏡 Newly created/released accommodations
- 💳 Successful payments

Our Notifications Service acts as the central hub, allowing seamless interactions with other services to keep administrators in the loop. 🔄

Key Features:

- 🤖 Utilizes Telegram API for secure notifications
- 💬 Engage through Telegram Chats
- 🚀 Enhance efficiency with purpose-built Telegram Bots

Stay in control and informed with our Telegram Notifications Service! 🌟

<p align="center"><img src="pictures/img_3.png"> <img src="pictures/img_2.png"></p>

![img_1.png](pictures/img_1.png)

### <p align="center"><img src="https://media4.giphy.com/media/hJl9v892gjwLEdHoZv/giphy.gif?cid=790b7611l43ui7nrwxl8x9ywd5p44aufwveucp8jd1p8yjj5&ep=v1_stickers_search&rid=giphy.gif&ct=s" width="45"/> Challenges and Solutions:<img src="https://media4.giphy.com/media/hJl9v892gjwLEdHoZv/giphy.gif?cid=790b7611l43ui7nrwxl8x9ywd5p44aufwveucp8jd1p8yjj5&ep=v1_stickers_search&rid=giphy.gif&ct=s" width="45"/></p>

1. ❌**Payment Session Creation**

   _Challenge:_ We encountered difficulties in creating payment sessions, but through diligent research and exploration of the Stripe documentation, we successfully resolved these issues. Understanding the intricacies of payment session creation proved crucial to ensuring a seamless payment process.

2.  🚨**Console Error Overload**

   _Challenge:_ Our console was flooded with errors, mainly stemming from the fact that a single token was used by everyone. This issue was hampering the user experience and platform functionality. 

   _Solution:_ To overcome this challenge, we implemented a solution by creating a new bot for each user. This not only addressed the error overload but also improved the security and individualized the interactions for each user, ensuring a more stable and reliable system.







![img_1.png](pictures/img_1.png)

### <p align="center"><img src="https://media4.giphy.com/media/hJl9v892gjwLEdHoZv/giphy.gif?cid=790b7611l43ui7nrwxl8x9ywd5p44aufwveucp8jd1p8yjj5&ep=v1_stickers_search&rid=giphy.gif&ct=s" width="45"/> Possible Improvements:<img src="https://media4.giphy.com/media/hJl9v892gjwLEdHoZv/giphy.gif?cid=790b7611l43ui7nrwxl8x9ywd5p44aufwveucp8jd1p8yjj5&ep=v1_stickers_search&rid=giphy.gif&ct=s" width="45"/></p>

- 🌟 **User Reviews and Ratings:** Implement a feature for users to leave reviews and ratings for accommodations. This can enhance the decision-making process for other users and provide valuable feedback for property owners.


- 🔍 **Advanced Search Filters:** Enhance the search functionality with advanced filters such as price range, specific amenities, and accommodation types. This will provide users with more precise search results based on their preferences.
![img_1.png](pictures/img_1.png)

### <p align="center"><img src="https://media4.giphy.com/media/hJl9v892gjwLEdHoZv/giphy.gif?cid=790b7611l43ui7nrwxl8x9ywd5p44aufwveucp8jd1p8yjj5&ep=v1_stickers_search&rid=giphy.gif&ct=s" width="45"/>You can test and use this project:<img src="https://media4.giphy.com/media/hJl9v892gjwLEdHoZv/giphy.gif?cid=790b7611l43ui7nrwxl8x9ywd5p44aufwveucp8jd1p8yjj5&ep=v1_stickers_search&rid=giphy.gif&ct=s" width="45"/></p>
1. 🚀 Install [Docker](https://www.docker.com/products/docker-desktop/)
2. 📦 Clone [THIS](https://github.com/jv-sep23-binov/accommodation-booking-service) repository
3. 🔧 Configure a "**.env**" file with necessary environment variables
4. 🛠️ Run the command `mvn clean package`
5. 🚢 Use `docker-compose up --build` to build and launch Docker containers
6. 🌐 Access the locally running application at http://localhost:8088
### <p align="center">OR </p>
1. You can easily read the documentation and test our project [HERE](http://ec2-54-174-166-23.compute-1.amazonaws.com/api/swagger-ui/index.html).
![img_1.png](pictures/img_1.png)

### <p align="center"><img src="https://media4.giphy.com/media/hJl9v892gjwLEdHoZv/giphy.gif?cid=790b7611l43ui7nrwxl8x9ywd5p44aufwveucp8jd1p8yjj5&ep=v1_stickers_search&rid=giphy.gif&ct=s" width="45"/>Authors:<img src="https://media4.giphy.com/media/hJl9v892gjwLEdHoZv/giphy.gif?cid=790b7611l43ui7nrwxl8x9ywd5p44aufwveucp8jd1p8yjj5&ep=v1_stickers_search&rid=giphy.gif&ct=s" width="45"/></p>
- 👨‍💻[Valerii Fedorovych](https://github.com/fedorovychh)
- 👨‍💻[Igor Korolevich](https://github.com/DevIgork)
- 👨‍💻[Bohdan Bilko](https://github.com/BohdanTrue)
- 👨‍💻[Alexander Liubchenko](https://github.com/Alexander1iubchenko)
- 👩‍💻[Nataliia Shtykh](https://github.com/nshtykh)


