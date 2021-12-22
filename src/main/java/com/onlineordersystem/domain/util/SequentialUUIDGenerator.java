package com.onlineordersystem.domain.util;

import java.io.Serializable;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

public class SequentialUUIDGenerator implements IdentifierGenerator {

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object o) {
        Serializable id = session
            .getEntityPersister(null, o)
            .getClassMetadata()
            .getIdentifier(o, session);
        return id != null ? id : SequentialUUID.generate();
    }
}
