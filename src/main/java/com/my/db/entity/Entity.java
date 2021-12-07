package com.my.db.entity;

import java.io.Serializable;
import java.util.Objects;

public abstract class Entity implements Serializable, Comparable<Entity> {
    protected long id;

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int compareTo(Entity o) {
        Long thisId = this.getId();
        Long otherId = o.getId();
        return thisId.compareTo(otherId);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o != null && this.getClass() == o.getClass()) {
            Entity entity = (Entity)o;
            return this.getId() == entity.getId();
        } else {
            return false;
        }
    }

    public int hashCode() {
        return Objects.hash(this.getId());
    }
}

