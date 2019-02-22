package entity;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class OrderLine implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private int quantity;
    @JoinColumn()
    @ManyToOne(optional = false)
    private OrderOne orderOne;
    @JoinColumn()
    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    private ItemType itemType;

    public OrderLine() {
    }

    public OrderLine(int quantity) {
        this.quantity = quantity;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "OrderLine{" + "id=" + id + ", quantity=" + quantity + '}';
    }

    public OrderOne getOrderOne() {
        return orderOne;
    }

    public void setOrderOne(OrderOne orderOne) {
        this.orderOne = orderOne;
    }

    public ItemType getItemType() {
        return itemType;
    }

    public void setItemType(ItemType itemType) {
        this.itemType = itemType;
    }

}
