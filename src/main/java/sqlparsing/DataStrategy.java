package sqlparsing;
import java.util.Date;
/**
 * Interface for DATA Base work. Three important methods.
 */
public interface DataStrategy {
    /**
     * @return String value of Max Date from database.
     */
    String selectMax();
    /**
     * Method for add Items to database.
     * @param item - Item which we want to add.
     */
    void addTodata(Item item);
    /**
     * This method for convert from String to util.Date.
     * @param s -String of date from database.
     * @return - value of util.Data.
     */
    Date convertToData(String s);
    //todo perhaprs this method should be in other place. Think about this.
}
