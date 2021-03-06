package ru.makarov.system;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SQLParseTest {

    @Test
    public void whenShouldCheckParsText() {
        SQLParse sqlParse = new SQLParse();
        assertEquals("25 июл 20, 00:56    ", sqlParse.parText("25 июл 20, 00:56    [22173477]     Ответить | Цитировать Сообщить модератору"));
    }

    @Test
    public void whenShouldCheckParseTitle() {
        SQLParse sqlParse = new SQLParse();
        assertTrue(sqlParse.parserTitle("Услуги по разработке React + Java "));
    }

}