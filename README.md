# Spring Boot User Management API (Dailype Assignment)

Welcome to the Spring Boot User Management API repository! This project implements a set of endpoints for managing user data.

## Endpoints
1. **/create_user**: Creates a new user with the provided data.
2. **/get_user**: Retrieves user records from the database.
3. **/delete_user**: Deletes a user from the database.
4. **/update_user**: Updates user data in the database.

## About the Assignment

The assignment is purely my work done by following the given instructions. You can find the detailed instructions [here](https://gist.github.com/ashwin-dailype/b2f26c7f4ca37304c921b4ef582b75c3).

## Manager Table Details

The `manager` table contains the following columns:
1. `manager_id` (UUID)
2. `is_active` (boolean)
3. `name` (varchar)

Use the following values for the `manager` table:

```sql
INSERT INTO manager (manager_id, is_active, name)
VALUES 
    ('e6c96d15-1b27-4d4f-a8a1-5b5a789e62f1', true, 'John Smith'),
    ('f1a6850e-7f3d-4814-b496-5b3b2ae0714c', false, 'Alice Johnson'),
    ('b4b0f5e1-52d4-4728-9ee1-c1f28a8ab6e9', true, 'Michael Brown'),
    ('9f918dc5-5f65-4aa3-aa6d-8146e96d97a1', false, 'Emily Davis');
```
## Video Link

[Screen Recording of POSTMAN Testing](https://drive.google.com/file/d/1D8IzmtgY1Tq6mPwhxPw1f_mB67u2ntBf/view?usp=drive_link)
