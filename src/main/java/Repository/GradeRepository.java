package Repository;

import Entity.Grade;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class GradeRepository {
    public GradeRepository(){
    }

    public static void createGrade(Grade grade, EntityManagerFactory session){
        EntityManager entityManager= session.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(grade);
        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
