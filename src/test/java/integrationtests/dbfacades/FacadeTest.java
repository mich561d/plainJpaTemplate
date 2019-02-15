package integrationtests.dbfacades;

import entity.MyEntity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class FacadeTest {
  
EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu-test", null);

  @BeforeClass
  public static void setUpClass() {
  }

  /** Setup the database in a known state before Each test */
  @Before
  public void setUp() {
    EntityManager em = emf.createEntityManager();
    try {
      em.getTransaction().begin();
      em.createQuery("delete from MyEntity").executeUpdate();
      MyEntity e1 = new MyEntity("Hello");
      MyEntity e2 = new MyEntity("World");
      em.persist(e1);
      em.persist(e2);
      em.getTransaction().commit();
    } finally {
      em.close();
    }
  }

  @Test
  public void countEntities() {
    EntityManager em = emf.createEntityManager();
    try {
      long count = (long)em.createQuery("Select Count(c) from MyEntity c").getSingleResult();
      Assert.assertEquals(2,count);
    } finally {
      em.close();
    }
  }
  
}