import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Manager {
    private static EntityManagerFactory manager;

    private Manager(){
    }

    public static  EntityManagerFactory getInstance(){
        if(manager == null){
            manager= Persistence.createEntityManagerFactory("alo");
        }
        return manager;
    }
}