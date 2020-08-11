package ru.makarov.system;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import ru.makarov.dao.JDBCData;
import ru.makarov.model.Post;

import java.util.ArrayList;
import java.util.List;

/**
 * Class describe main Job activity by timing.
 */
public class GrabJob implements Job {

    public GrabJob() {
    }

    /**
     * First activity for parsing and init this file to the List.
     * Second save all list adata to DataBase.
     *
     * @param context -main activity from Grabber Job.
     * @throws JobExecutionException - if snth wrong with job.
     */
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JDBCData jdbcData = (JDBCData) context.getJobDetail().getJobDataMap().get("store");
        SQLParse sqlParse = (SQLParse) context.getJobDetail().getJobDataMap().get("parse");
        List<Post> list = new ArrayList<>();
        list = sqlParse.list();
        jdbcData.addList(list);
    }
}
