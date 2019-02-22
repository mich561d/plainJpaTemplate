package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class OrderOne implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @JoinColumn()
    @ManyToOne
    private Customer customer;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "orderOne")
    private Collection<OrderLine> orderLines = new ArrayList();

    public OrderOne() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "OrderOne{" + "id=" + id + '}';
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Collection<OrderLine> getOrderLines() {
        return orderLines;
    }

    public void setOrderLines(Collection<OrderLine> orderLines) {
        this.orderLines = orderLines;
    }

}
