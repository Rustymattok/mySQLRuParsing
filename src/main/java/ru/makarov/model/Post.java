package ru.makarov.model;

import lombok.Data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Class describe Post  - parameteres and its methods.
 */
@Data
public class Post {
    /**
     * @param id - id for item.
     * @param name - name for item.
     * @param description - description of item.
     * @param date - date of ctreated item.
     */
    private Integer id;
    private String name;
    private String description;
    private Date date;

    public Post() {
    }

    public Post(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public void setFormatDate(String text) {
        Locale local = new Locale("ru", "RU");
        SimpleDateFormat simpleDateFormatIn = new SimpleDateFormat("dd MMMM yyyy, HH:mm", local);
        try {
            date = simpleDateFormatIn.parse(text);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

}