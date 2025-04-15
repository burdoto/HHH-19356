package de.kaleidox.hhh19356;

import org.hibernate.jpa.HibernatePersistenceProvider;

import javax.persistence.SharedCacheMode;
import javax.persistence.ValidationMode;
import javax.persistence.spi.ClassTransformer;
import javax.persistence.spi.PersistenceUnitInfo;
import javax.persistence.spi.PersistenceUnitTransactionType;
import javax.sql.DataSource;
import java.net.URL;
import java.util.List;
import java.util.Properties;

public class MyPersistenceUnit implements PersistenceUnitInfo {
    DataSource dataSource;

    public MyPersistenceUnit(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public String getPersistenceUnitName() {
        return "test";
    }

    @Override
    public String getPersistenceProviderClassName() {
        return HibernatePersistenceProvider.class.getCanonicalName();
    }

    @Override
    public PersistenceUnitTransactionType getTransactionType() {
        return PersistenceUnitTransactionType.RESOURCE_LOCAL;
    }

    @Override
    public DataSource getJtaDataSource() {
        return dataSource;
    }

    @Override
    public DataSource getNonJtaDataSource() {
        return dataSource;
    }

    @Override
    public List<String> getMappingFileNames() {
        return List.of();
    }

    @Override
    public List<URL> getJarFileUrls() {
        return List.of();
    }

    @Override
    public URL getPersistenceUnitRootUrl() {
        return Main.class.getProtectionDomain().getCodeSource().getLocation();
    }

    @Override
    public List<String> getManagedClassNames() {
        return List.of(MyEntity.class.getCanonicalName());
    }

    @Override
    public boolean excludeUnlistedClasses() {
        return true;
    }

    @Override
    public SharedCacheMode getSharedCacheMode() {
        return SharedCacheMode.ALL;
    }

    @Override
    public ValidationMode getValidationMode() {
        return ValidationMode.AUTO;
    }

    @Override
    public Properties getProperties() {
        return new Properties();
    }

    @Override
    public String getPersistenceXMLSchemaVersion() {
        return "1";
    }

    @Override
    public ClassLoader getClassLoader() {
        return Main.class.getClassLoader();
    }

    @Override
    public void addTransformer(ClassTransformer transformer) {
        // wtf?
    }

    @Override
    public ClassLoader getNewTempClassLoader() {
        return getClassLoader();
    }
}
