package com.flyback.oracle;

import oracle.jdbc.pool.OracleDataSource;
import org.flywaydb.core.Flyway;
import org.junit.Ignore;
import org.junit.Test;

import javax.sql.DataSource;
import java.sql.SQLException;

public class ExampleOfUsage {
    @Ignore
    @Test
    public void generates() throws Exception {
        String owner = "***";
        String pwd = "***";
        String connectionString = "***";
        recreateSchema(owner,
                pwd,
                connectionString);
        OracleDdlGenerator.generate(owner,
                pwd,
                connectionString,
                "src/test/resources");
    }

    private DataSource getDataSource(String owner, String pwd, String connectionString) throws SQLException {
        OracleDataSource dataSource = new OracleDataSource();
        dataSource.setUser(owner);
        dataSource.setPassword(pwd);
        dataSource.setURL(connectionString);
        return dataSource;
    }

    private void recreateSchema(String owner, String pwd, String connectionString) throws Exception {
        DataSource dataSource = getDataSource(owner, pwd, connectionString);
        Flyway flyway = getFlyway(dataSource);
        flyway.clean();
        flyway.migrate();
    }

    private Flyway getFlyway(DataSource dataSource) {
        Flyway flyway = new Flyway();
        flyway.setDataSource(dataSource);
        flyway.setLocations("classpath:/migrations");
        flyway.setValidateOnMigrate(true);
        return flyway;
    }
}
