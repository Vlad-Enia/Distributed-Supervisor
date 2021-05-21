package Repository;

import Entity.*;
import Exceptions.DuplicatedObjectException;
import Exceptions.ParentKeyException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.RollbackException;
import java.util.List;

public class GroupStudentRepository {

    public GroupStudentRepository() {
    }

    public static void createGroupStudent(GroupStudent groupStudent, EntityManagerFactory session) {
        var found=GroupStudentRepository.findGroupStudent(new GroupStudentPK(groupStudent.getGroupname(),groupStudent.getStudent()),session);
        if(found==null){
            try{
                EntityManager entityManager = session.createEntityManager();
                entityManager.getTransaction().begin();
                entityManager.persist(groupStudent);
                entityManager.getTransaction().commit();
                entityManager.close();
            }
            catch (RollbackException e){
                throw new ParentKeyException(GroupProfessor.class.getName());
            }
        }
        else throw  new DuplicatedObjectException(GroupProfessor.class.getName());
    }

    public static GroupStudent findGroupStudent(GroupStudentPK pk, EntityManagerFactory session){
        EntityManager entityManager= session.createEntityManager();
        entityManager.getTransaction().begin();
        GroupStudent foundGroupStudent=entityManager.find(GroupStudent.class,pk);
        entityManager.getTransaction().commit();
        return foundGroupStudent;
    }

    public static List<Student> findAllStudents(Group group, EntityManagerFactory session){
        EntityManager entityManager= session.createEntityManager();
        entityManager.getTransaction().begin();
        Query q=entityManager.createNamedQuery("GroupStudent.findByGroup").setParameter("gr",group.getName());
        entityManager.getTransaction().commit();
        List<Student> foundStudents=q.getResultList();
        entityManager.close();
        return foundStudents;
    }
}
