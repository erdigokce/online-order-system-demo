package com.onlineordersystem.domain;

import com.onlineordersystem.domain.base.Auditable;
import com.onlineordersystem.domain.base.Principle;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

@NoArgsConstructor
@Getter
@Setter
@Table(name = "users")
@Entity
public class User extends Auditable {

    @Id
    @GenericGenerator(name = "sequential_uuid", strategy = "com.onlineordersystem.domain.util.SequentialUUIDGenerator")
    @GeneratedValue(generator = "sequential_uuid")
    @Column(name = "id", nullable = false, columnDefinition = "binary(16)")
    private UUID id;

    @Lob
    @Column(name = "address", nullable = false)
    private String address;

    @OneToOne(optional = false, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "principle_email", nullable = false)
    private Principle principle;

    @OrderBy("createdDate DESC")
    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private List<UserOrder> userOrders;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof User user)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        return Objects.equals(address, user.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), address);
    }

}