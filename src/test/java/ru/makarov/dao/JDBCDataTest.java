package ru.makarov.dao;

import jdk.nashorn.internal.scripts.JD;
import org.junit.Test;
import ru.makarov.model.Post;

import java.util.List;

public class JDBCDataTest {

    @Test
    public void whenShouldCheckAdd() {
        JDBCData jdbcData = new JDBCData();
        Post post = new Post();
        post.setName("Java");
        post.setDescription("My Dream is Java1");
        post.setFormatDate("24 авг 19, 20:23");
        jdbcData.add(post);
        Post post1 = new Post();
        post1 = jdbcData.selectByTag("My Dream is Java");
        System.out.println(post1.getDescription());
    }

    @Test
    public void whenShouldCheallAllPost(){
        JDBCData jdbcData = new JDBCData();
        List<Post> list = jdbcData.allPost();
        System.out.println(list.size());
    }

}