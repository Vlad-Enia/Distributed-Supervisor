package Repository;

import Entity.StudentsEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class StudentRepository {
    StudentRepository(){
    }

    public static void createStudent(StudentsEntity student, EntityManagerFactory session){
        EntityManager entityManager=session.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(student);
        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
