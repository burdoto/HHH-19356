package de.kaleidox.hhh19356;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.UniqueConstraint;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
public class MyEntity {
    @ElementCollection @CollectionTable(uniqueConstraints = @UniqueConstraint(columnNames = { "option", "number" }))
    private final List<MyDetail> details = new ArrayList<>();
    private @Id   UUID           id;

    public MyEntity() {
        this(UUID.randomUUID());
    }

    public MyEntity(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    public List<MyDetail> getDetails() {
        return details;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        MyEntity myEntity = (MyEntity) o;
        return Objects.equals(getId(), myEntity.getId());
    }
}
