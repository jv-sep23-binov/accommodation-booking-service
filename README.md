# <p align="center"><img src="https://cdn.icon-icons.com/icons2/3761/PNG/512/house_building_home_icon_231030.png" width="65"/>BINOV BOOKING<img src="https://cdn.icon-icons.com/icons2/3761/PNG/512/house_building_home_icon_231030.png" width="65"/></p>
Embark on a housing revolution with our BINOV Booking! 🌟 Our goal? Streamline property management, empower customers, and redefine how you experience housing rentals.

Picture this: Effortless booking, real-time property availability checks, and a seamless interface for both customers and managers. Add, view, and modify accommodations with ease. The best part? Secure, cashless transactions through Stripe, making payments a breeze. 💳💨

But wait, there's more! Stay in the loop with our Telegram Notifications Service. Get instant updates on bookings, cancellations, and successful payments. Say goodbye to the old and welcome the future of hassle-free, modern housing rentals! 🚀🏠

-------
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
  <summary><b>Docker</b></summary>

*Platform for automating deployment and managing containerized applications, simplifying work with isolated environments.*
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

------

### <p align="center"><img src="https://media4.giphy.com/media/hJl9v892gjwLEdHoZv/giphy.gif?cid=790b7611l43ui7nrwxl8x9ywd5p44aufwveucp8jd1p8yjj5&ep=v1_stickers_search&rid=giphy.gif&ct=s" width="45"/>The project has such controllers:<img src="https://media4.giphy.com/media/hJl9v892gjwLEdHoZv/giphy.gif?cid=790b7611l43ui7nrwxl8x9ywd5p44aufwveucp8jd1p8yjj5&ep=v1_stickers_search&rid=giphy.gif&ct=s" width="45"/></p>
**Authentication Controller** - endpoints with open access for new users who want to register and for registered users who want to log in.

| HTTP method |       Endpoint        |           Description           |
|:-----------:|:---------------------:|:-------------------------------:|
|    POST     | `/auth/registration ` |       Register a new user       |
|    POST     |    `/auth/login `     |   Login as a registered user    |

**Customer Controller** - endpoints for managing customers.

| HTTP method |         Endpoint         |   Role   |                               Description                                |
|:-----------:|:------------------------:|:--------:|:------------------------------------------------------------------------:|
|     PUT     | `/customers/{id}/role" ` | MANAGER  | Enables managers to update customers roles, providing role-based access. |
|     GET     |     `/customers/me`      | CUSTOMER | Retrieves the profile information for the currently logged-in customer.  |
|     PUT     |     `/customers/me`      | CUSTOMER |          Allows customers to update their profile information.           |

**Accommodation Controller** - endpoints for managing accommodations.

| HTTP method |            Endpoint            |   Role   |                          Description                           |
|:-----------:|:------------------------------:|:--------:|:--------------------------------------------------------------:|
|    POST     |       `/accommodations `       | MANAGER  |          Permits the addition of new accommodations.           |
|     GET     |        `accommodations`        | CUSTOMER |          Provides a list of available accommodations.          |
|     GET     |     `/accommodations/{id}`     | CUSTOMER | Retrieves detailed information about a specific accommodation. |
|     PUT     |     `/accommodations/{id}`     | MANAGER  |            Allows updates to accommodation details.            |
|     PUT     | `/accommodations/{id}/address` | MANAGER  |            Allows updates to accommodation address.            |
|   DELETE    |     `/accommodations/{id}`     | MANAGER  |             Enables the removal of accommodations.             |