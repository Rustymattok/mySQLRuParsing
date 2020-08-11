package ru.makarov;

import org.quartz.SchedulerException;
import ru.makarov.dao.JDBCData;
import ru.makarov.interfaces.Store;
import ru.makarov.system.SQLParse;

public class ParsingApplication {

    public static void main(String[] args) {
        Grabber grabber = new Grabber();
        try {
            Store store = new JDBCData();
            grabber.init(new SQLParse(grabber.lastDate(store)), store, grabber.scheduler());
            grabber.web(store);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

}
