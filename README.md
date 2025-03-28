# Laboratory Reservation System

The purpose of this project is to develop an application for laboratory reservations, specifically for the laboratories in the systems area of the Universidad Escuela Colombiana de Ingeniería Julio Garavito. This application will allow users to reserve each laboratory within the permitted schedule, as well as cancel reservations if necessary, and consult any type of information about each laboratory they need, all through a user-friendly and intuitive web interface. This repository is specifically for the Backend area; if you want to check the status of the front, follow this link: https://github.com/Waldron63/FrutiFront

## Tech Stack
- Java OpenJDK 17
- Spring Boot
- MongoDB Atlas
- Maven 3.9.x
- Docker
- JaCoCo for test coverage
- Sonar for code quality

## Development Team

- Alejandro Prieto
- Juan David
- María Paula Sánchez
- Santiago Gualdron

## Procedure:
### Diagram
To start this project, we begin by creating a class diagram, to understand how objects are related within the database (in this case MongoDB).


![image](https://github.com/user-attachments/assets/f97c31c1-b686-4164-bc43-c901b09971a0)

### maven
Once we understand the relationship of the objects, we create the project with maven in SpringBoot; and the respective dependencies for the web, mongo, Jacoco, etc.


![image](https://github.com/user-attachments/assets/d8682104-c07d-4693-84f4-707efc21dd17)
![image](https://github.com/user-attachments/assets/d5994e0c-002c-43af-b781-30feadf1506e)

### Package
Maven and the dependencies are already working, now we start to generate the package system to have an order of all the java classes that will be created.
Among these we will have
- edu.eci.cvds.labReserves: the root path of all the main of the project (it has internal to the LabReserve that executes the project).
- model: with all the logic of objects, relations, arrays, attributes, getters and setters. It was made this way with the intention of being extensible in the future to add other databases.
- collections: contains the @Documents of each class that will be stored in MongoDB, with their IDs and important constructors.
- repository.mongodb: has the interfaces that extend mongoRepository to generate the CRUD in the DB, in this case we think of having 3 controllers, so there will be 3 interfaces for the database, plus an extra one that will help to store a kind of relationship.
- service: the logic of the CRUD to store the objects created in the DB.
- controller: the relation between front and back, where there are only the 3 most important: user, reserve and laboratory; this has the call of all the necessary methods to store the objects sent in front with back.
- dto: specifically has 1 class that helps to store reservation data, it helps to create ReserveMongoDB and ScheduleMongoDB objects, the latter being a relation between Reserve and Laboratory.

![image](https://github.com/user-attachments/assets/fccf69fa-56bc-4efb-9aec-5ae76e36a7b9)

### Unit Test and JACOCO
For the unit tests, all classes referenced by model were considered, the classes in the other packages were not considered, since the tests of services, controllers and interfaces are done with mockito, which only simulates the procedure, it's not a real test; collections have the same methods as models, since all classes are extended, and the main LabReserve only has the main execution method.

![image](https://github.com/user-attachments/assets/68ffa7e3-3d72-411e-9311-8baeb56319f1)
using the mvn test command, we test all created unit tests:

![image](https://github.com/user-attachments/assets/d6022ca1-3bcf-45c8-965e-f0cce071a7f0)

and the coverage shown by jacoco, in this case has a percentage of 87%:

![image](https://github.com/user-attachments/assets/f6848898-cbab-4206-afca-ac27c6ec0d2a)
![image](https://github.com/user-attachments/assets/f995be5f-00a2-4ba8-a466-d1b41ca52b43)


### MongoDB
To save the data in mongoDB, we decided to use the 2 ways of saving data (in JSON type documents):
1. referenced: the data of : User, Reserve and Schedule will be saved.
(a lot of data can be generated, and are constantly being searched)
2. embedded: it will store in the main Laboratory class, its important data plus the Resource data (physical and Software), and ScheduleReference.

Finally, we created the 4 members of the team, a MongoDB atlas (cloud) account to access the future data we will create:

![image](https://github.com/user-attachments/assets/ca0649b2-580c-4d54-85a9-0ee0f7e595ad)

En application.properties, gracias a SpringBoot, podemos modificar la url para poder conectarnos con la bd por una url, ademas de añadir ciertas configuraciones que pueden servir para las pruebas test, etc:

![image](https://github.com/user-attachments/assets/c415eaf8-9ca2-4102-b9df-c2683a865ff7)


### Azure

In order to coordinate the activities, repositories, pipelines and others, we use the AzureDevOps platform for the coordination and distribution of the team.

![image](https://github.com/user-attachments/assets/93c70162-a2f9-49d8-b0e5-3f7b837da4da)

And generate an epic that includes both front-end and back-end tasks. 4 functions to distribute the main areas of the project: 
1. Consult details of laboratories, reservations and users: the code is generated to be able to visualise the important of each of the objects that will be necessary for the front (GET). Person in charge: Juan David Martinez Mendez
2. Management of laboratories: the corresponding code for the laboratory and its embedded objects, as well as the initial CRUD that can be used by the front end. Person in charge: Maria Paula Sanchez Macias
3. Manage Reservations: the respective code for Reserve and Schedule, plus the initial CRUD that can be used by the front-end and its relationship with Laboratory and User. Person in charge: Santiago Gualdron Rincon
4. Manage Users: the corresponding code for User, plus the initial CRUD that can be used by the front-end. Person in charge: Samuel Alejandro Prieto Reyes

![image](https://github.com/user-attachments/assets/c9c08d13-67ab-4287-9526-c8a973463097)

Within each feature are the respective user stories, tasks and tests that each person must generate.

![image](https://github.com/user-attachments/assets/07257ace-b6d5-457b-a596-eb898bd0a61e)

### Postman
In order to start testing that the HTTP works, connects to the database and can be used to relate front to back, we will use the Postman platform to generate the POST, DELETE, GET, UPDATE tests.
Tests for Reserve:

![image](https://github.com/user-attachments/assets/97714708-b8da-40f3-b662-fb792be2bbbc)

Tests for User:

![image](https://github.com/user-attachments/assets/6e77dd72-0c6e-472b-a6ff-9ef2b8d99b0f)


Tests for Laboratory:

![image](https://github.com/user-attachments/assets/c0e2065d-57a7-4bd5-8b4b-227a77c4dd1a)


## CI/CD
### Pruebas Unitarias para ReserveService
To generate the unit tests of the services, we implemented the Mockito library. It helps us to simulate the behaviour of the methods implemented in the service, which store in the database and return the json: this is what we call Mockito:
![image](https://github.com/user-attachments/assets/4f1c0ffd-c69b-447c-a600-4f4ee14a068a)

example of the use of mockito:
![image](https://github.com/user-attachments/assets/727c0894-3252-4780-9226-c13d233e5f08)

functionality test of the ReserveService tests:

![image](https://github.com/user-attachments/assets/7e1f0633-2635-45d1-992d-43c780321d3b)

### Git Flow Actions:
Before implementing the gitflow actions in this repository, a copy repository, identical to the main repository, was created to test the generation, creation and structure of the .yml files.
This structure has the same project creation details as shown at the beginning of this document.
It also has a task structure of: Build, Test, Deploy (each requiring the previous to function).

test repository for pipelines: https://github.com/Waldron63/copySunTeaBack

There are 2 types of files required for the development of this part:
1. pipeline that is triggered by push or pull request to main: main_labrreserveeci.yml
   app-name: LabReserveEci -> [labreserveecidevelop-cbfjhdbqb3h5end7.canadacentral-01.azurewebsites.net](https://labreserveecidevelop-cbfjhdbqb3h5end7.canadacentral-01.azurewebsites.net/)

   ![image](https://github.com/user-attachments/assets/b448ced8-6a16-4974-89f6-1215ddc2a0c7)
   ![image](https://github.com/user-attachments/assets/9907c540-8696-454e-b837-48284dae823b)

3. pipeline that is triggered by a push or pull request to develop: develop_labrreserveecidevelop.yml
   app-name: LabReserveEciDevelop -> [labreserveecidevelop-cbfjhdbqb3h5end7.canadacentral-01.azurewebsites.net](https://labreserveecidevelop-cbfjhdbqb3h5end7.canadacentral-01.azurewebsites.net/)

   ![image](https://github.com/user-attachments/assets/873b0279-dfe5-45e6-ac80-91a858f283b3)
   ![image](https://github.com/user-attachments/assets/db178270-1dae-4ef5-a7f3-a1df5f94624f)

## REACT
In this case, a first code was generated in HTML, CSS and JS, which has a complete functionality between the back and the front. 
But we moved the code to React, where we also restructured the graphic design of the site:
Link to see the mockup of the final design of the site (FIGMA):
https://www.figma.com/design/ywe4fMaXpMjBt53KG8gCqE/CVDS-PROYECTO-1?m=auto&t=KPdovwRh6Pr1C1jy-1

Link to the frontend repository where the HTML and React codes are located:
https://github.com/Waldron63/FrutiFront

## SEGURIDAD
