package sqlparsing;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Class describe Item  - parameteres and its methods.
 */
public class Item{
    /**
     * @param id - id for item.
     * @param name - name for item.
     * @param description - description of item.
     * @param date - date of ctreated item.
     */
    private String id;
    private String name;
    private String description;
    private Date date;

    public Item(){
    }

    public Item(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public void setID(String id){
        this.id = id;
    }

    public void setName(String name){
        if(this.name != null)
        {
            this.name = null;
        }
        this.name = name;
    }

    public void setDescription(String description){
        if(this.description != null)
        {
            this.description = null;
        }
        this.description = description;
    }

    public void setDate(String text){
        Locale local = new Locale("ru","RU");
        SimpleDateFormat simpleDateFormatIn = new SimpleDateFormat("dd MMMM yyyy, HH:mm", local);
        try {
            date = simpleDateFormatIn.parse(text);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void setCurrentDate(Date date){
        this.date = date;
    }

    public String getID(){
        return id;
    }

    public String getName(){
        return name;
    }

    public String getDescription(){
        return description;
    }

    public Date getDate(){
        return date;
    }

}