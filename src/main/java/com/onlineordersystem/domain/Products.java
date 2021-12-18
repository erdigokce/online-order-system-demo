package com.onlineordersystem.domain;

import com.onlineordersystem.domain.base.Auditable;
import java.util.Objects;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Table(name = "products")
@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Products extends Auditable<String, UUID> {

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "description", length = 512)
    private String description;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Products product)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        return quantity == product.quantity && name.equals(product.name) && Objects.equals(description, product.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, description, quantity);
    }
}