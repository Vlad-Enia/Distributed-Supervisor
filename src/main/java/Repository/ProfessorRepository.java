package Repository;

import Entity.ProfessorsEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.swing.text.html.parser.Entity;

public class ProfessorRepository {

    public ProfessorRepository() {
    }

    public static void createProfessor(ProfessorsEntity professor, EntityManagerFactory session){
        EntityManager entityManager=session.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(professor);
        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
