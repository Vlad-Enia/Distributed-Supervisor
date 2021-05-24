
import Entity.*;
import Entity.GroupProfessor;
import Exceptions.DuplicatedObjectException;
import Exceptions.ParentKeyException;
import Repository.*;
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
import java.util.ArrayList;
import java.util.List;

class ClientThread extends Thread {
    private static Integer TIMEOUT_TIME=3600_000; // change with a smaller number to see the results
    private Socket socket = null;

    private boolean logged = false;
    private String username = "";

    public ClientThread(Socket socket) throws SQLException, SocketException {
        this.socket = socket;
        socket.setSoTimeout(TIMEOUT_TIME); /// sec*1000
    }

    List<GradeCustomObject> createCustomGradeObject(List<Grade> grades){
        List<GradeCustomObject> ans=new ArrayList<>();
        if(!grades.isEmpty()){
            GradeCustomObject obj=new GradeCustomObject();
            obj.task=grades.get(0).getTask();
            int i=0;
            for(var grade:grades){
                if(grade.getTask().equals(obj.task)){
                    obj.student.add(grade.getStudent());
                    obj.grade.add(grade.getGrade());
                }else{
                    ans.add(obj);
                    obj=new GradeCustomObject();
                    obj.task=grade.getTask();
                    obj.student.add(grade.getStudent());
                    obj.grade.add(grade.getGrade());
                }
                ++i;
            }
            ans.add(obj);
            return ans;
        }else{
            return null;
        }
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
                System.out.println(request);
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
                else if (!logged) {
//                    if (request.length() > 8 && request.startsWith("register")) {
//                        raspuns = "Register received";
//                        if(request.startsWith("register1")){
//                           /* Create a professor */
//                            String professorJSON=request.substring(10);
//                            Professor professorFromJSON=gson.fromJson(professorJSON, Professor.class);
//                            try{
//                                ProfessorRepository.createProfessor(professorFromJSON,instance);
//                                raspuns+=" professor created!";
//                            }catch(RollbackException e){
//                                raspuns+=" but professor is already in the DB";
//                            }finally{
//                                out.println(raspuns);
//                                out.flush();
//                            }
//                        }
//                        else if(request.startsWith("register2")){
//                            /* Create a student*/
//                            String studentJSON=request.substring(10);
//                            Student studentFromJSON=gson.fromJson(studentJSON,Student.class);
//                            try{
//                                StudentRepository.createStudent(studentFromJSON,instance);
//                                raspuns+=" student created!";
//                            }catch(RollbackException e){
//                                raspuns += " but student is already in the DB";
//                            }finally{
//                                out.println(raspuns);
//                                out.flush();
//                            }
//                        }else{
//                            raspuns+=" but it is not a professor or student!";
//                            out.println(raspuns);
//                            out.flush();
//                        }
//
//                    }
                     if (request.length() > 5 && request.startsWith("login")) {
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
                else if(request.startsWith("grade")){
                    raspuns+="Grade received ";
                    if(request.startsWith("grade add")){
                        raspuns+="to be created, ";
                        String gradeJSON=request.substring(10);
                        Grade gradeFromJSON=gson.fromJson(gradeJSON,Grade.class);
                        try{
                            GradeRepository.createGrade(gradeFromJSON,instance);
                            raspuns+="grade created!";
                        }catch(ParentKeyException| DuplicatedObjectException e){
                            raspuns+=e;
                        }finally
                        {
                            out.println(raspuns);
                            out.flush();
                        }
                    }
                    else if(request.startsWith("grade get")){
                        raspuns+="to be returned, ";
                        if(request.startsWith("grade get all")){
                            raspuns+="all grades";
                            String groupJson=request.substring(14);
                            Group groupFromJson=gson.fromJson(groupJson,Group.class);
                            var foundGrades=GradeRepository.findAllGrades(groupFromJson,instance);

                            var customGrade=createCustomGradeObject(foundGrades);

                           // String gradeList=gson.toJson(foundGrades);/* ans is an array of grades (lazy method) */
                            String customGradeList=gson.toJson(customGrade); /* Check GradeCustomObject to see the format of this response */
                           // raspuns+=gradeList;
                            raspuns+=" "+customGradeList;
                            out.println(raspuns);
                            out.flush();
                        }
                    }
                    else if(request.startsWith("grade update")){
                        raspuns+="to be updated, ";
                        String gradeJSON=request.substring(13);
                        Grade gradeFromJSON=gson.fromJson(gradeJSON,Grade.class);
                        try{
                            GradeRepository.updateGrade(gradeFromJSON,instance);
                            raspuns+="grade updated!";
                        }catch(ParentKeyException| DuplicatedObjectException e){
                            raspuns+=e;
                        }finally
                        {
                            out.println(raspuns);
                            out.flush();
                        }
                    }else{
                        out.println(raspuns);
                        out.flush();
                    }
                }
                else if(request.startsWith("group-professor")){
                    raspuns+="Group-Professor received ";
                    if(request.startsWith("group-professor add")){
                        raspuns+="to be created, ";
                        String groupProfessorJson=request.substring(20);
                        GroupProfessor groupProfessorFromJSOM=gson.fromJson(groupProfessorJson, GroupProfessor.class);
                        try{
                            GroupProfessorRepository.createGroupProfessor(groupProfessorFromJSOM,instance);
                            raspuns+="group-professor created!";
                        }catch(ParentKeyException| DuplicatedObjectException e){
                            raspuns+=e;
                        }finally
                        {
                            out.println(raspuns);
                            out.flush();
                        }
                    }else{
                        out.println(raspuns);
                        out.flush();
                    }
                }
                else if(request.startsWith("group-student")){
                    raspuns+="Group-Student received ";
                    if(request.startsWith("group-student add")){
                        raspuns+="to be created, ";
                        String groupStudentJson=request.substring(18);
                        GroupStudent groupStudentFromJSON=gson.fromJson(groupStudentJson, GroupStudent.class);
                        try{
                            GroupStudentRepository.createGroupStudent(groupStudentFromJSON,instance);
                            raspuns+="group-student created!";
                        }catch(ParentKeyException| DuplicatedObjectException e){
                            raspuns+=e;
                        }finally
                        {
                            out.println(raspuns);
                            out.flush();
                        }
                    }
                    else if(request.startsWith("group-student get")){
                        raspuns+="to be returned, ";
                        if(request.startsWith("group-student get all")){
                            raspuns+="all students ";
                            String groupJson=request.substring(22);
                            Group groupFromJSON=gson.fromJson(groupJson,Group.class);
                            var foundStudents=GroupStudentRepository.findAllStudents(groupFromJSON,instance);
                            String studentList=gson.toJson(foundStudents);
                            raspuns+=studentList;
                            out.println(raspuns);
                            out.flush();
                        }
                    }
                    else if(request.startsWith("group-student delete")){
                        raspuns+="to be deleted, ";
                        String groupStudentJson=request.substring(21);
                        GroupStudent groupStudentFromJSON=gson.fromJson(groupStudentJson,GroupStudent.class);
                        GroupStudentRepository.deleteRelation(groupStudentFromJSON,instance);
                        raspuns+="success!";
                        out.println(raspuns);
                        out.flush();
                    }
                    else{
                        out.println(raspuns);
                        out.flush();
                    }
                }
                else if(request.startsWith("group-task")){
                    raspuns+="Group-Task received ";
                    if(request.startsWith("group-task add")){
                        raspuns+="to be created, ";
                        String groupTaskJson=request.substring(15);
                        GroupTask groupTaskFromJSON=gson.fromJson(groupTaskJson,GroupTask.class);
                        try{
                            GroupTaskRepository.createGroupTask(groupTaskFromJSON,instance);
                            raspuns += "group-task created!";
                        }catch(ParentKeyException| DuplicatedObjectException e) {
                            raspuns+=e;
                        }finally
                        {
                            out.println(raspuns);
                            out.flush();
                        }
                    }else if(request.startsWith("group-task get")){
                        raspuns+="to be returned, ";
                        if(request.startsWith("group-task get all")) {
                            raspuns+="all tasks ";
                        String groupJson=request.substring(19);
                        Group groupFromJSON=gson.fromJson(groupJson,Group.class);
                            var foundTasks=GroupTaskRepository.findAllTasks(groupFromJSON,instance);
                            String taskList= gson.toJson(foundTasks);
                            raspuns+=taskList;
                            out.println(raspuns);
                            out.flush();
                        }
                    }else{
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
                    }
                    else if(request.startsWith("group get")){
                        raspuns+="to be returned, ";
                        if(request.startsWith("group get all")){
                            raspuns+="all groups";
                            var foundGroups=GroupRepository.findAllGroups(instance);
                            String groupList=gson.toJson(foundGroups);
                            raspuns+=groupList;
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