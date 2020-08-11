package ru.makarov.system;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ru.makarov.interfaces.Parse;
import ru.makarov.model.Post;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class for parsing SQL web site.
 * It works only with link http://www.sql.ru/forum/job.
 */

public class SQLParse implements Parse {
    /**
     * @param db - init app.properties.
     * @param pageNumbers - size of pages for parsing(current value)
     * @param lastDate -  date of last update post or default in app.properties.
     * @param flag - value for stop parsing.
     * @param list - list of post from parsing.
     */
    private DBProperties db = new DBProperties();
    private String pageNumbers;
    private String lastDate;
    private boolean flag = false;
    private List<Post> list = new ArrayList<>();
    private static Logger logger = Logger.getLogger("InfoLogging");

    public SQLParse() {
    }

    public SQLParse(String lastDate) {
        this.lastDate = lastDate;
        initSQLparsing();
    }

    public void initSQLparsing() {
        Document document = null;
        try {
            document = Jsoup.connect(db.getWeblink()).get();
            Elements element = document.getElementsByClass("sort_options");
            Elements elements = element.get(1).getElementsByAttribute("href");
            this.pageNumbers = elements.get(elements.size() - 1).text();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method for init application page by page .
     */
    public void startParsing() {
        int num = Integer.parseInt(pageNumbers);
        int i = 0;
        while (i <= num) {
            if (flag) {
                break;
            }
            parserPage(Integer.toString(i));
            i++;
        }
    }

    @Override
    public List<Post> list() {
        startParsing();
        return list;
    }

    /**
     * Main parsing of Web site.
     *
     * @param pageNumber -  page html for parsing.
     */
    public void parserPage(String pageNumber) {
        String link = new StringBuilder().append(db.getWeblink()).append("/").append(pageNumber).toString();
        try {
            Document documentPage = Jsoup.connect(link).get();
            Elements element = documentPage.getElementsByClass("postslisttopic");
            for (int i = 0; i < element.size(); i++) {
                if (flag) {
                    logger.info("last updates were finished :");
                    break;
                }
                if (parserTitle(element.get(i).text())) {
                    Elements element1 = element.get(i).getElementsByClass("newTopic");
                    for (Element links : element1) {
                        String irl = links.attr("href");
                        Elements element2 = Jsoup.connect(irl).get().getElementsByClass("msgFooter");
                        Post post = new Post("Java", element.get(i).text());
                        post.setFormatDate(parText(element2.get(0).text()));
                        if (post.getDate().getTime() <= convertToData(lastDate).getTime()) {
                            flag = true;
                        } else {
                            list.add(post);
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param date - String for convert date in string to util.Date.
     * @return util.Date
     */
    public Date convertToData(String date) {
        Date result = null;
        SimpleDateFormat simpleDateFormatIn = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            result = simpleDateFormatIn.parse(date);
        } catch (ParseException e) {
            logger.info("not correct during convertation String to Date :");
        }
        return result;
    }

    /**
     * This method take parsed text and convert it to the date of first created topic.
     *
     * @param text - text wich was parsed in topic(first creation).
     * @return date of created topic.
     */
    public String parText(String text) {
        String newtext = text.split("\\[")[0];
        return newtext;
    }

    /**
     * Method to find java in topics of one page.
     *
     * @param lineTitle - title of topic.
     * @return true - if Java in title of topic.
     */
//todo to check work with word "JavaScript".
    public boolean parserTitle(String lineTitle) {
        boolean flag = false;
        Pattern p = Pattern.compile("[Jj]ava.+");
        Matcher m = p.matcher(lineTitle);
        if (m.find()) {
            flag = true;
        }
        return flag;
    }
}
