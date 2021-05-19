import Entity.*;
import Repository.*;
import com.google.gson.Gson;

import javax.persistence.RollbackException;

public class Main {
    public static void main(String[] args) {
        var instance= Manager.getInstance();
        System.out.println("Mai dormi afara?\nda");
        try{
            StudentRepository.createStudent(new Student("Robert"),instance);
        }
        catch(RollbackException e){
            System.out.println(e);
        }
        String json="{\"username\":\"DanChisu\"}";
        Gson g = new Gson();

        Student s=g.fromJson(json,Student.class);

        System.out.println(s);

        //TaskRepository.createTask(new TasksEntity("Proiect la Java"),instance);
        //GroupRepository.createGroup(new Group("E4"),instance);
        //ProfessorRepository.createProfessor(new Professor("Ciobaca"),instance);
        //GradeRepository.createGrade(new Grade("Proiect la Java","Robert",9.5),instance);
        //GroupTaskRepository.createGroupTask(new GroupTask("E4","Proiect la Java"),instance);

    }
}