package Repository;

import Entity.Task;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class TaskRepository {
    TaskRepository(){
    }

    public static void createTask(Task task, EntityManagerFactory session){
        EntityManager entityManager=session.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(task);
        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
