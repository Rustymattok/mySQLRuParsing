package sqlparsing;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;
/**
 * Class for orgonizing schedule for start application.
 */
public class QuartzJob {
    private DBPropereties dbPropereties;
    private HelloJob helloJob;

    public QuartzJob(DBPropereties dbPropereties) {
        this.dbPropereties = dbPropereties;
        helloJob = new HelloJob(dbPropereties.getFileWay());
        dbPropereties.initData();
    }

    public void startApplication() {
        try {
            helloJob.startJob();
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            JobDetail job = newJob(HelloJob.class)
                    .withIdentity("job1", "group1")
                    .build();
            Trigger trigger = newTrigger()
                    .withIdentity("trigger1", "group1")
                    .withSchedule(cronSchedule(dbPropereties.getCorExp()))
                    .forJob(job)
                    .build();
            scheduler.scheduleJob(job, trigger);
            scheduler.start();
        } catch (SchedulerException se) {
            se.printStackTrace();
        }
    }
}
