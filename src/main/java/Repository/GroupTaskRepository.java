package Repository;


import Entity.GroupTask;
import Entity.GroupTaskPK;
import Entity.Student;
import Exceptions.DuplicatedObjectException;
import Exceptions.ParentKeyException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.RollbackException;

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
            //return "OK";
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

}
