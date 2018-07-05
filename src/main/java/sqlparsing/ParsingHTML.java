package sqlparsing;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * This class include methods for parsing web site.
 */
public class ParsingHTML {
    private String pageNumbers;
    private String url;
    private DataStrategy dataStrategy;
    private boolean flag = false;
    private DBPropereties dbPropereties;
    private static Logger LOGGER = Logger.getLogger("InfoLogging");
    /**
     * Method to take all html code from url.
     * @param url
     */
    public ParsingHTML(String url,DataStrategy dataStrategy,String dataList) {
        dbPropereties = new DBPropereties(dataList);
        this.url = url;
        this.dataStrategy = dataStrategy;
        try {
            Document document = Jsoup.connect(url).get();
            Elements element = document.getElementsByClass("sort_options");
            Elements elements = element.get(1).getElementsByAttribute("href");
            this.pageNumbers = elements.get(elements.size() - 1).text();
            try {
                dbPropereties.initData();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Method to start parsing page step by step.
     * @param pageNumber
     */
    public  void parserPage(String pageNumber) {
        String link = new StringBuilder().append(url).append("/").append(pageNumber).toString();
        try {
            Document documentPage = Jsoup.connect(link).get();
            Elements element = documentPage.getElementsByClass("postslisttopic");
            for (int i = 0; i < element.size(); i++) {
                if (flag){
                    LOGGER.info("last updates were finished :");
                    break;
                }
                if (parserTitle(element.get(i).text())){
                    Elements element1 = element.get(i).getElementsByClass("newTopic");
                    for(Element links : element1){
                        String irl = links.attr("href");
                        Elements element2 = Jsoup.connect(irl).get().getElementsByClass("msgFooter");
                        Item item = new Item("Java", element.get(i).text());
                        item.setDate(parText(element2.get(0).text()));
                        if(item.getDate().getTime() < dataStrategy.convertToData(dbPropereties.getLastDate()).getTime()){
                            flag = true;
                            dbPropereties.setNewDate(dataStrategy.selectMax());
                        }else{
                            dataStrategy.addTodata(item);
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Method to find java in topics of one page.
     * @param lineTitle
     * @return
     */
//todo to check work with word "JavaScript".
    public boolean parserTitle(String lineTitle){
        boolean flag = false;
        Pattern p = Pattern.compile("[Jj]ava.+");
        Matcher m = p.matcher(lineTitle);
        if(m.find()){
            flag = true;
        }
        return flag;
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
    /**
     * This method take parsed text and convert it to the date of first created topic.
     * @param text - text wich was parsed in topic(first creation).
     * @return  date of created topic.
     */
    public String parText(String text){
        String newtext  = text.split("\\[")[0];
        return newtext;
    }
}
