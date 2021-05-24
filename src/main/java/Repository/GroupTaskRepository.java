package Repository;


import Entity.*;
import Exceptions.DuplicatedObjectException;
import Exceptions.ParentKeyException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.RollbackException;
import java.lang.reflect.Array;
import java.util.List;

public class GroupTaskRepository {
    public static void createGroupTask(GroupTask groupTask, EntityManagerFactory session){
        var found=GroupTaskRepository.findGroupTask(new GroupTaskPK(groupTask.getGroupname(),groupTask.getTask()),session);
        if(found==null){
            try{
                EntityManager entityManager= session.createEntityManager();
                entityManager.getTransaction().begin();
                entityManager.persist(groupTask);
                entityManager.getTransaction().commit();
                entityManager.close();
            }
            catch(RollbackException e){
                throw new ParentKeyException(GroupTask.class.getName());
            }
        }
        else throw new DuplicatedObjectException(GroupTask.class.getName());
    }

    public static GroupTask findGroupTask(GroupTaskPK pk, EntityManagerFactory session){
        EntityManager entityManager=session.createEntityManager();
        entityManager.getTransaction().begin();
        GroupTask foundGroupTask=entityManager.find(GroupTask.class,pk);
        entityManager.getTransaction().commit();
        return foundGroupTask;
    }

    public static List<Task> findAllTasks(Group group, EntityManagerFactory session){
        EntityManager entityManager=session.createEntityManager();
        entityManager.getTransaction().begin();
        Query q=entityManager.createNamedQuery("GroupTask.findByGroup").setParameter("gr",group.getName());
        entityManager.getTransaction().commit();
        List<Task> foundTasks=q.getResultList();
        entityManager.close();
        return foundTasks;
    }

    public static void deleteRelation(GroupTask groupTask,EntityManagerFactory session){
        EntityManager entityManager= session.createEntityManager();
        entityManager.getTransaction().begin();
        GroupTask foundGroupTask=entityManager.find(GroupTask.class,new GroupTaskPK(groupTask.getGroupname(), groupTask.getTask()));
        entityManager.remove(foundGroupTask);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

}
