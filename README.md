# TuitionReimbursementManagementSystem

## Project Description

The Tuition Reimbursement System, TRMS, allows users to submit reimbursements for courses and training. The submitted reimbursement must be approved by that employee's supervisor, department head, and benefits coordinator. The benefits coordinator then reviews the grade received before finalizing the reimbursement.

## Technologies Used

* Java            -version (1.8 or greater)
* HTML            - version 5.0
* BootStrap       - version 4
* CSS             - version 3
* Oracle Database - verson 11.2.0.4 
* Apache Tomcat   -version 8.5

## Features

List of features ready and TODOs for future development
* Employees and mangaers Can login into app using the correct email and password.
* Any employee can submit a reimbursement request through the app
* A Employee can view previously submitted requests and see their status
* Managers can accept, reject, or request more info from the employee through a messaging system
* Employee is notified through messaging system of a denial or request for more feedback
* Manager is notified of the employee FeedBack when it is sent and then notified to approve or deny request
* Employee can see pending pending payouts that have been preapproved or awarded. 



## Getting Started
   
(include git clone command)
(include all environment setup steps)
> Clone the repository with git command: git clone https://github.com/ReesePoche/TuitionReimbursementManagementSystem.git
> In an Oracle Database, run the SQL script named project1 from the SQL directory in the project.
> Change password and location of database you will use in the JDBCConnection class located in src/main/java/dev/reese/project1/util/
> Install and setup an Apache Tomcat server version 8.5.
> Ensure all dependencies located in the projects Pom.xml are downloaded
> Run the project on the setup Tomcat Server
> In a browser on the same machine the server is running type in the url: http://localhost:8080/dev.reese.project1/LoginPage.goto
> You should be at the login page. 
> Check Oracle Database for some valid login credential with command:  select * from employees;


## Usage



