package dbfacades;

import entity.MyEntity;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Plaul
 */
public class DemoFacade {

  EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu-memory");

  DemoFacade(EntityManagerFactory emf) {
    this.emf = emf;
  }

  public DemoFacade() {
    EntityManager em = emf.createEntityManager();
    try {
      MyEntity e1 = new MyEntity("Hello");
      MyEntity e2 = new MyEntity("World");
      em.getTransaction().begin();
      em.createQuery("delete from MyEntity e").executeUpdate();
      em.persist(e1);
      em.persist(e2);
      em.getTransaction().commit();
    } finally {
      em.close();
    }
  }

  public List<MyEntity> getAllMyEntities() {
    EntityManager em = emf.createEntityManager();
    try {
      return (List<MyEntity>) em.createQuery("select m from MyEntity m").getResultList();
    } finally {
      em.close();
    }

  }

  public static void main(String[] args) {
    DemoFacade df = new DemoFacade();
    List<MyEntity> entities = df.getAllMyEntities();
    System.out.println(entities.size());
  }

}
