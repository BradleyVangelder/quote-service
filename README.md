# quote-service

## Run the quote service application

First of all we'll need to create a .jar file

install and test the application(mac users need to run this in bash):
```Java
mvn clean install 
```

in the root of the project I've created a Dockefile that will be used in our docker compose. The docker compose will run the mongo database and the springboot rest API

```docker
Docker compose up -d
```

After using all the command above you will be able to access the app on port 8080!
