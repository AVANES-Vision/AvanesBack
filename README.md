### Register a new user
POST http://localhost:8080/api/users
Content-Type: application/json

{
  "username": "admin",
  "password": "admin",
  "role": "USER"
}

### Login
POST http://localhost:8080/login
Content-Type: application/json

{
  "username": "admin",
  "password": "admin"
}
### Get all users
GET http://localhost:8080/api/users

### Get user by ID
GET http://localhost:8080/api/users/1

### Update user
PUT http://localhost:8080/api/users/1
Content-Type: application/json

{
  "userId": 1,
  "username": "updateduser",
  "password": "newpassword123",
  "role": "ADMIN"
}

### Delete user
DELETE http://localhost:8080/api/users/1
