package Repository;

import Entity.Group;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import java.util.List;

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

    public static List<Group> findAllGroups(EntityManagerFactory session){
        EntityManager entityManager=session.createEntityManager();
        entityManager.getTransaction().begin();
        Query q=entityManager.createNamedQuery("Group.findAllGroups");
        entityManager.getTransaction().commit();
        List<Group> foundGroups=q.getResultList();
        entityManager.close();
        return foundGroups;
    }
}
