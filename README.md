# PetWellness

<div align="center">
  <img src="https://petwelnessv2.netlify.app/assets/Logo.png" width="350" alt="PetWellness  Logo" />
</div>

**PetWellness** is an all-in-one platform designed to offer personalized and comprehensive solutions for pet owners, focusing on providing reliable information, remote veterinary services, and an active online community. With PetWellness, users can create an account, access health advice tailored to their pet's needs, and have online consultations as well as schedule appointments with local professionals. The platform also allows users to organize their pet's information, including health records and automatic reminders, to ensure continuous and proper care.

Admins can efficiently manage the platform through CRUD (Create, Read, Update, Delete) operations on products, categories, veterinarians, and users, ensuring that the platform's services and information are always up-to-date with market needs.

PetWellness aims to provide a unified platform that combines easy access to veterinary services, specialized products, and a supportive community, all in a secure and easy-to-use environment. It allows pet owners not only to care for their furry companions but also to connect with other pet caretakers by sharing experiences and advice.

---

## 🛠️ Project Progress

| **Column**      | **Description**                                                                                                                                   |
|-----------------|---------------------------------------------------------------------------------------------------------------------------------------------------|
| **Backlog**     | Contains all user stories, tasks, and features to be developed. This is the list of all pending work.                                             |
| **In Progress** | Includes tasks that are currently under development. View the ongoing work to ensure the flow of progress.                                       |
| **Review**      | After completing a task, it is moved here for code review and peer review. This phase includes creating **pull requests** to ensure the code meets quality standards before merging into the main project. |
| **Testing**     | Contains tasks that have passed the code review and need to undergo thorough testing (unit, integration, and acceptance) to ensure quality. |
| **Done**        | Tasks that are fully developed, reviewed, and tested are moved here, indicating they are complete and finalized.                                  |

Check the progress of our work via the following link: [Trello Board](https://trello.com/invite/b/66df9b90adc79739768b56c7/ATTI1f0522ce1c974ea160da75e4d3ed2c8cD691B6BA/user-stories-petwellness).

---

## 💡 Application Features

### **User Management Module**

- **User Creation and Login:**
    - Allows pet owners, veterinarians, and administrators to register on the platform.
    - Facilitates login for users to access their profiles and manage their pets' information or services.
    - Ensures security for user credentials to protect sensitive data.

### **Shopping Module**

- **Purchase Products and Services for Pets:**
    - Integration with payment systems like PayPal to enable secure and fast transactions for veterinary services and pet-related products.
    - Transaction processing for purchasing items such as food, accessories, and medications.
    - Purchase confirmation and order tracking, providing updated information about delivery status.

### **Content Management Module**

- **Managing Products and Services:**
    - Add new products to the catalog, such as food, accessories, or medications.
    - Edit product details and services available to users.
    - Remove products or services that are no longer available.
    - List all available products and services for easy user purchase.

- **Product Categories:**
    - Categorize products into various groups like "Food," "Accessories," "Medications," etc.
    - Help users navigate and search for products by category, improving the shopping experience.
    - Keep the catalog organized and accessible.

- **Veterinarian and Consultations Management:**
    - Allows admins to add and manage registered veterinarians' information.
    - Facilitates editing veterinarian details (specialties, available hours, etc.).
    - Remove veterinarians or services that are no longer active.
    - Keeps the list of veterinarians and services up-to-date.

### **Pet Management Module**

- **Pet Health Records:**
    - Allows users to register and manage their pets' information, such as vaccinations, treatments, and routine check-ups.
    - Facilitate the creation, editing, and deletion of health records and services rendered.
    - Improves access and management of pet health information, with options for automatic reminders for upcoming appointments or treatments.

### **Reporting Module**

- **Activity and Service Reports:**
    - Generates reports on veterinary consultations, purchased products, and requested services.
    - Displays statistics on user activities, such as top-selling products or most-requested consultations.
    - Provides admins with detailed information on platform usage to help improve the offering of products and services.

---

## 🧑‍💻 Project Layers Description

| **Layer**     | **Description**                                                                                                            |
|---------------|----------------------------------------------------------------------------------------------------------------------------|
| **api**       | Contains the REST controllers that handle HTTP requests and responses.                                                     |
| **entity**    | Defines the entities of the data model that map to the database tables.                                                   |
| **repository**| Provides the interface for CRUD operations and interaction with the database.                                               |
| **service**   | Declares the business logic and operations that will be performed on the entities.                                          |
| **serviceImpl**| Implements the business logic defined in services, using the necessary repositories.                                        |

---
