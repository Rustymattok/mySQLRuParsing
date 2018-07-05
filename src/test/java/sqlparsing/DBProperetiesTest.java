package sqlparsing;
import org.junit.Test;

/**
 * Class to check how DB propeteries works.
 */
public class DBProperetiesTest {
    @Test
    public void whenShouldCheckPropereties() {
        DBPropereties dbPropereties = new DBPropereties("/home/rustymattok/MyGit/lesson7/src/main/java/sqlparsing/files/app.properties");
        dbPropereties.initData();
        System.out.println(dbPropereties.getUrl());
        System.out.println(dbPropereties.getUsername());
        System.out.println(dbPropereties.getPassword());
        System.out.println(dbPropereties.getNameTable());
        System.out.println(dbPropereties.getLastDate());
        dbPropereties.setNewDate("0000-06-28 15:53:00");
    }
}