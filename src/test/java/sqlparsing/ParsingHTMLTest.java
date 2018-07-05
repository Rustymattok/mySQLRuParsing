package sqlparsing;
import org.junit.Test;

/**
 * Class for checking full application.
 */
public class ParsingHTMLTest {
    public DBPropereties dbPropereties = new DBPropereties("/home/rustymattok/MyGit/lesson7/src/main/java/sqlparsing/app.properties");
    @Test
    public void whenShouldCheckParser() {
        dbPropereties.initData();
        DataBase dataBase = new DataBase(new WorkBase(dbPropereties.getUrl(), dbPropereties.getNameTable(), dbPropereties.getUsername(), dbPropereties.getPassword()));
        ParsingHTML parsingHTML = new ParsingHTML(dbPropereties.getWeblink(), dataBase,dbPropereties.getFileWay());
        parsingHTML.startParsing();
    }
}