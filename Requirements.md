## Database Tables

### users

| id                 | first_name | last_name | UNIQUE(user_name) | password_digest |
| ------------------ | ---------- | --------- | ----------------- | --------------- |
| serial primary key | string     | string    | string            | string          |

### book

| id                 | title  | author | description | publication_year | ISBN   | price | stock |
| ------------------ | ------ | ------ | ----------- | ---------------- | ------ | ----- | ----- |
| serial primary key | string | string | string      | string           | string | float | int   |

### patron

| id                 | first_name | last_name | phone  | email  |
| ------------------ | ---------- | --------- | ------ | ------ |
| serial primary key | string     | string    | string | string |

### borrowed_book

| id                 | book_id                      | patron_id                      | status                     | constraints                |
| ------------------ | ---------------------------- | ------------------------------ | -------------------------- | -------------------------- |
| serial primary key | foreign key references(book) | foreign key references(patron) | ENUM ("Borrowed, Returned) | UNIQUE(book_id, patron_id) |

### All tables have additional hidden columns, created and updated, for creation and update timestamps

## Application URL

`http://localhost:3000/api`

## API Endpoints

### Books

- **URLs**<br />
  GET `/books`<br />
  GET `/books/{book_id}`<br />
  POST `/books`<br />
  PUT `/books/{book_id}`<br />
- **Request body for PUT and POST**<br />
  `title:[string]`<br />
  `author:[string]`<br />
  `description:[string]`<br />
  `publicationYear:[string]`<br />
  `isbn:[string]`<br />
  `price:[float]`<br />
  `stock:[int]`<br />
- **Success Response:**<br />
  - **Code:** 200 <br />
    **Content:** `Book or List<Book> in JSON`<br />
- **Error Response:**<br />
  - **Code:** 404 <br />
    **Content:** `{ error : "No books found"}, {error : "Book not found id: {book_id}" }`<br />
  - **Code:** 401 <br />
    **Content:** `{ error : "Full authentication is required to access this resource" }`<br />
  - **Code:** 400 <br />
    **Content:** `{ error : "Invalid Data" }` <br />
  - **Code:** 500 <br />
    **Content:** `{ error : "Internal server error" }` <br />
- **Satisfied requirements:**<br />
  - **Book management endpoints:**<br />
    - GET /api/books: Retrieve a list of all books.
    - GET /api/books/{id}: Retrieve details of a specific book by ID.
    - POST /api/books: Add a new book to the library.
    - PUT /api/books/{id}: Update an existing book's information.
    - DELETE /api/books/{id}: Remove a book from the library.

### Users

- **URLs**<br />
  POST `/api/users/sign_up`<br />
  POST `/api/users/sign_in`<br />
  PUT `/api/users/{user_id}`<br />
  DELETE `/api/users/{user_id}`<br />
  GET `/api/users/{user_id}`<br />
  GET `/api/users`<br />
- **Request body**<br />
  - **Sign up:**<br />
    `first_name:[string]`<br />
    `last_name:[string]`<br />
    `user_name:[string]`<br />
    `password:[string]`<br />
  - **Sign in:**<br />
    `user_name:[string]`<br />
    `password:[string]`<br />
  - **Update:**<br />
    `id:[string]`<br />
    `first_name:[string]`<br />
    `last_name:[string]`<br />
    `user_name:[string]`<br />
- **Success Response:**<br />
  - **Code:** 200 <br />
    **Content:** `User or List<User> in JSON`<br />
  - **Code:** 200 <br />
    **Content:** `Authorization Token along with a user ID (sign up, sign in) in JSON`<br />
- **Error Response:**<br />
  - **Code:** 404 <br />
    **Content:** `{ error : "No results found" }`<br />
  - **Code:** 401 <br />
    **Content:** `{ error : "Full authentication is required to access this resource" }` <br />
  - **Code:** 403 <br />
    **Content:** `{ error : "Unauthorized (when trying to access resources of another user)" }` <br />
  - **Code:** 400 <br />
    **Content:** `{ error : "Invalid Data" }` <br />
  - **Code:** 500 <br />
    **Content:** `{ error : "Internal server error" }` <br />
- **Satisfied requirements:**<br />
  - **Spring Boot Security Using JWT**

### Patrons

- **URLs**<br />
  GET `/patrons`<br />
  GET `/patrons/{patron_id}`<br />
  POST `/patrons`<br />
  PUT `/patrons/{patron_id}`<br />
- **Request body for PUT and POST**<br />
  `firstName:[string]`<br />
  `lastName:[string]`<br />
  `phone:[string]`<br />
  `email:[string]`<br />
- **Success Response:**<br />
  - **Code:** 200 <br />
    **Content:** `Patron or List<Patron> in JSON`<br />
- **Error Response:**<br />
  - **Code:** 404 <br />
    **Content:** `{ error : "No patrons found"}, {error : "Patron not found id: {patron_id}" }`<br />
  - **Code:** 401 <br />
    **Content:** `{ error : "Full authentication is required to access this resource" }`<br />
  - **Code:** 400 <br />
    **Content:** `{ error : "Invalid Data" }` <br />
  - **Code:** 500 <br />
    **Content:** `{ error : "Internal server error" }` <br />
- **Satisfied requirements:**<br />
  - **Patron management endpoints:**<br />
    - GET /api/patrons: Retrieve a list of all patrons.
    - GET /api/patrons/{id}: Retrieve details of a specific patron by ID.
    - POST /api/patrons: Add a new patron to the system.
    - PUT /api/patrons/{id}: Update an existing patron's information.
    - DELETE /api/patrons/{id}: Remove a patron from the system.

### Borrowed Books

- **URLs**<br />
  POST `/borrow/{book_id}/patron/{patron_id}`<br />
  POST `/return/{book_id}/patron/{patron_id}`<br />
- **Success Response:**<br />
  - **Code:** 200 <br />
    **Content:** `BorrowedBook {id, book_id, patron_id, status ("Borrowed", "Returned")} in JSON`<br />
- **Error Response:**<br />
  - **Code:** 404 <br />
    **Content:** `{ error : "Book not found, patron not found" }`<br />
  - **Code:** 401 <br />
    **Content:** `{ error : "Full authentication is required to access this resource" }`<br />
  - **Code:** 400 <br />
    **Content:** `{ error : "Invalid Request" }` <br />
  - **Code:** 500 <br />
    **Content:** `{ error : "Internal server error" }` <br />
- **Satisfied requirements:**<br />
  - **Borrowing endpoints:**<br />
    - POST /api/borrow/{bookId}/patron/{patronId}: Allow a patron to borrow a book.
    - PUT /api/return/{bookId}/patron/{patronId}: Record the return of a borrowed book by a patron.

## Route Protection

### Protected Routes (require authenticatio: sign up first, log in, receive the jwt and use it as a bearer token in all subsequent requests):

- POST /api/books: Add a new book to the library.
- PUT /api/books/{id}: Update an existing book's information.
- DELETE /api/books/{id}: Remove a book from the library.
- GET /api/patrons: Retrieve a list of all patrons.
- GET /api/patrons/{id}: Retrieve details of a specific patron by ID.
- POST /api/patrons: Add a new patron to the system.
- PUT /api/patrons/{id}: Update an existing patron's information.
- DELETE /api/patrons/{id}: Remove a patron from the system.
- POST /api/borrow/{bookId}/patron/{patronId}: Allow a patron to borrow a book.
- PUT /api/return/{bookId}/patron/{patronId}: Record the return of a borrowed book by a patron.
- PUT `/api/users/{user_id}` (Requires authorization, user_id must match the user id of the current session)
- DELETE `/api/users/{user_id}` (Requires authorization, user_id must match the user id of the current session)
- GET `/api/users/{user_id}` (Requires authorization, user_id must match the user id of the current session)
- GET `/api/users`

### Public routes: Do not require authentication:

- POST `/api/users/sign_up`
- POST `/api/users/sign_in`
- GET /api/books: Retrieve a list of all books.
- GET /api/books/{id}: Retrieve details of a specific book by ID.
