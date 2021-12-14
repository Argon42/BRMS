package ru.sfedu.brms;

import java.beans.Encoder;
import java.beans.Expression;
import java.beans.PersistenceDelegate;
import java.util.HashSet;
import java.util.UUID;

public class UUIDPersistenceDelegate extends PersistenceDelegate {
    private final HashSet<UUID> hashesWritten = new HashSet<UUID>();

    public Expression instantiate(Object oldInstance, Encoder out) {
        UUID id = (UUID) oldInstance;
        hashesWritten.add(id);
        return new Expression(oldInstance, UUID.class, "fromString", new Object[]{id.toString()});
    }

    protected boolean mutatesTo(Object oldInstance, Object newInstance) {
        return hashesWritten.contains(oldInstance);
    }
}