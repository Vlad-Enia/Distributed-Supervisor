package Repository;

import Entity.Group;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class GroupRepository {

    public GroupRepository() {
    }

    public static void createGroup(Group group, EntityManagerFactory session){
        EntityManager entityManager=session.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(group);
        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
