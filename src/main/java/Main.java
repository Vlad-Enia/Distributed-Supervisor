import Entity.StudentsEntity;
import Repository.StudentRepository;

public class Main {
    public static void main(String[] args) {
        var instance= Manager.getInstance();
        System.out.println("Mai dormi afara?\nda");
        StudentRepository.createStudent(new StudentsEntity("Robert"),instance);
    }
}