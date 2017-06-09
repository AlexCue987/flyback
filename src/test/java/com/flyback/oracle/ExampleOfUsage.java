package com.flyback.oracle;

import org.junit.Ignore;
import org.junit.Test;

public class ExampleOfUsage {
    @Ignore
    @Test
    public void generates(){
        String owner = "****";
        String pwd = "****";
        String connectionString = "****";
        /*
        * before running, make sure to create both functions in
        * test/resources/prerequisites
        *
        * the baseline table definitions are in resources/migrations
        *
        * the sample output is in src/test/resources/tables
        * */
        OracleDdlGenerator.generate(owner,
                pwd,
                connectionString,
                "src/test/resources");
    }
}
