# Distributed-Supervisor

## Application architecture
The application will be based upon the <b>Client-Server</b> model
with a <b>concurrent</b> server. In this way, we are avoiding the starvation problem that appears when using an iterative server with an
increased number of clients.

The project implementation is based on the Transmission Control
Protocol (<b>TCP</b>). This connection oriented method was chosen in
order to assure a safe transport of data between the clients and the
server. For this type of application we must be sure that we are not
losing any data packets during the transfer nor receiving them in
a different order. In addition to this, it is more reliable to keep a
constant connection between the clients and the server in order to
send data updates to the participants. Therefore, the TCP protocol
will be used for implementing the application.

The concurrency pattern used will be <b>threading</b>. For each client
the server will create a new thread that will execute the commands
required. Threading was chosen over forking because of the common
memory zone present in the technique, allowing the server to send
information faster to the clients.

The information transmitted between the client and the server
will be done using <b>sockets</b>.  

All the data will be stored into a SQL database. The database is manipulated using JPA (Hibernate).
This database can be modiffied over time by the clients and each update
of it can be sent to the connected clients.

## Implementation details
### 1. Relation structure
This app will monitor the activity of the students.
The students are split into groups (where each student can pe part of multiple groups). 
For each group there are a number of tasks and a number of students.
Each student can be graded at a task. 
The table logic can be found in the <b> script.sql </b> file.

### 2.Estabilishing a connection
A connection between a client and a server can be established
only by the client. When we want to create a connection the client
will initiate the connection request. When receiving the connection
request the server accepts it and creates a new thread that will receive further commands from the client or send updates to the client.
 
![figure1](https://user-images.githubusercontent.com/58529493/119331788-71790080-bc90-11eb-989f-fd6bde466e9b.png)

 
 ### 3. Client-Server communication
 In the application we deffne two types of communication operation: Interogations and updates. An interogation is a request that
doesn't alter the database while an update modiffes it.
1. The types of interogations are:
* Data request from a client for receiving the groups, tasks, students, grades...
2. The types of updates are:
* A client signals an information that is not in the database already
to the server (add student/task/group)
* A set of clients signal the absence of an event that is present in
the database (remove student/task/group)

![figure2](https://user-images.githubusercontent.com/58529493/119331897-8c4b7500-bc90-11eb-8cdd-2640ce73077b.png)

When a client signals an event that is not registered by the server
the thread responsible with the communication with that client will
modify the database.

## Communication protocol:
The server receives commands from clients as a string made of two components: 
*Tokenized words that are used to identify the command
*Auxiliary objects in JSON form (Using Google JSON API (Gson))

Available commands:
### Register
Register an user to the app (register1 for professors register2 for students)

### Login
Login an user in the app (login1 for professors, login2 for students)

### Grade 
(manipulate grade objects)

* grade add "taskName" "studentName" "grade" - Add a grade to the database (this command converts the parameters given into a gradeObject)

* grade get all (Returns an array of grades OR a custom object used for the table reconstruction (Array of customObj), (customObj)={task,Array of Students, Array of grades))

* grade update "taskName" "studentName" "grade" - Update a grade from the database (this command converts the parameters given into a gradeObject)

### Group-student
(manipulate group-student relations)

* groupStudent add "groupName" "studentName" -  Add a student to a group (this command converts the parameters given into a groupStudentObject)

* groupStudent get all "groupName" - Returns an array of Students associated with that group

* groupStudent delete "groupName" "studentName"  -  Remove a student from a group (this command converts the parameters given into a groupStudentObject)

### Group-task
* groupTask add "groupName" "taskName" -  Add a task to a group (this command converts the parameters given into a groupTaskObject)

* groupTask get all "groupName" - Returns an array of Tasks associated with that group

* groupTask delete "groupName" "taskName"  -  Remove a task from a group (this command converts the parameters given into a groupTaskObject)

### Task, Group, Student, Professor... (Unique entities)
* Task|Group|Student add name -  Add entity to the database 

* Task|Group|Student name - Returns an array of entity objects

* Task|Group|Student delete name  -  Remove an entity

