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
   
   | Para name | Type   | Necesstity | Comment                                   |
   |:---------:|:------:|:----------:|:-----------------------------------------:|
   | username  | string | yes        | contains num, char, symbol                |
   | password  | string | yes        | must contains char and num, above 6 chars |
   
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