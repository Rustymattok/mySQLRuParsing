package ru.makarov.dao;

import ru.makarov.interfaces.Store;
import ru.makarov.model.Post;
import ru.makarov.system.DBProperties;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Class for work with dataBase JDBC.
 */
public class JDBCData implements Store, AutoCloseable {
    private String tableName;
    private Connection con;
    private static Logger logger = Logger.getLogger("InfoLogging");

    public JDBCData() {
        DBProperties db = new DBProperties();
        try {
            this.con = DriverManager.getConnection(db.getUrl(), db.getUsername(), db.getPassword());
            this.tableName = db.getNameTable();
            logger.info("Connection with DB sucsessful");
        } catch (SQLException e) {
            logger.info("Connection  was failed");
            e.printStackTrace();
        }
    }

    @Override
    public void add(Post item) {
        String taskInsertIntoTable = new StringBuilder().append("INSERT INTO ").append(tableName)
                .append("(name,description,date) VALUES (?,?,?)").toString();
        try {
            PreparedStatement st = con.prepareStatement(taskInsertIntoTable);
            st.setString(1, item.getName());
            st.setString(2, item.getDescription());
            st.setObject(3, new Timestamp(item.getDate().getTime()));
            st.executeUpdate();
            st.close();
        } catch (SQLException e) {
            logger.info("method: addToData has mistake");
            e.printStackTrace();
        }
    }

    @Override
    public void addList(List<Post> posts) {
        for (Post post : posts) {
            add(post);
        }
    }

    @Override
    public String selectMax() {
        String date = "0000-00-00 00:00:00";
        String selectTableSQL = "SELECT max(date) FROM mytask";
        try {
            DatabaseMetaData dbm = con.getMetaData();
            PreparedStatement st = con.prepareStatement(selectTableSQL);
            ResultSet rs = dbm.getTables(null, null, "mytask", null);
            if (rs.next()) {
                rs = st.executeQuery();
                while (rs.next()) {
                    date = rs.getString(1);
                }
            }
            st.close();
            rs.close();
        } catch (SQLException e) {
            logger.info("not work of method selectMax");
            e.printStackTrace();
        }
        return date;
    }

    @Override
    public Post selectByTag(String tag) {
        Post post = new Post();
        String selectTableSQL = "SELECT * FROM mytask where description = '" + tag + "'";
        try {
            PreparedStatement st = con.prepareStatement(selectTableSQL);
            ResultSet resultSet = st.executeQuery();
            while (resultSet.next()) {
                post.setId(resultSet.getInt(1));
                post.setName(resultSet.getString(2));
                post.setDescription(resultSet.getString(3));
                post.setDate(resultSet.getDate(4));
            }
            st.close();
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return post;
    }

    @Override
    public List<Post> allPost() {
        List<Post> list = new ArrayList<>();
        String selectTableSQL = "SELECT * FROM mytask";
        try {
            PreparedStatement st = con.prepareStatement(selectTableSQL);
            ResultSet resultSet = st.executeQuery();
            while (resultSet.next()) {
                Post post = new Post();
                post.setId(resultSet.getInt(1));
                post.setName(resultSet.getString(2));
                post.setDescription(resultSet.getString(3));
                post.setDate(resultSet.getDate(4));
                list.add(post);
            }
            st.close();
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public void removeById(Integer id) {
        //todo smth later
    }

    @Override
    public void getItemById(Integer id) {
        //todo smth later
    }

    @Override
    public void close() throws Exception {
        con.close();
    }

}
