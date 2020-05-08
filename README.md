# Getting Started

### Reference Documentation
You can access API documentation on URL: /restaurant-vote/swagger-ui.html 
in case of localhost, visit URL http://localhost:8080/restaurant-vote/swagger-ui.html

There are predefined users in test database:
user with Admin rights: admin@test.com, password: 123
regular user: test@test.com, password: 321
regular users: use34@test.com, user4@test.com, swagger@test.com. For these three users passord is 123. 

To be able to send requests, please, provide in headers the authorization token (the one with Bearer prefix) that you get on successful login. 
There are two sql scripts in src/main/resources/db folder:
initDB.sql - drops all tables in the database and creates them again.
populateDB.sql - populate the database with preset values for testing.