package sqlparsing;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Logger;
import java.util.Date;
/**
 * Class allow to connect to dataBase. and make some injections and operations.
 */
public class DataBase implements DataStrategy {
    private Connection con;
    private WorkBase workBase;
    private String fullWayToData;
    private static Logger LOGGER = Logger.getLogger("InfoLogging");

    public DataBase(WorkBase workBase) {
        this.workBase = workBase;
        this.fullWayToData = new StringBuilder().append(workBase.getUrl()).append("/").append(workBase.getNameData()).toString();
        connectToDataBase();
    }
    /**
     * Method for connection to dataBase.
     */
    public void connectToDataBase(){
        try {
            con = DriverManager.getConnection(fullWayToData, workBase.getUser(), workBase.getPassword());
            LOGGER.info("server found :");
        } catch (SQLException e) {
            LOGGER.info("connect to DATA :");
            creatNewDataBase();
        }
    }
    /**
     * Method for create new DataBase if it's absent.
     */
    public void creatNewDataBase(){
        if(con == null) {
            try {
                String hrappSQL = new StringBuilder().append("CREATE DATABASE").append(" ").append(workBase.getNameTable()).append(";").toString();
                con = DriverManager.getConnection(fullWayToData, workBase.getUser(), workBase.getPassword());
                PreparedStatement st = con.prepareStatement(hrappSQL);
                st.execute();
            }
            catch (SQLException e) {
                LOGGER.info("create new  DATA :");
                e.printStackTrace();
            }
        }
    }
    /**
     * Method for close all operations.
     */
    public void closeData(){
        Statement st = null;
        try {
            if (con!=null) {
                con.close();
            }
            if (st != null){
                st.close();
            }
        } catch (SQLException e) {
            LOGGER.info("close DATA :");
            e.printStackTrace();
        }
    }
    /**
     * Method to choose max date.
     * @return String-  found by postgresql.
     */
    @Override
    public  String selectMax(){
        String date="";
        String selectTableSQL = "SELECT max(date) FROM mytask";
        try{
            con = DriverManager.getConnection(fullWayToData, workBase.getUser(), workBase.getPassword());
            DatabaseMetaData dbm = con.getMetaData();
            PreparedStatement st = con.prepareStatement(selectTableSQL);
            ResultSet rs = dbm.getTables(null,null,"mytask",null);
            if(rs.next()) {
                rs = st.executeQuery();
                while (rs.next()) {
                    date = rs.getString(1);
                }
            }else{
                date = "0000-00-00 00:00:00";
            }
        }catch (SQLException e) {
            LOGGER.info("not work of method selectMax");
            e.printStackTrace();
        }
        return date;
    }
    /**
     * Add to data base new item.
     * @param item- element to add.
     */
    @Override
    public void addTodata(Item item) {
        String taksCreateTable = new StringBuilder().append("CREATE TABLE IF NOT EXISTS").append(" ")
                .append(workBase.getNameTable()).append("(id text,name text,description text,date timestamp);").toString();
        String taskInsertIntoTable = new StringBuilder().append("INSERT INTO ").append(workBase.getNameTable())
                .append(" VALUES (?,?,?,?)").toString();
        try {
            PreparedStatement st = con.prepareStatement(taksCreateTable);
            st.executeUpdate();
            st = con.prepareStatement(taskInsertIntoTable);
            st.setString(1,item.getID());
            st.setString(2,item.getName());
            st.setString(3,item.getDescription());
            st.setObject(4,new Timestamp(item.getDate().getTime()));
            st.executeUpdate();
        } catch (SQLException e) {
            LOGGER.info("method: addToData has mistake");
            e.printStackTrace();
        }
    }
    /**
     * @param date - String for convert date in string to util.Date.
     * @return util.Date
     */
    @Override
    public Date convertToData(String date){
        Date result = null;
        SimpleDateFormat simpleDateFormatIn = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            result = simpleDateFormatIn.parse(date);
        } catch (ParseException e) {
            LOGGER.info("not correct during convertation String to Date :");
        }
        return result;
    }
}