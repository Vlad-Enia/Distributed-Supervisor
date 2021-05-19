import Entity.StudentsEntity;
import Entity.TasksEntity;
import Repository.StudentRepository;
import Repository.TaskRepository;

public class Main {
    public static void main(String[] args) {
        var instance= Manager.getInstance();
        System.out.println("Mai dormi afara?\nda");
        //StudentRepository.createStudent(new StudentsEntity("Robert"),instance);
        //TaskRepository.createTask(new TasksEntity("Proiect la Java"),instance);
    }
}