package de.mrjulsen.mcdragonlib.common;

import java.util.UUID;

public interface IIdentifiable {
    UUID getId();
    
    default UUID generateId() {
        return UUID.randomUUID();
    }
}
