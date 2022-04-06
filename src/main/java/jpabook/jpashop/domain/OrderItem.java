package jpabook.jpashop.domain;
import javax.persistence.*;

@Entity
public class OrderItem extends BaseEntity{

    @Id
    @GeneratedValue
    @Column(name = "ORDRE_ITEM")
    private Long id;

    @ManyToOne
    @JoinColumn(name="ORDER_ID")
    private Order order;

    @ManyToOne
    @JoinColumn(name="ITEM_ID")
    private item item;

    private int oderPrice;
    private int count;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public jpabook.jpashop.domain.item getItem() {
        return item;
    }

    public void setItem(jpabook.jpashop.domain.item item) {
        this.item = item;
    }

    public int getOderPrice() {
        return oderPrice;
    }

    public void setOderPrice(int oderPrice) {
        this.oderPrice = oderPrice;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
