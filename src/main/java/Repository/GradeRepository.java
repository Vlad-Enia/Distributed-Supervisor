package Repository;

import Entity.Grade;
import Entity.GradePK;
import Entity.Group;
import Entity.GroupProfessor;
import Exceptions.DuplicatedObjectException;
import Exceptions.ParentKeyException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.RollbackException;
import java.util.List;

public class GradeRepository {
    public GradeRepository(){
    }

    public static void createGrade(Grade grade, EntityManagerFactory session){
        var found=GradeRepository.findGrade(new GradePK(grade.getTask(),grade.getStudent()),session);
        if(found==null){
            try{
                EntityManager entityManager= session.createEntityManager();
                entityManager.getTransaction().begin();
                entityManager.persist(grade);
                entityManager.getTransaction().commit();
                entityManager.close();
            }
            catch (RollbackException e){
                throw new ParentKeyException(Grade.class.getName());
            }
        }
        else throw  new DuplicatedObjectException(Grade.class.getName());
    }

    public static Grade findGrade(GradePK pk,EntityManagerFactory session){
        EntityManager entityManager=session.createEntityManager();
        entityManager.getTransaction().begin();
        Grade foundGrade=entityManager.find(Grade.class,pk);
        entityManager.getTransaction().commit();
        return foundGrade;
    }

    public static List<Grade> findAllGrades(Group group, EntityManagerFactory session){
        EntityManager entityManager=session.createEntityManager();
        entityManager.getTransaction().begin();
        Query q=entityManager.createNamedQuery("Grade.findByGroup").setParameter("gr",group.getName());
        entityManager.getTransaction().commit();
        List<Grade> foundGrades=q.getResultList();
        entityManager.close();
        return foundGrades;
    }

    public static void updateGrade(Grade grade,EntityManagerFactory session){
        var found=GradeRepository.findGrade(new GradePK(grade.getTask(),grade.getStudent()),session);
        if(found!=null){
            try{
                EntityManager entityManager=session.createEntityManager();
                entityManager.getTransaction().begin();
                Query q=entityManager.createNamedQuery("Grade.UpdateValue")
                        .setParameter("value",grade.getGrade())
                        .setParameter("student",grade.getStudent())
                        .setParameter("task",grade.getTask());
                var ans=q.executeUpdate();
                System.out.println(ans);
                entityManager.getTransaction().commit();
                entityManager.close();
            }catch (RollbackException e){
                throw new ParentKeyException(Grade.class.getName());
            }
        }else  throw new ParentKeyException(Grade.class.getName());
    }
}
