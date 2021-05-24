# Distributed-Supervisor

## Application architecture
The application will be based upon the Client-Server model
with a concurrent server. In this way, we are avoiding the starvation problem that appears when using an iterative server with an
increased number of clients.

The project implementation is based on the Transmission Control
Protocol (TCP). This connection oriented method was chosen in
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
will be done using sockets.  

All the data will be stored into a SQL database. This
database can be modiffied over time by the clients and each update
of it can be sent to the connected clients.
