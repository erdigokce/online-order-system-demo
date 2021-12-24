package com.onlineordersystem.domain.base;

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

}