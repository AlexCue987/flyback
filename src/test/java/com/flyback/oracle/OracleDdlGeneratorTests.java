package com.flyback.oracle;

import org.junit.Ignore;
import org.junit.Test;

public class OracleDdlGeneratorTests {
    @Ignore
    @Test
    public void generates(){
        String owner = "****";
        String pwd = "****";
        String connectionString = "****";
        OracleDdlGenerator.generate(owner,
                pwd,
                connectionString,
                "src/test/resources");
    }
}
