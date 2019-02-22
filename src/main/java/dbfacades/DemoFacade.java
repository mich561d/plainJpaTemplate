package dbfacades;

import entity.Customer;
import entity.ItemType;
import entity.OrderLine;
import entity.OrderOne;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class DemoFacade {

    EntityManagerFactory emf;

    public DemoFacade(EntityManagerFactory emf) {
        this.emf = emf;
    }

    void createCustomer(String name, String email) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Customer customer = new Customer(name, email);
            em.persist(customer);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    Customer getCustomerByID(int id) {
        EntityManager em = emf.createEntityManager();
        try {
            return (Customer) em.createQuery("select m from Customer m where m.id = :id").setParameter("id", id).getSingleResult();
        } finally {
            em.close();
        }
    }

    List<Customer> getAllCustomers() {
        EntityManager em = emf.createEntityManager();
        try {
            return (List<Customer>) em.createQuery("select m from Customer m").getResultList();
        } finally {
            em.close();
        }
    }

    void createOrder(OrderOne order) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(order);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    List<OrderOne> getAllOrders() {
        EntityManager em = emf.createEntityManager();
        try {
            return (List<OrderOne>) em.createQuery("select m from OrderOne m").getResultList();
        } finally {
            em.close();
        }
    }

    OrderOne getOrderById(Integer id) {
        EntityManager em = emf.createEntityManager();
        try {
            return (OrderOne) em.createQuery("select m from OrderOne m where m.id = :id").setParameter("id", id).getSingleResult();
        } finally {
            em.close();
        }
    }

    List<OrderOne> getAllOrdersByCustomerId(Integer id) {
        EntityManager em = emf.createEntityManager();
        try {
            return (List<OrderOne>) em.createQuery("select m from OrderOne m where m.customer.id = :id").setParameter("id", id).getResultList();
        } finally {
            em.close();
        }
    }

    void createOrderLine(OrderLine orderLine) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(orderLine);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    List<OrderLine> getOrderLinesByOrderId(Integer id) {
        EntityManager em = emf.createEntityManager();
        try {
            return (List<OrderLine>) em.createQuery("select m from OrderLine m where m.orderOne.id  = :id").setParameter("id", id).getResultList();
        } finally {
            em.close();
        }
    }

    List<OrderLine> getAllOrderLines() {
        EntityManager em = emf.createEntityManager();
        try {
            return (List<OrderLine>) em.createQuery("select m from OrderLine m").getResultList();
        } finally {
            em.close();
        }
    }

    void createItemType(ItemType itemType) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(itemType);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    List<ItemType> getAllItemType() {
        EntityManager em = emf.createEntityManager();
        try {
            return (List<ItemType>) em.createQuery("select m from ItemType m").getResultList();
        } finally {
            em.close();
        }
    }

}
