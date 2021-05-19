package Repository;

import Entity.Grade;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class GroupProfessor {
    public GroupProfessor(){
    }

    public static void createGrade(GroupProfessor groupProfessor, EntityManagerFactory session){
        EntityManager entityManager= session.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(groupProfessor);
        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
