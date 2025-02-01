# BookManager

> A backend project of a book management system built with Spring Boot.

## Analysis of Functions

1. System Objectives
   
   - **Improve Management Efficiency**: Automate book borrowing, returning, and searching processes to reduce manual operations.
   
   - **Enhance User Experience**: Provide readers with convenient book searching, borrowing, and reservation functionalities.
   
   - **Data Management**: Centralize the management and analysis of data related to books, readers, and borrowing records.

2. User Roles
   
   - **Administrator**: Responsible for book entry, modification, deletion, borrowing management, and user management.
   
   - **Reader**: Regular users who can search, borrow, return, and reserve books.

3. Functional Requirements
   
   - **Book Management**
     
     - Book Entry: Administrators can add new books, including details such as title, author, ISBN, publisher, category, and stock quantity.
     
     - Book Modification: Administrators can update book information.
     
     - Book Deletion: Administrators can remove books that are no longer needed.
     
     - Book Search: Readers and administrators can search for books by title, author, ISBN, etc.
     
     - Book Categorization: Books are classified into categories (e.g., literature, technology, history) for easier management.
   
   - **Borrowing Management**
     
     - Borrowing: Readers can borrow books, and the system records the borrowing date and due date.
     
     - Returning: Readers return books, and the system updates the book status.
     
     - Renewal: Readers can renew borrowed books to extend the borrowing period.
     
     - Reservation: Readers can reserve books that are currently available or borrowed, if the book is borrowed system will notify them when the book is returned. 
     
     - Overdue Handling: The system automatically calculates overdue days, reminds readers to return books, and allows administrators to handle overdue cases.
   
   - **User Management**
     
     - User Registration: Readers can register accounts by providing username and password.
     
     - User Information: Readers can upload personal information(name, contact details, ID number, etc.), and modify their information in the future.
     
     - User ban: If a reader's account violates the rules, the system and administrator can ban the account and restrict the reader's behavior (such as prohibiting reservations and borrowing books).
     
     - Borrowing History: Readers can view their borrowing history.
   
   - **Statistics and Reporting**
     
     - Book Borrowing Statistics: Track the number of times each book has been borrowed and identify popular books.
     
     - Reader Borrowing Statistics: Analyze readers' borrowing behavior, such as borrowing frequency and overdue incidents.
   
   - **System Management**
     
     - System Settings: Administrators can configure system parameters, such as borrowing periods and overdue penalty rules.
     
     - Log Management: Record system operation logs for tracking and auditing purposes.

4. Non-Functional Requirements
   
   - **Performance Requirements**: The system should support multiple users simultaneously, with response times within reasonable limits (e.g., search operations should take no more than 2 seconds).
   
   - **Security Requirements**: The system should include user authentication, access control, and data encryption to prevent data breaches and unauthorized operations.
   
   - **Availability Requirements**: The system should be highly available, ensuring smooth operation during peak hours.
   
   - **Scalability Requirements**: The system should be designed for future expansion, allowing for the addition of new features and modifications.

5. Future Extensions
   
   - **Mobile Support**: Develop a mobile app to allow readers to search and borrow books anytime, anywhere.
   
   - **Smart Recommendation**: Recommend books based on readers' borrowing history.

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
       publisher VARCHAR(50) NOT NULL,
       category book_category NOT NULL
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

5. Book_status(ENUM)
   
   ```sql
   CREATE TYPE book_status AS ENUM ('AVAILABLE','BORROWED','RESERVED','UNAVAILABLE');
   ```

6. Book_category(ENUM)
   
   ```sql
   CREATE TYPE book_category AS ENUM ('哲学','社会科学','政治','法律','经济',
        '文化','文学','教育','语言','艺术','历史','自然科学','数理科学与化学','天文学与地球科学',
        '生物科学','医学与卫生','农业科学','工业技术','交通运输','航空航天','计算机科学','环境科学');
   ```

7. Library_config
   
   ```sql
   CREATE TABLE library_config (
         id SERIAL PRIMARY KEY,
         config_key VARCHAR(50) NOT NULL UNIQUE,
         config_value VARCHAR(100) NOT NULL
   );
   ```
   
   

## Design of API

- Response Message Structure
  
  1. Result (Base Response)
     
     ```json
     {
         "code": 0,
         "message": "success"
     }
     ```
  
  2. ResultData (Response with Data)
     
     ```json
     {
         "code": 0,
         "message": "success",
         "data": {}
     }
     ```

- Response Code Definition
  
  | Code(!0 is error code) | Comment                 |
  |:----------------------:|:-----------------------:|
  | 0                      | success                 |
  | 1                      | no authentication       |
  | 2                      | invalid parameter       |
  | 3                      | parameter format error  |
  | 4                      | unspecified error       |
  | 5                      | insufficient permission |
1. Login
   
   > Path: /api/user/login
   > 
   > Method: GET
   > 
   > Description: User can log in system with this api
   
   contentType: application/json
   
   | Para name | Type   | Necessity | Comment                                |
   |:---------:|:------:|:---------:|:--------------------------------------:|
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
   
   | Para name | Type   | Necessity | Comment                                |
   |:---------:|:------:| --------- |:--------------------------------------:|
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