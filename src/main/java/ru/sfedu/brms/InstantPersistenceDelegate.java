package ru.sfedu.brms;

import java.beans.Encoder;
import java.beans.Expression;
import java.beans.PersistenceDelegate;
import java.time.Instant;
import java.util.HashSet;

public class InstantPersistenceDelegate extends PersistenceDelegate {
    private final HashSet<Instant> hashesWritten = new HashSet<>();

    public Expression instantiate(Object oldInstance, Encoder out) {
        Instant time = (Instant) oldInstance;
        hashesWritten.add(time);
        return new Expression(oldInstance, Instant.class, "parse", new Object[]{time.toString()});
    }

    protected boolean mutatesTo(Object oldInstance, Object newInstance) {
        return hashesWritten.contains(oldInstance);
    }
}
