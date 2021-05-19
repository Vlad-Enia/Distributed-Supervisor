package Repository;

import Entity.GroupStudent;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class GroupStudentRepository {

    public GroupStudentRepository() {
    }

    public static void createGroupStudent(GroupStudent groupStudent, EntityManagerFactory session) {
        EntityManager entityManager = session.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(groupStudent);
        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
