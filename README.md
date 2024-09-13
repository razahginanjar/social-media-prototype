## Prerequisites
- [Java JDK 22](https://adoptium.net/) or later
- [Maven](https://maven.apache.org/) (for building the project)
- [Postgresql](https://www.postgresql.org/download/) (for database)

##  Requirements Dependencies
- [Spring Web MVC]
- [Spring Data JPA]
- [Postgresql Driver]
- [Lombok]
- [Spring Validation]
- [Spring AOP]
- [Spring Security]
- [AUTH-0]

##   Features
    - [Feature 1, Data processing]
    - [Feature 2.x] 
        2.1 API endpoints for User
            1. post
            2. addfriend
            3. remove friend
            4. list friend
            5. update post
            6. remove post
            7. see post that from your friend + page that your follow
            8. comment
            9. update comment
            10. delete comment
            11. get all comment from post
            12. liking post
            13. unliking post
            14. liking comment
            15. unliking comment
            16. follow page
            17. unfollow page
            18. create group
            19. delete group
            20. join group
            21. logout from group
            22. register page

        2.2 API endpoints for Group
            1. post
            2. update post
            3. delete post
            4. comment
            5. update comment
            6. delete comment
            7. liking post
            8. unliking post
            9. liking comment
            10. unliking comment
            11. remove member (ADMIN, CREATOR)
            12. gave another role (ADMIN, CREATOR)

        2.3 API endpoints for Page
            1. post
            2. update post
            3. delete post
            4. comment
            5. update comment
            6. delete comment
            7. liking post
            8. unliking post
            9. liking comment
            10. unliking comment
            11. get all post

        2.4 Auth
            1. Register
            2. Login
    - [Feature 3, Different Authorization for accessing different API]

more detail feature in API can check in docs folder

##   Technology Stack
- Language: [Java]
- Framework: [Spring Boot]
- Database: [postgresql]

##  Installation

```bash
    git clone https://github.com/razahginanjar/social-media-prototype.git
```

##  Running Program
before running the program, you must carefully set password and username in application.properties according to your database
and your name of database in url datasource

### Build
```bash
    mvn clean package
```

### Run
```bash
    java -jar target/my-spring-boot-app-1.0-SNAPSHOT.jar
```

### Contact
For any questions or issues, please contact:

- email: razahdedenginanjar@gmail.com