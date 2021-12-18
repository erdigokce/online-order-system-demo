package com.onlineordersystem.domain;

import com.onlineordersystem.domain.base.Auditable;
import java.util.Objects;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Lob;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Table(name = "sellers")
@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Sellers extends Auditable<String, UUID> {

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false, length = 16)
    private String password;

    @Column(name = "business_name", nullable = false, unique = true)
    private String businessName;

    @Lob
    @Column(name = "business_address", nullable = false)
    private String businessAddress;

    @Column(name = "email_confirmed", nullable = false)
    private boolean emailConfirmed;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Sellers register)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        return emailConfirmed == register.emailConfirmed && email.equals(register.email) && password.equals(register.password) && businessName.equals(register.businessName)
            && businessAddress.equals(register.businessAddress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), email, password, businessName, businessAddress, emailConfirmed);
    }
}