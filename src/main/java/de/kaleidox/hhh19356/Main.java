package de.kaleidox.hhh19356;

import com.zaxxer.hikari.HikariDataSource;
import org.hibernate.dialect.MariaDBDialect;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.mariadb.jdbc.Driver;

import javax.persistence.EntityManager;
import javax.persistence.spi.PersistenceProvider;
import java.time.Instant;
import java.util.Map;
import java.util.UUID;

public class Main {
    static final PersistenceProvider SPI = new HibernatePersistenceProvider();
    static final UUID                id  = UUID.randomUUID();

    public static void main(String[] args) {
        EntityManager manager;

        try (
                var dataSource = new HikariDataSource() {{
                    setDriverClassName(Driver.class.getCanonicalName());
                    setJdbcUrl("jdbc:mariadb://localhost:3306/dev?useUnicode=true&amp;character_set_server=utf8mb4");
                    setUsername("dev");
                    setPassword("dev");
                }}
        ) {
            var factory = SPI.createContainerEntityManagerFactory(new MyPersistenceUnit(dataSource),
                    Map.of("hibernate.connection.driver_class",
                            Driver.class.getCanonicalName(),
                            "hibernate.connection.url",
                            "jdbc:mariadb://localhost:3306/dev?useUnicode=true&amp;character_set_server=utf8mb4",
                            "hibernate.connection.username",
                            "dev",
                            "hibernate.connection.password",
                            "dev",
                            "hibernate.connection.useUnicode",
                            true,
                            "hibernate.dialect",
                            MariaDBDialect.class.getCanonicalName(),
                            "hibernate.show_sql",
                            true,
                            "hibernate.type",
                            "trace",
                            "hibernate.hbm2ddl.auto",
                            "update"));

            manager = factory.createEntityManager();

            var entity = new MyEntity(id);
            manager.getTransaction().begin();
            manager.persist(entity);
            manager.getTransaction().commit();

            findAndPrint("First iteration: no detail", manager);

            var primaryDetail = new MyDetail() {{
                setTime(Instant.now());
                setSomeText("my first detail");
                setSomeNumber(1);
            }};
            entity.detail.add(primaryDetail);

            findAndPrint("Second iteration: pre persist() call", manager);

            manager.getTransaction().begin();
            manager.persist(entity);
            manager.getTransaction().commit();

            findAndPrint("Third iteration: should have one detail", manager);

            primaryDetail.setSomeText("my first updated detail");

            manager.getTransaction().begin();
            manager.persist(entity);
            manager.getTransaction().commit();

            findAndPrint("Fourth iteration: detail should be updated", manager);

            entity.detail.add(new MyDetail() {{
                setTime(Instant.now());
                setSomeText("my second detail");
                setSomeNumber(2);
            }});

            findAndPrint("Fifth iteration: pre persist() call", manager);

            manager.getTransaction().begin();
            manager.persist(entity);
            manager.getTransaction().commit();

            findAndPrint("Sixth iteration: should have two details", manager);

            manager.close();
        }
    }

    private static void findAndPrint(String desc, EntityManager manager) {
        System.out.println(desc);

        var entity = manager.find(MyEntity.class, id);

        System.out.println("entity.id = " + entity.id);
        for (var i = 0; i < entity.detail.size(); i++)
            printDetail(i, entity.detail.get(i));
    }

    private static void printDetail(int i, MyDetail detail) {
        System.out.println("entity.details[" + i + "].time = " + detail.time);
        System.out.println("entity.details[" + i + "].someText = " + detail.someText);
        System.out.println("entity.details[" + i + "].someNumber = " + detail.someNumber);
    }
}