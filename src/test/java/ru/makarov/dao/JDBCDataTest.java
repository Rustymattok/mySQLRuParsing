package ru.makarov.dao;

import jdk.nashorn.internal.scripts.JD;
import org.junit.Test;
import ru.makarov.model.Post;

import java.util.Date;
import java.util.List;

public class JDBCDataTest {

    @Test
    public void whenShouldCheckAdd() {
        JDBCData jdbcData = new JDBCData();
        Post post = new Post();
        post.setName("Java");
        post.setDescription("My Dream is Java1");
        post.setDate(new Date());
        jdbcData.add(post);
        Post post1 = new Post();
        post1 = jdbcData.selectByTag("My Dream is Java");
    }

    @Test
    public void whenShouldCheallAllPost(){
        JDBCData jdbcData = new JDBCData();
        List<Post> list = jdbcData.allPost();
    }

}