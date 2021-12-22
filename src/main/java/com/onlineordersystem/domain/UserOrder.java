package com.onlineordersystem.domain;

import com.onlineordersystem.domain.base.Auditable;
import java.util.Objects;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

@NoArgsConstructor
@Getter
@Setter
@Table(name = "user_orders")
@Entity
public class UserOrder extends Auditable {

    @Id
    @GenericGenerator(name = "sequential_uuid", strategy = "com.onlineordersystem.domain.util.SequentialUUIDGenerator")
    @GeneratedValue(generator = "sequential_uuid")
    @Column(name = "id", nullable = false, columnDefinition = "binary(16)")
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserOrder order)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        return quantity == order.quantity && product.equals(order.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), product, quantity);
    }
}