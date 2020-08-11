package ru.makarov.system;

import lombok.Data;

import java.io.*;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * Class for work with application porperties.
 */
@Data
public class DBProperties implements AutoCloseable {
    private String url;
    private String nameTable;
    private String username;
    private String password;
    private String lastDate;
    private String weblink;
    private String time;
    private String path;
    private FileInputStream fis;
    private static Logger logger = Logger.getLogger("InfoLogging");

    public DBProperties() {
        System.out.println("im here 1");
        Properties property = new Properties();
        try {
            InputStream in = getClass().getResourceAsStream("/app.properties");
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            property.load(reader);
            this.url = property.getProperty("jdbc.url");
            this.nameTable = property.getProperty("jdbc.nameTable");
            this.username = property.getProperty("jdbc.username");
            this.password = property.getProperty("jdbc.password");
            this.lastDate = property.getProperty("jdbc.lastDate");
            this.weblink = property.getProperty("jdbc.weblink");
            this.time = property.getProperty("jdbc.time");
        } catch (IOException e) {
            logger.info("FAIL: File of properties not found!");
        }
    }

    @Override
    public void close() throws Exception {
        fis.close();
    }
}

