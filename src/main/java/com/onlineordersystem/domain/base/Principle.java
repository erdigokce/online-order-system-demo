package com.onlineordersystem.domain.base;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Table(name = "principles")
@Getter
@Setter
@Entity
public class Principle {

    @Id
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false, length = 16)
    private String password;

    @Column(name = "email_confirmed", nullable = false)
    private boolean emailConfirmed;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Principle principle)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        return emailConfirmed == principle.emailConfirmed && email.equals(principle.email) && password.equals(principle.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), email, password, emailConfirmed);
    }
}
