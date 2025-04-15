package de.kaleidox.hhh19356;

import org.hibernate.annotations.Type;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.UniqueConstraint;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
public class MyEntity {
    @ElementCollection @Convert(converter = MyConverter.class)
    @CollectionTable(name = "my_details",
                     joinColumns = @JoinColumn(name = "id"),
                     uniqueConstraints = { @UniqueConstraint(columnNames = { "id", "option", "number" }) })
    private final List<MyDetail> details = new ArrayList<>();

    @Id @Convert(converter = UuidVarchar36Converter.class) @Type(type = "uuid-char")
    @Column(columnDefinition = "varchar(36)", updatable = false, nullable = false) private UUID id;

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
