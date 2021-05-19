
import Entity.Student;
import Repository.StudentRepository;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;

import javax.persistence.RollbackException;
import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.sql.ResultSet;
import java.sql.SQLException;

class ClientThread extends Thread {
    private static Integer TIMEOUT_TIME=3600_000; // change with a smaller number to see the results
    private Socket socket = null;

    private boolean logged = false;
    private String username = "";

    public ClientThread(Socket socket) throws SQLException, SocketException {
        this.socket = socket;
        socket.setSoTimeout(TIMEOUT_TIME); /// sec*1000
    }

    public void run() {
        try {
            var instance= Manager.getInstance();
            while (true) {
                // Get the request from the input stream: client → server
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(socket.getInputStream()));
                String request = in.readLine();
                // Send the response to the oputput stream: server → client
                PrintWriter out = new PrintWriter(socket.getOutputStream());
                String raspuns = "";
                if (request.compareTo("stop") == 0) {
                    raspuns = "Server shutdown...";
                    out.println(raspuns);
                    out.flush();
                    Server.stopServerGracefully();
                } else if (request.equals("exit")) {
                    System.out.println("Client disconnected. Stopping thread...");
                    return;
                }
                if (!logged) {
                    if (request.length() > 8 && request.startsWith("register")) {
                      /* Create a student currently*/
                        raspuns = "Register received";
                        String name=request.substring(9);
                        try{
                            StudentRepository.createStudent(new Student(name),instance);
                            raspuns+=" user created!";
                        }catch(RollbackException e){
                            raspuns += " but user is already in the DB";
                        }finally{
                            out.println(raspuns);
                            out.flush();
                        }


                    } else if (request.length() > 5 && request.startsWith("login")) {

                    } else {
                        raspuns = "Server received the request \"" + request + "\", but you are not logged in ";
                        out.println(raspuns);
                        out.flush();
                    }
                }
                else {
                    raspuns = "Server received the request " + request + " but ?????";
                    out.println(raspuns);
                    out.flush();
                }

            }

        } catch(SocketTimeoutException e){
            System.out.printf("%d seconds passed since the last request. Timeout.",TIMEOUT_TIME/1000);
        }catch (IOException e) {
            System.err.println("Communication error... " + e);
        }finally {
            try {
                socket.close();
                System.out.println("Thread stopped");
                Server.threadCount--;
            } catch (IOException e) {
                System.err.println(e);
            }
        }
    }
}