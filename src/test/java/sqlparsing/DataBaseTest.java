package sqlparsing;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Class for Test method of DataBase.
 */
public class DataBaseTest {
    public DBPropereties dbPropereties = new DBPropereties("/home/rustymattok/MyGit/lesson7/src/main/java/sqlparsing/files/app.properties");
    private DataBase dataBase;

    public DataBaseTest() {
        dbPropereties.initData();
        dataBase = new DataBase(new WorkBase(dbPropereties.getUrl(), dbPropereties.getNameTable(), dbPropereties.getUsername(), dbPropereties.getPassword()));
    }
    /**
     * Check method for choose max date.
     */
    @Test
    public void whenShouldCheckselectMax() {
        assertThat(dataBase.selectMax(), is(dbPropereties.getLastDate()));
    }
    /**
     * Method to check convertation .
     */
    @Test
    public void whenShouldCheckconvertToDate() {
        assertThat(dataBase.convertToData("0015-01-01 00:00:00").toString(), is("Tue Jan 01 00:00:00 MSK 15"));
    }
}