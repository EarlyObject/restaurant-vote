# Getting Started

### Reference Documentation
You can access API documentation on URL:   /restaurant-vote/swagger-ui.html   
in case of localhost, visit URL   http://localhost:8080/restaurant-vote/swagger-ui.html  

There are predefined users in test database:  
user with Admin rights: admin@test.com, password: 123  
regular user: test@test.com, password: 321  
regular users: user3@test.com, user4@test.com, swagger@test.com. For these three users the password is 123.   

To be able to send requests, please, provide in headers the authorization token (the one with Bearer prefix) that you get on successful login.   
There are two sql scripts in src/main/resources/db folder:  
initDB.sql - drops all tables in the database and creates them again.  
populateDB.sql - populate the database with preset values for testing.  
  
    
###LOGIN  
Authorized for concrete user, admins.  
`curl -X POST \
  http://localhost:8080/restaurant-vote/users/login \`  
  -H 'Content-Type: application/json,text/plain' \  
    -d '{  
  	"email"		: "test@test.com",  
  	"password"  : "321"  
  }'
  
  
###USER
###create user
Does not need authorization, open for whoever.  
`curl -X POST \  
    http://localhost:8080/restaurant-vote/users \`  
      -H 'Accept: application/json' \    
       -H 'Content-Type: application/json,application/json' \    
       -d '{    
     	"firstName" : "John ",  
     	"lastName"  : "Connor",  
     	"email"		: "john@connor.com",  
     	"password"  : "123"  
         }'  

###get user
Authorized for concrete user, admins.  
`curl -X GET \  
  http://localhost:8080/restaurant-vote/users/stringUserIdHere \  `  
   -H 'Accept: application/json' \  
   -H 'Authorization: Bearer AuthorizationBearerHere' \  
   -H 'Content-Type: application/json' \
  
###update user
Authorized for concrete user, admins.  
`curl -X PUT \  
  http://localhost:8080/restaurant-vote/users/stringUserIdHere \  `  
    -H 'Authorization: Bearer AuthorizationBearerHere' \  
    -H 'Content-Type: application/json,text/plain' \  
    -d '{  
  	"firstName":"Sarah",  
  	"lastName":"Connor" 
  	 }' 

###update user to admin
Authorized for admins only.  
`curl -X PUT \  
    http://localhost:8080/restaurant-vote/users/stringUserIdHere/admin \`  
         -H 'Authorization: Bearer AuthorizationBearerHere' \    
        -H 'Content-Type: application/json' \     
   
###delete user
Authorized for concrete user, admins.  
`curl -X DELETE \   
   http://localhost:8080/restaurant-vote/users/stringUserIdHere \    
   -H 'Authorization: Bearer AuthorizationBearerHere' \ 
   `   
  
###get user
Authorized for concrete user, admins.  
`curl -X GET \   
   http://localhost:8080/restaurant-vote/users/stringUserIdHere \`  
      -H 'Accept: application/json' \  
      -H 'Authorization: Bearer AuthorizationBearerHere' \  
      -H 'Content-Type: application/json' \  
   
###get all users
Authorized for admins only.  
`curl -X GET \  
   'http://localhost:8080/restaurant-vote/users?page=0&limit=50' \`  
   -H 'Accept: application/json' \  
      -H 'Authorization: Bearer AuthorizationBearerHere' \  
      -H 'Content-Type: application/json,application/json' \    
    
###RESTAURANT
###create restaurant
Authorized for admins only.  
`curl -X POST \  
   http://localhost:8080/restaurant-vote/restaurants \`  
    -H 'Accept: application/json' \  
      -H 'Authorization: Bearer AuthorizationBearerHere' \  
      -H 'Content-Type: application/json,text/plain' \    
      -d '{  
    	"name":"Vouge",  
    	"address":"Main street 400"  
        }'
###get restaurant
Authorized for users, admins.  
`curl -X GET \   
   'http://localhost:8080/restaurant-vote/restaurants/1009' \    
   -H 'Authorization: Bearer AuthorizationBearerHere' \  
   `    
###get restaurant with meal
Authorized for users, admins.  
`curl -X GET \  
   'http://localhost:8080/restaurant-vote/restaurants/1009?loadAll=true' \  
   -H 'Authorization: Bearer AuthorizationBearerHere' \  
   `    
###get all restaurants
Authorized for users, admins.  
`curl -X GET \  
   'http://localhost:8080/restaurant-vote/restaurants?page=0&limit=10' \  
   -H 'Authorization: Bearer AuthorizationBearerHere' \  
`
###get all restaurants with meals 
Authorized for users, admins.  
`curl -X GET \  
   'http://localhost:8080/restaurant-vote/restaurants?page=0&limit=10&loadAll=true' \  
   -H 'Authorization: Bearer AuthorizationBearerHere' \  
   `
###get restaurants meal history
Authorized for users, admins.  
`curl -X GET \  
   'http://localhost:8080/restaurant-vote/restaurants/1008/filter?start=2020-04-01&end=2020-04-30&page=0&limit=10' \   
   -H 'Authorization: Bearer AuthorizationBearerHere' \  
   `
###update restaurant
Authorized for admins only.  
`curl -X PUT \  
   http://localhost:8080/restaurant-vote/restaurants/1009 \`    
    -H 'Accept: application/json' \  
      -H 'Authorization: Bearer AuthorizationBearerHere' \  
      -H 'Content-Type: application/json,text/plain' \  
      -d '{  
    	"name":"Jasmine",  
    	"address":"Main Street 500"  
    }'  
      
###delete restaurant
Authorized for admins only.  
`curl -X DELETE \  
   http://localhost:8080/restaurant-vote/restaurants/1000 \  
   -H 'Authorization: Bearer AuthorizationBearerHere' \  
   `
       
###MEAL
###create meal
Authorized for admins only.  
`curl -X POST \  
   http://localhost:8080/restaurant-vote/meals \  `  
   -H 'Accept: application/json' \  
   -H 'Authorization: Bearer AuthorizationBearerHere' \  
   -H 'Content-Type: application/json,text/plain' \  
   -d '{  
 	"date": "2020-05-03",  
 	"description":"Pizza",  
 	"price":"25",  
 	"restaurantId":"1009"  
 }'  

  
###get meal
Authorized for admins only.  
`curl -X GET \  
   http://localhost:8080/restaurant-vote/meals/1019 \  
   -H 'Authorization: Bearer AuthorizationBearerHere' \  
   `
  
###get all meals
Authorized for admins only.  
`curl -X GET \  
   'http://localhost:8080/restaurant-vote/meals?page=0&limit=10' \  
   -H 'Authorization: Bearer AuthorizationBearerHere' \  
   `
  
###update meal
Authorized for admins only.  
`curl -X PUT \  
   http://localhost:8080/restaurant-vote/meals/1017 \ `  
    -H 'Accept: application/json' \  
      -H 'Authorization: Bearer AuthorizationBearerHere' \  
      -H 'Content-Type: application/json,text/plain' \ <br /> 
      -d '{  
    	"date": "2020-04-28",  
    	"description":"Chips & Fish",  
    	"price":"30",  
    	"restaurantId":"1008"  
        }' 
  
###delete meal
Authorized for admins only.  
`curl -X DELETE   
http://localhost:8080/restaurant-vote/meals/1019   
-H 'Authorization: Bearer AuthorizationBearerHere' \  
   `  
      
###VOTE  
###create vote
Authorized for concrete user, admins.  
`curl -X POST \  
   'http://localhost:8080/restaurant-vote/users/votes?restaurantId=1008' \`  
   -H 'Accept: application/json' \  
      -H 'Authorization: Bearer AuthorizationBearerHere' \  
      -H 'Content-Type: application/json' \  
        
      
###get votes history
Authorized for concrete user, admins.  
`curl -X GET \  
   http://localhost:8080/restaurant-vote/users/votes \`  
   -H 'Accept: application/json' \  
      -H 'Authorization: Bearer AuthorizationBearerHere' \  
      -H 'Content-Type: application/json' \  
       
    
###update vote
Authorized for concrete user, admins.  
`curl -X PUT \  
   'http://localhost:8080/restaurant-vote/users/votes?restaurantId=1009' \`  
   -H 'Accept: application/json' \  
      -H 'Authorization: Bearer AuthorizationBearerHere' \  
      -H 'Content-Type: application/json' \  
        
