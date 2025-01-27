# BookManager

> A backend project of a book management system built with Spring Boot.

## Analysis of Functions

## Design of Database

1. books
   
   ```sql
   CREATE TABLE books (
       id SERIAL PRIMARY KEY,
       isbn VARCHAR(20) NOT NULL
           CONSTRAINT fk_books_information
           REFERENCES book_information
           ON DELETE CASCADE,
       status book_status DEFAULT 'AVAILABLE'::book_status NOT NULL,
       library INTEGER REFERENCES libraries
           ON DELETE SET NULL
   );
   ```

2. book_information
   
   ```sql
   CREATE TABLE book_information (
       isbn VARCHAR(20) PRIMARY KEY,
       title VARCHAR(90) NOT NULL,
       author VARCHAR(50) NOT NULL,
       publisher VARCHAR(50) NOT NULL
   );
   ```

3. users
   
   ```sql
   CREATE TABLE users (
       id SERIAL PRIMARY KEY,
       username VARCHAR(50) UNIQUE NOT NULL,
       password VARCHAR(127) NOT NULL,
       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
       status BOOLEAN DEFAULT TRUE NOT NULL, -- account status, true: normal, false: disabled
       admin BOOLEAN DEFAULT FALSE NOT NULL
   );
   ```

4. Libraries
   
   ```sql
   CREATE TABLE libraries (
        id SERIAL PRIMARY KEY,
        name VARCHAR(100) NOT NULL UNIQUE,
        address VARCHAR(200) NOT NULL,
        phone VARCHAR(100) NOT NULL
   )
   ```

## Design of API

1. Login
   
   > Path: /api/user/login
   > 
   > Method: GET
   > 
   > Description: User can log in system with this api
   
   contentType: application/json
   
   | Para name |  Type  | Necessity |                Comment                 |
   |-----------|:------:|-----------|:--------------------------------------:|
   | username  | string | yes       | contains num, char, symbol, 5-15 chars |
   | password  | string | yes       | must contains char and num, 6-20 chars |
   
   ```json
   {
       "username": "admin",
       "password": "admin123456"
   }
   ```
   
   Response
   
   ```json
   {
     "code": 0,
     "message": "Login successfully",
     "data": {
       "token": "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VyTmFtZSI6ImFkbWluX3Rlc3QyIiwiZXhwIjoxNzM3Mzc0MTk1LCJpYXQiOjE3MzczNjY5OTV9.BicyNScAAO55FlgBcQCu6piOlDh9X0l7_d5cklILqfQ"
     }
   }
   ```

2. /api/signup
   
   > Path: /api/user/signup
   > 
   > Method: POST
   > 
   > Description: User can sign up a new account
   
   contentType: application/json
   
   | Para name |  Type  | Necessity |                Comment                 |
   |:---------:|:------:|-----------|:--------------------------------------:|
   | username  | string | yes       | contains num, char, symbol, 5-15 chars |
   | password  | string | yes       | must contains char and num, 6-20 chars |
   
   ```json
   {
       "username": "admin",
       "password": "admin123456"
   }
   ```
   
   Response
   
   ```json
   {
     "code": 0,
     "message": "Signup successfully"
   }
   ```
   
3. /api/books
   - GET
   
   > Path: /api/books
   > 
   > Method: GET
   > 
   > Description: Get a list of all books

   

   - POST