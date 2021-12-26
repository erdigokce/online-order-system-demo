package com.onlineordersystem.domain.base;

import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public abstract class PrincipleRelated extends Auditable {

    @OneToOne(optional = false, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "principle_email", nullable = false)
    private Principle principle;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PrincipleRelated that)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        return Objects.equals(principle, that.principle);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), principle);
    }
}