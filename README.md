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

All the data will be stored into a SQL database. This
database can be modiffied over time by the clients and each update
of it can be sent to the connected clients.

## Implementation details
### 1. Relation structure
This app will monitor the activity of the students.
The students are split into groups (where each student can pe part of multiple groups). 
For each group there are a number of tasks and a number of students.
Each student can be graded at a task. 
The table logic can be found in the <b> script.sql </b> file.

### 2.Estabilishing a connection
Estabilishing a connection
A connection between a client and a server can be established
only by the client. When we want to create a connection the client
will initiate the connection request. When receiving the connection
request the server accepts it and creates a new thread that will receive further commands from the client or send updates to the client.
 
 ---------------------ADD FIGURE1 ````````````````````````````````````
 
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
 ---------------------ADD FIGURE2 ````````````````````````````````````
When a client signals an event that is not registered by the server
the thread responsible with the communication with that client will
modify the database.


