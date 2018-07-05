package sqlparsing;
/**
 * Class for describe parameters of work database.
 */
public class WorkBase {

    private  String url;
    private  String nameData;
    private  String user;
    private  String password;
    private  String nameTable;

    public WorkBase(String url, String nameData, String user, String password) {
        this.url = url;
        this.nameData = nameData;
        this.user = user;
        this.password = password;
        this.nameTable = "mytask";
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getNameData() {
        return nameData;
    }

    public void setNameData(String nameData) {
        this.nameData = nameData;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNameTable() {
        return nameTable;
    }

    public void setNameTable(String nameTable) {
        this.nameTable = nameTable;
    }

}
