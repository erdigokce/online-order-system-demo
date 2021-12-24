package com.onlineordersystem.domain;

import com.onlineordersystem.domain.base.Auditable;
import java.util.Objects;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldNameConstants;
import org.hibernate.annotations.GenericGenerator;

@FieldNameConstants
@ToString
@NoArgsConstructor
@Getter
@Setter
@Table(name = "products")
@Entity
public class Product extends Auditable {

    @Id
    @GenericGenerator(name = "sequential_uuid", strategy = "com.onlineordersystem.domain.util.SequentialUUIDGenerator")
    @GeneratedValue(generator = "sequential_uuid")
    @Column(name = "id", nullable = false, columnDefinition = "binary(16)")
    private UUID id;

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
        if (!(o instanceof Product product)) {
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