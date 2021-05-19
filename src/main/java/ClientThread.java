
import Entity.Group;
import Entity.Professor;
import Entity.Student;
import Entity.Task;
import Repository.GroupRepository;
import Repository.ProfessorRepository;
import Repository.StudentRepository;
import Repository.TaskRepository;
import com.google.gson.Gson;
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
        Gson gson=new Gson();
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
                        raspuns = "Register received";
                        if(request.startsWith("register1")){
                           /* Create a professor */
                            String professorJSON=request.substring(10);
                            Professor professorFromJSON=gson.fromJson(professorJSON, Professor.class);
                            try{
                                ProfessorRepository.createProfessor(professorFromJSON,instance);
                                raspuns+=" professor created!";
                            }catch(RollbackException e){
                                raspuns+=" but professor is already in the DB";
                            }finally{
                                out.println(raspuns);
                                out.flush();
                            }
                        }
                        else if(request.startsWith("register2")){
                            /* Create a student*/
                            String studentJSON=request.substring(10);
                            Student studentFromJSON=gson.fromJson(studentJSON,Student.class);
                            try{
                                StudentRepository.createStudent(studentFromJSON,instance);
                                raspuns+=" student created!";
                            }catch(RollbackException e){
                                raspuns += " but student is already in the DB";
                            }finally{
                                out.println(raspuns);
                                out.flush();
                            }
                        }else{
                            raspuns+=" but it is not a professor or student!";
                            out.println(raspuns);
                            out.flush();
                        }

                    }
                    else if (request.length() > 5 && request.startsWith("login")) {
                        raspuns="Login received ";
                        if(request.startsWith("login1")){
                            /* Login as professor */
                            String professorJSON=request.substring(7);
                            Professor professorFromJSON=gson.fromJson(professorJSON,Professor.class);
                            var found=ProfessorRepository.findProfessor(professorFromJSON,instance);
                            if(found==null){
                                raspuns+="professor does not exist!";
                            }else{
                                raspuns+=" professor logged in!";
                                logged=true;
                            }
                            out.println(raspuns);
                            out.flush();


                        }else if(request.startsWith("login2")){
                            String studentJSON=request.substring(7);
                            Student studentFromJSON=gson.fromJson(studentJSON,Student.class);
                            var found=StudentRepository.findStudent(studentFromJSON,instance);
                            if(found==null){
                                raspuns+="student does not exist!";
                            }else{
                                raspuns+=" student logged in!";
                                logged=true;
                            }
                            out.println(raspuns);
                            out.flush();
                        }


                    } else {
                        raspuns = "Server received the request \"" + request + "\", but you are not logged in ";
                        out.println(raspuns);
                        out.flush();
                    }
                }
                else if(request.startsWith("task")){
                    raspuns+="Task received ";
                    if(request.startsWith("task add")){
                        raspuns+=" to be created, ";
                        String taskJson=request.substring(9);
                        Task taskFromJSON=gson.fromJson(taskJson, Task.class);
                        try{
                            TaskRepository.createTask(taskFromJSON,instance);
                            raspuns+=" task created!";
                        }catch(RollbackException e){
                            raspuns+=" but task is already in the DB";
                        }finally{
                            out.println(raspuns);
                            out.flush();
                        }

                    } else{
                        out.println(raspuns);
                        out.flush();
                    }
                }
                else if(request.startsWith("group")){
                    raspuns+="Group received ";
                    if(request.startsWith("group add")){
                        raspuns+=" to be created, ";
                        String groupJson=request.substring(10);
                        Group groupFromJSON=gson.fromJson(groupJson,Group.class);
                        try{
                            GroupRepository.createGroup(groupFromJSON,instance);
                            raspuns+=" group created!";
                        }catch(RollbackException e){
                            raspuns+=" but the group is already in the DB";
                        }finally{
                            out.println(raspuns);
                            out.flush();
                        }
                    }else{
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