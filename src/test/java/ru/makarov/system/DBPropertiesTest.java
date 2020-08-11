package ru.makarov.system;

import org.junit.Test;

import static org.junit.Assert.*;

public class DBPropertiesTest {
    @Test
    public void whenShouldCheckProperties() {
//        DBProperties dbProperties = new DBProperties("src/main/resources/app.properties");
        DBProperties dbProperties = new DBProperties();
        assertEquals(dbProperties.getUrl(), "jdbc:postgresql://localhost:5432");
        assertEquals(dbProperties.getNameTable(), "mytask");
    }
}