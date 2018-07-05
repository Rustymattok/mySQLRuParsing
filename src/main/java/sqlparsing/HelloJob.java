package sqlparsing;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
/**
 * Class for start job by schedule.
 */
public class HelloJob implements Job {
    private DBPropereties dbPropereties;
    private String fileName;

    public HelloJob(String fileName) {
        this.fileName = fileName;
        dbPropereties = new DBPropereties(fileName);
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        startJob();
    }

    public void startJob(){
        dbPropereties.initData();
        DataBase dataBase = new DataBase(new WorkBase(dbPropereties.getUrl(), dbPropereties.getNameTable(), dbPropereties.getUsername(), dbPropereties.getPassword()));
        ParsingHTML parsingHTML = new ParsingHTML(dbPropereties.getWeblink(), dataBase,dbPropereties.getFileWay());
        parsingHTML.startParsing();
    }
}