package com.onlineordersystem.domain;

import com.onlineordersystem.domain.base.Auditable;
import com.onlineordersystem.domain.base.Principle;
import java.util.Objects;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

@NoArgsConstructor
@Getter
@Setter
@Table(name = "sellers")
@Entity
public class Seller extends Auditable {

    @Id
    @GenericGenerator(name = "sequential_uuid", strategy = "com.onlineordersystem.domain.util.SequentialUUIDGenerator")
    @GeneratedValue(generator = "sequential_uuid")
    @Column(name = "id", nullable = false, columnDefinition = "binary(16)")
    private UUID id;

    @Column(name = "business_name", nullable = false, unique = true)
    private String businessName;

    @Lob
    @Column(name = "business_address", nullable = false)
    private String businessAddress;

    @OneToOne(optional = false, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "principle_email", nullable = false)
    private Principle principle;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Seller seller)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        return businessName.equals(seller.businessName) && businessAddress.equals(seller.businessAddress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), businessName, businessAddress);
    }
}