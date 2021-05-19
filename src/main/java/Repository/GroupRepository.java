package Repository;

import Entity.GroupsEntity;
import Entity.StudentsEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class GroupRepository {

    public GroupRepository() {
    }

    public static void createGroup(GroupsEntity group, EntityManagerFactory session){
        EntityManager entityManager=session.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(group);
        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
