package sqlparsing;
import org.junit.Test;

/**
 * Class to test XMLparser. Not actual for current application.
 */
public class ParserDataXMLTest {

    @Test
    public void whenShouldCheckParser() {
        try(ParserDataXML parserDataXML = new ParserDataXML("/home/rustymattok/MyGit/lesson7/src/main/java/sqlparsing/files/data.xml")) {
            parserDataXML.parser();
            parserDataXML.addToDataLastDate("0015-01-01 00:00:00");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}