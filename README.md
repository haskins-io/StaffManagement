# READ ME

Currently this is just me messing around with Kotlin and Compose for Desktop.

## Overview
The basic premise of this application is that it can be used by multiple managers within an organisation, with each 
application using a shared database. This allows all managers to see what is being worked on with the organisation.

### Implementation

#### Base Framework
The application is being developed using Compose Multiplatorm, targeting a Desktop application. 

#### Database
It requires a postgres database to work. Schema in the sql_schema folder. **Note**: I'm only going to be uploading a 
full schema file at this point, as I'm still developing this, and things might change.

The JetBrains Exposed framework is being used to communicate with the databases.

#### Theme
I'm using the Jetbrains Jewel stand-alone theme to try and make it look like a Jetbrains application.

## Things to do
Crud the things

### Projects
* ~~employees to projects~~
* ~~notes / Comments~~
* budget
* Status - Red/Amber/Green
* Priority
* Due Date
* background information about project. Where did it comes. Why are we doing this, etc?
* tasks to project
* Project Team?
* Progress

### Employee
* Holiday tracking

## Possible ideas
* Nice Timeline/calendar view for employee allocation start/end against project
* Generate reports

## Would be brilliant to have
* Integration into tools such as JIRA and those types of things
* multi db support
* Authentication/Authorisation
