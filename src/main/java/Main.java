import Entity.Group;
import Entity.Professor;
import Repository.GroupRepository;
import Repository.ProfessorRepository;

public class Main {
    public static void main(String[] args) {
        var instance= Manager.getInstance();
        System.out.println("Mai dormi afara?\nda");
        //StudentRepository.createStudent(new StudentsEntity("Robert"),instance);
        //TaskRepository.createTask(new TasksEntity("Proiect la Java"),instance);
        GroupRepository.createGroup(new Group("E4"),instance);
        ProfessorRepository.createProfessor(new Professor("Ciobaca"),instance);
    }
}