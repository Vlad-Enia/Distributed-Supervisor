package Repository;

import Entity.GroupProfessor;
import Entity.GroupProfessorPK;
import Exceptions.DuplicatedObjectException;
import Exceptions.ParentKeyException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.RollbackException;

public class GroupProfessorRepository {
    public static void createGroupProfessor(GroupProfessor groupProfessor, EntityManagerFactory session){
        var found=GroupProfessorRepository.findGroupProfessor(new GroupProfessorPK(groupProfessor.getGroupname(), groupProfessor.getProfessor()),session);
        if(found==null){
            try{
                EntityManager entityManager= session.createEntityManager();
                entityManager.getTransaction().begin();
                entityManager.persist(groupProfessor);
                entityManager.getTransaction().commit();
                entityManager.close();
            }
            catch(RollbackException e){
                throw new ParentKeyException(GroupProfessor.class.getName());
            }
        }
        else throw  new DuplicatedObjectException(GroupProfessor.class.getName());
    }

    public static GroupProfessor findGroupProfessor(GroupProfessorPK pk, EntityManagerFactory session){
        EntityManager entityManager= session.createEntityManager();
        entityManager.getTransaction().begin();
        GroupProfessor foundGroupProfessor=entityManager.find(GroupProfessor.class,pk);
        entityManager.getTransaction().commit();
        return foundGroupProfessor;
    }
}
