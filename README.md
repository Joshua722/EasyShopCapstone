# EasyShop Application
## **Capstone Project**
## By:
### - Angel Gonzalez
### - Jake Arnett
### - Joshua Ramos
### - Sing Tuttanon


## Table of Contents:

- [Overview](#overview)
- [Tech Stacks](#tech-stacks)
- [Debugging Concepts](#debugging-concepts)
- [Phase 1: Debugging](#phase-1-debugging)
- [Phase 2: Debugging](#phase-2-debugging)
- [Phase 3: Debugging](#phase-3-debugging)
- [Code Highlights](#code-highlight)
- [Acknowledgements](#acknowledgements)

## Overview

This project was a collaborative effort involving a team of four individuals dedicated to developing a robust e-commerce platform. The team collectively tackled debugging issues and enhanced various aspects of the framework, emphasizing collaboration, communication, and division of responsibility.

## Tech Stacks

The application framework utilizes several technologies and tools:
- **Java:** Primary programming language.
- **Spring Boot:** Framework used for building and deploying Java-based applications.
- **Spring Security:** Used for securing the application with authentication and access control.
- **MySQL:** Database management system used for data storage.
- **Postman:** API testing tool used for debugging and testing RESTful APIs.
- **Git:** Version control system for collaboration and code management.
- **JSON:** Format used for data exchange between the client and server.
- **Maven:** Build automation tool and dependency management for Java-based projects.
- **JUnit:** Testing framework for unit testing Java applications.
- **RESTful API:** Architectural style for designing networked applications.

## Debugging Concepts
- **Error Handling:** Implementing proper error messages and status codes for failed operations.
- **Data Integrity:** Ensuring data consistency and integrity in database operations.
- **Access Control:** Enforcing role-based access to specific endpoints or features.
- **Bug Identification:** Debugging and troubleshooting to identify and resolve reported issues.
- **Testing and Validation:** Rigorous testing of implemented solutions to validate fixes and new features.

## Phase-1: Debugging
In Phase 1, the primary focus was on setting up the foundation for category management within the application. This involved implementing the CategoriesController, enabling CRUD operations for categories, and ensuring that only administrators could perform specific actions.

Process:
- **CategoriesController Implementation:**
  - Implement methods for handling CRUD operations on categories to administrators only.
  - Ensure proper annotations are added to the controller for RESTful endpoints.
- **Administrator Access Control:**
  - Restrict insert, update, or delete operations on categories to administrators only.
- **MySqlCategoriesDao Implementation:**
  - Write code to handle database interactions (CRUD) for categories.

Debugging:
- Understanding and applying proper Spring Security annotations to restrict access to administrative roles.
- Implementing error handling for unauthorized access attempts.
- Ensuring proper error messages or status codes are returned for failed authentication and authorization attempts

## Phase-2: Debugging
In Phase 2, our focus shifted to extending the functionality to manage products within the system. This phase encompasses completing the ProductsController, enabling CRUD operations for products, and addressing reported bugs related to products search and duplication.

Process:
- **Complete ProductsController Endpoints:**
  - Implement endpoints for CRUD operations related to products.
  - Enforced access control: Allow only administrators to perform insert, update, or delete operations on products.
- **Bug Fixes:**
  - Investigate and rectify the reported product search functionality issues.
  - Identify and resolve product duplication problems during updates.

Debugging:
- Analyzing and debugging product search functionality to understand incorrect results.
- Investigating the database update process to pinpoint why updates create duplicate products.
- Testing and validation fixes to ensure the search functionality and updates work as intended.

## Phase-3: Debugging
In Phase 3, we introduced the shopping cart feature, enabling users to add items to their carts. This phase involved implementing ShoppingCartController, managing REST actions for shopping carts, and ensuring persistence of cart items across user sessions.

Process:
- **ShoppingCartController Implementation:**
  - Develop methods to handle REST actions for shopping cart functionality.
  - Implement GET, POST, and DELETE operations for managing shopping carts.
- **Shopping Cart Feature Development:**
  - Create functionality to allow logged-in users to add items to their shopping carts.
  - Ensure persistent cart items even after user logout/login.

Debugging:
- Ensuring secure access to shopping cart features for authenticated users only.
- Verify that shopping cart items persist correctly in the database.
- Testing the entire shopping cart flow to confirm seamless functionality.


## Code Highlight


## Acknowledgements
