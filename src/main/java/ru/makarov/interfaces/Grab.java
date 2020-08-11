package ru.makarov.interfaces;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;

/**
 * Interface for Grabber init.
 */
public interface Grab {
    /**
     * Method for start Grabber job.
     *
     * @param parse     - type of class parsing(SQL, HH and ect)
     * @param store     - type of DataBase.
     * @param scheduler - timer for work.
     * @throws SchedulerException - if parametr is not OK.
     */
    void init(Parse parse, Store store, Scheduler scheduler) throws SchedulerException;
}
