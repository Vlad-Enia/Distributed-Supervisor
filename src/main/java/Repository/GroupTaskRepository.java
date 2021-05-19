package Repository;


import Entity.GroupTask;
import Entity.Student;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class GroupTaskRepository {
    public static void createGroupTask(GroupTask groupTask, EntityManagerFactory session){
        EntityManager entityManager= session.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(groupTask);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

}
