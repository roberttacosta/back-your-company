# Back Your Company

Back your company is a back-end project developed in java with spring boot.

## Installation

For best experience it is recommended that you have installed [java](https://www.oracle.com/technetwork/pt/java/javase/downloads/index.html), an IDE of your choice, and [postman](https://www.postman.com/).

For the project build use the [maven](https://maven.apache.org/download.cgi).

```
mvn clean install
```

## Database

For this project we used the database postgre. To run locally, just download the [postgree database](https://www.postgresql.org/download/) on your machine.

The project was also deployed on [heroku](https://back-your-company.herokuapp.com/), so there is no need to install postgre locally.

In the project folder there is a folder called [postman](https://github.com/roberttacosta/back-your-company/tree/master/postman), it contains the collection and the environment containing both the local environment and the heroku environment.

If you choose to run locally, when running the application your database will already be populated with some information.

## Usage

```bash

Create activity: 
curl --location --request POST 'https://back-your-company.herokuapp.com//activity' \
--header 'Content-Type: application/json' \
--data-raw '{
    "activityTitle" : "Auditoria",
    "activitySubtitle" : "Limpar conta",
    "sla" : 2
}'


Get all activities:
curl --location --request GET 'https://back-your-company.herokuapp.com//activity'


Create Card:
curl --location --request POST 'https://back-your-company.herokuapp.com//card' \
--header 'Content-Type: application/json' \
--data-raw '{
    "activityTitle" : "Auditoria",
    "patientName" : "Leandro Almeida",
    "healthInsuranceName" : "SulAmerica",
    "visitId" : 1,
    "numberOfPendencies" : 10,
    "numberOfOpenPendencies" : 8,
    "numberOfDocuments" : 4,
    "numberOfNotReceivedDocuments": 2,
    "numberOfChecklistItem": 12,
    "numberOfDoneChecklistItem": 8
}'


Create bill:
curl --location --request POST 'https://back-your-company.herokuapp.com//bill' \
--header 'Content-Type: application/json' \
--data-raw '{
    "valueBill": "215.0",
    "billType": "HOSPITALAR",
    "descriptionBill": "Cirurgia emergencial",
    "idCard": 1
}'


Get Cards With Parameters:
curl --location --request GET 'https://back-your-company.herokuapp.com//card?activityId=2&patientName=Lean
dro%20Almeida&filter=TO_RECEIVE&page=0&size=2'
```

## Testes

To run the tests, use the command below:
```
mvn test
```
