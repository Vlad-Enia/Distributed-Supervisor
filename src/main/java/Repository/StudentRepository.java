package Repository;

import Entity.Student;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class StudentRepository {
    StudentRepository(){
    }

    public static void createStudent(Student student, EntityManagerFactory session){
        EntityManager entityManager=session.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(student);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public static Student findStudent(Student student,EntityManagerFactory session){
            EntityManager entityManager=session.createEntityManager();
            entityManager.getTransaction().begin();
            Student foundStudent=entityManager.find(Student.class,student.getUsername());
            entityManager.getTransaction().commit();
            return foundStudent;
    }
}
