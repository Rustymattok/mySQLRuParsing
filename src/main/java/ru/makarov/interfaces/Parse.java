package ru.makarov.interfaces;

import ru.makarov.model.Post;

import java.util.List;

/**
 * Interface for parse main  methods.
 */
public interface Parse {
    /**
     * Parsing web site
     *
     * @return - all data in List.
     */
    List<Post> list();
}
