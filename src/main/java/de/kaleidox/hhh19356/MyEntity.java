package de.kaleidox.hhh19356;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.UniqueConstraint;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
public class MyEntity {
    @Id UUID id;
    @ElementCollection @CollectionTable(uniqueConstraints = @UniqueConstraint(columnNames = { "time", "someNumber" }))
    List<MyDetail> detail = new ArrayList<>();

    public MyEntity() {
        this(UUID.randomUUID());
    }

    public MyEntity(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    public List<MyDetail> getDetail() {
        return detail;
    }
}
