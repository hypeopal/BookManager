# BookManager

> A backend project of a book management system

## Analysis of Functions

## Design of API

1. Login
   
   > Path: /api/login
   > 
   > Method: GET
   > 
   > Description: User can log in system with this api
   
   contentType: application/json
   
   | Para name | Type   | Necessity | Comment                                |
   |:---------:|--------|:---------:|----------------------------------------|
   | username  | string |    yes    | contains num, char, symbol, 5-15 chars |
   | password  | string |    yes    | must contains char and num, 6-20 chars |
   
   ```json
   {
       "username": "admin",
       "password": "admin123456"
   }
   ```
   
   Response

2. /api/signup

3. /api/books
   
   - GET
   
   - 