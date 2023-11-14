# Medusa Demo Testautomation with Selenium and Cucumber
## Introduction
This project shows how to automate tests using Selenium and Cucumber.

## How to run
### Precondition
As a precondition, this project requires an running instance of Medusa Online Shop (backend and frontend) as described in the related [https://docs.medusajs.com/](Documentation).
The testcleint expects the system running on 
- frontend: localhost:8000
- REST API: localhost:9000

### Start the test

> mvn clean install

## Structure

The hierarchy of this project is based on the control structure of Cucumber plus the idea of Page Object Pattern:

- A **Feature File** defines the behavior of the test. It includes the test configuration and flow, and the expected results. The feature is written in "customer language".
- The **Step Definitions** are responsible to translate the feature files into "programming language" and to perform the interactions with the AUT (application under test).
- A **Page** represents a real (sub-) page of the (Web-) Frontend of the AUT. It provides a programming interface being used by Step Definitions.
- The package **API** provides a domain specific, high level programming interface being used by Step Definitions. It uses the Rest API of the AUT (Store, Backend).  
- The package **DATA** contains a domain data model. During testing, it is used for test data creation, update and comparison with expected data. 


### Features
t.b.d

### Steps
t.b.d


### Pages
t.b.d

### REST API
t.b.d

### Data
t.b.d

