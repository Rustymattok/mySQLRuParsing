package ru.makarov.interfaces;

import ru.makarov.model.Post;

import java.util.List;

/**
 * Main interface for work with DataBase
 */
public interface Store extends AutoCloseable {
    /**
     * @param item - add new post to dataBase.
     */
    void add(Post item);

    /**
     * @param posts - add all posts in list.
     */
    void addList(List<Post> posts);

    /**
     * @param id - remove post by id.
     */
    void removeById(Integer id);

    /**
     * @param id - get post by id.
     */
    void getItemById(Integer id);

    /**
     * @return - select max date of updated post.
     */
    String selectMax();

    /**
     * @param tag - init tag of search.
     * @return - post by Tag.
     */
    Post selectByTag(String tag);

    /**
     *
     * @return all list of post in DataBase.
     */
    List<Post> allPost();
}
