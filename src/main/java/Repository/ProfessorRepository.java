package Repository;

import Entity.Professor;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class ProfessorRepository {

    public ProfessorRepository() {
    }

    public static void createProfessor(Professor professor, EntityManagerFactory session){
        EntityManager entityManager=session.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(professor);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public static Professor findProfessor(Professor professor,EntityManagerFactory session){
        EntityManager entityManager= session.createEntityManager();
        entityManager.getTransaction().begin();
        Professor foundProfessor=entityManager.find(Professor.class,professor.getUsername());
        entityManager.getTransaction().commit();
        return foundProfessor;
    }
}
