package de.kaleidox.hhh19356;

import com.zaxxer.hikari.HikariDataSource;
import org.hibernate.dialect.MariaDBDialect;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.mariadb.jdbc.Driver;

import javax.persistence.EntityManager;
import javax.persistence.spi.PersistenceProvider;
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
                setOption(MyOption.OPTION_A);
                setNumber(1);
                setValue("my first detail");
            }};
            entity.getDetails().add(primaryDetail);

            findAndPrint("Second iteration: pre persist() call", manager);

            manager.getTransaction().begin();
            manager.persist(entity);
            manager.getTransaction().commit();

            findAndPrint("Third iteration: should have one detail", manager);

            var details = entity.getDetails();
            details.remove(primaryDetail);
            details.add(primaryDetail.withValue("my first updated detail"));

            manager.getTransaction().begin();
            manager.persist(entity);
            manager.getTransaction().commit();

            findAndPrint("Fourth iteration: detail should be updated", manager);

            entity.getDetails().add(new MyDetail() {{
                setOption(MyOption.OPTION_B);
                setNumber(2);
                setValue("my second detail");
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

        System.out.println("entity.id = " + entity.getId());
        for (var i = 0; i < entity.getDetails().size(); i++)
            printDetail(i, entity.getDetails().get(i));
    }

    private static void printDetail(int i, MyDetail detail) {
        System.out.println("entity.details[" + i + "].option = " + detail.getOption().getName());
        System.out.println("entity.details[" + i + "].number = " + detail.getNumber());
        System.out.println("entity.details[" + i + "].value = " + detail.getValue());
    }
}