package de.kaleidox.hhh19356;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
public class MyEntity {
    @Id                UUID           id;
    @ElementCollection List<MyDetail> detail = new ArrayList<>();

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
