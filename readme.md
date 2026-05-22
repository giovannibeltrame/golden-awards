# Read Me First
This REST API was developed based on the specifications contained in the files:

* backend_specification_en.pdf (English)
* backend_specification_pt_br.pdf (Portuguese)

# Getting Started

##### Configuring the movielist.csv file
For the application to read the data on startup, the following file must exist in the project root folder:

> - movielist.csv

To be read correctly, the file must have the data structure defined below:

> - year;title;studios;producers;winner

##### Running the application

To start the application, run the command below in the project root folder from your operating system's terminal:

`mvnw spring-boot:run`

##### Running the integration tests

To run the API integration tests, execute the command below:

`mvnw clean test`

##### Exploring the API operations

Once the application is running, you can see all the operations and methods available in the API at the link below:

`http://localhost:8080/swagger-ui.html`
