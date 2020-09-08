# smooth-recruiter
A microservice based backend REST API for application, which handles recruitement process entirely. Work in progress

## Installation
This is a Spring Boot Java application with each microservice having own Dockerfile. On the top of that docker-compose is orchestrating all the docker containers.
As the application is still in its' very early stage application.properties are not removed, since it is only for development purposes.

To run this application you should have installed on your local machine:
1. JDK - the project uses JDK 14;
2. Docker;
3. Maven;

In order to check the application working, you should:

1. Clone the repository
2. Go inside each folder in the main repor - those are respective services.
3. When inside each of those microservices projects, run mvn command in terminal to build project. The command you nned to run:
  ```bash
  mvn clean package -DskipTests
  ```
 4. Go to the smooth-recruiter folder (this is where the docker-compose.yml) is located. Open terminal and run:
 ```bash
 docker-compose up --build -d
 ```
 This will run all the containers in detached mode. Now you should wait a while for the Eureka Server to get up and running
 and for the services to register themselves in the Eureka Server. This can take a while. 
 
 5. You should be able to check if the project was built successfully, by opening your browser and going to URL: localhost:8761.
 This should open the page with Eureka Server dashboard. You should see services registered in the Eureka Server as well.
 
 ## Next steps
 As the work is still in its' early stage, there is no API documentation. Currently I will be working on adding one to the API-GATEWAY microservices.
 Stay tuned!
