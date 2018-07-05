package sqlparsing;
import java.io.*;
import java.util.ArrayList;
/**
 * This class include methods for parsing propereties of application.
 */
public class ParserDataXML implements AutoCloseable {
    private String url;
    private String nameData;
    private String user;
    private String password;
    private String parserFileWay;
    private String lastDate;

    public ParserDataXML(String parserFileWay) {
        this.parserFileWay = parserFileWay;
    }
    /**
     * This method make parsing for all document line by line.
     * @throws IOException
     */
    public void parser() throws Exception {
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(parserFileWay))) {
            bufferedReader.read();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (line.startsWith("<entry url=")) {
                    url = line.replaceAll("<entry url=", "");
                    url = url.substring(1, url.length() - 3);
                }
                if ((line.startsWith("<entry namedata="))) {
                    nameData = line.replaceAll("<entry namedata=", "");
                    nameData = nameData.substring(1, nameData.length() - 3);
                }
                if ((line.startsWith("<entry user="))) {
                    user = line.replaceAll("<entry user=", "");
                    user = user.substring(1, user.length() - 3);
                }
                if ((line.startsWith("<entry password="))) {
                    password = line.replaceAll("<entry password=", "");
                    password = password.substring(1, password.length() - 3);
                }
                if((line.startsWith("<entry lastDate="))){
                    lastDate = line.replaceAll("<entry lastDate=","");
                    lastDate = lastDate.substring(1,lastDate.length()-3);
                }
            }
        }
    }
    /**
     * Method for update last date in propereties.
     * @param date - string value of date which we should to update of our list propereties.
     */
    public void addToDataLastDate(String date) {
        ArrayList<String> list = new ArrayList<>();
        File file = new File(parserFileWay);
        FileWriter fr = null;
        BufferedWriter br = null;
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(parserFileWay));
            String line;
            while ((line = bufferedReader.readLine()) != null){
                if((line.startsWith("<entry lastDate="))){
                    line = "<entry lastDate=" + "\""+date+"\"/>";
                }
                list.add(line);
            }
            fr = new FileWriter(file);
            br = new BufferedWriter(fr);
            fr.write("");
            for (int i = 0; i < list.size() ; i++) {
                br.write(list.get(i));
                br.newLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            try {
                br.close();
                fr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getUrl() {
        return url;
    }

    public String getNameData() {
        return nameData;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public String getLastDate() {
        return lastDate;
    }

    public void setLastDate(String lastDate) {
        this.lastDate = lastDate;
    }

    public void close() throws Exception {
        System.out.println("yahoo");
    }
}
