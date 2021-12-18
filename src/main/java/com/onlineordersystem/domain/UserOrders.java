package com.onlineordersystem.domain;

import com.onlineordersystem.domain.base.Auditable;
import java.util.Objects;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Table(name = "user_orders")
@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
public class UserOrders extends Auditable<String, UUID> {

    @ManyToOne(optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private Products product;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "user_email", nullable = false)
    private String userEmail;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserOrders order)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        return quantity == order.quantity && product.equals(order.product) && userEmail.equals(order.userEmail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), product, quantity, userEmail);
    }
}