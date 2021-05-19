import Entity.*;
import Repository.*;

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
        //TaskRepository.createTask(new TasksEntity("Proiect la Java"),instance);
        //GroupRepository.createGroup(new Group("E4"),instance);
        //ProfessorRepository.createProfessor(new Professor("Ciobaca"),instance);
        //GradeRepository.createGrade(new Grade("Proiect la Java","Robert",9.5),instance);
        //GroupTaskRepository.createGroupTask(new GroupTask("E4","Proiect la Java"),instance);

    }
}