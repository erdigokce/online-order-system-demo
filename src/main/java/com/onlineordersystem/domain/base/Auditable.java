package com.onlineordersystem.domain.base;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.onlineordersystem.security.util.SessionUtil;
import java.time.LocalDateTime;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public abstract class Auditable {

    @Column(name = "created_by", nullable = false, updatable = false)
    private String createdBy;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @Column(name = "created_date", nullable = false, updatable = false)
    private LocalDateTime createdDate;

    @Column(name = "last_modified_by", updatable = false)
    private String lastModifiedBy;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @Column(name = "last_modified_date", updatable = false)
    private LocalDateTime lastModifiedDate;

    @PrePersist
    public void prePersist() {
        setCreatedBy(SessionUtil.getUsername());
        setCreatedDate(LocalDateTime.now());
    }

    @PreUpdate
    public void preUpdate() {
        setLastModifiedBy(SessionUtil.getUsername());
        setLastModifiedDate(LocalDateTime.now());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Auditable auditable)) {
            return false;
        }
        return Objects.equals(createdBy, auditable.createdBy) && Objects.equals(createdDate, auditable.createdDate) && lastModifiedBy.equals(
            auditable.lastModifiedBy) && lastModifiedDate.equals(auditable.lastModifiedDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(createdBy, createdDate, lastModifiedBy, lastModifiedDate);
    }
}