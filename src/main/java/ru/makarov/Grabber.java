package ru.makarov;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import ru.makarov.interfaces.Grab;
import ru.makarov.interfaces.Parse;
import ru.makarov.interfaces.Store;
import ru.makarov.model.Post;
import ru.makarov.system.DBProperties;
import ru.makarov.system.GrabJob;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * Grabber parameters of JOB.
 */
public class Grabber implements Grab {
    /**
     * @param cfg - parameters of app.properties.
     */
    private DBProperties cfg;

    public Grabber() {
        this.cfg = new DBProperties();
    }

    /**
     * Sheduler work.
     *
     * @return - schedule timer.
     * @throws SchedulerException - if smth wrong.
     */
    public Scheduler scheduler() throws SchedulerException {
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        scheduler.start();
        return scheduler;
    }

    /**
     * @param store - type of data base where should we take last date.
     * @return - last date.
     */
    public String lastDate(Store store) {
        String lastDate = null;
        String lastDateFinal = store.selectMax();
        Long timeUpdate = null;
        if (lastDateFinal != null) {
            timeUpdate = convertToDate(lastDateFinal).getTime();
            Long timeDefaultUpdate = convertToDate(cfg.getLastDate()).getTime();
            if (timeUpdate < timeDefaultUpdate) {
                lastDate = lastDateFinal;
            }
        } else {
            lastDate = cfg.getLastDate();
        }
        return lastDate;
    }

    /**
     * Main method for start work.
     *
     * @param parse     - type of class parsing(SQL, HH and ect)
     * @param store     - type of DataBase.
     * @param scheduler - timer for work.
     * @throws SchedulerException - if smth wrong.
     */
    @Override
    public void init(Parse parse, Store store, Scheduler scheduler) throws SchedulerException {
        JobDataMap data = new JobDataMap();
        data.put("store", store);
        data.put("parse", parse);
        JobDetail job = newJob(GrabJob.class)
                .usingJobData(data)
                .build();
        SimpleScheduleBuilder times = simpleSchedule()
                .withIntervalInSeconds(Integer.parseInt(cfg.getTime()))
                .repeatForever();
        Trigger trigger = newTrigger()
                .startNow()
                .withSchedule(times)
                .build();
        scheduler.scheduleJob(job, trigger);
    }

    /**
     * Equal REST API manupilation.
     *
     * @param store - databse for work with data.
     * @value - /?msg=allPosts - show all data in data base.
     * @value - /?msg=lastDate - show final updaete of data base.
     */
    public void web(Store store) {
        new Thread(() -> {
            try (ServerSocket server = new ServerSocket(Integer.parseInt("9000"))) {
                while (!server.isClosed()) {
                    Socket socket = server.accept();
                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
                        String str;
                        while (!(str = in.readLine()).isEmpty()) {
                            if (str.contains("?msg=allPosts")) {
                                try (OutputStream out = socket.getOutputStream()) {
                                    out.write("HTTP/1.1 200 OK\r\n\r\n".getBytes());
                                    for (Post post : store.allPost()) {
                                        out.write(post.toString().getBytes());
                                        out.write(System.lineSeparator().getBytes());
                                    }
                                } catch (IOException io) {
                                    io.printStackTrace();
                                }
                            }
                            if (str.contains("?msg=lastDate")) {
                                try (OutputStream out = socket.getOutputStream()) {
                                    out.write("HTTP/1.1 200 OK\r\n\r\n".getBytes());
                                    out.write(store.selectMax().toString().getBytes());
                                    out.write(System.lineSeparator().getBytes());
                                } catch (IOException io) {
                                    io.printStackTrace();
                                }
                            }
                        }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    /**
     * @param date - date String to convert for format "yyyy-MM-dd HH:mm:ss".
     * @return - date with format.
     */
    public Date convertToDate(String date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return simpleDateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
