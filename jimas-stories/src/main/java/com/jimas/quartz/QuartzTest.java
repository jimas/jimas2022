package com.jimas.quartz;

import com.jimas.quartz.job.LoadJob;
import com.jimas.quartz.listener.LoadJobListener;
import org.junit.Test;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.triggers.CronTriggerImpl;

import java.io.IOException;

/**
 * @author liuqj
 */
public class QuartzTest {
    @Test
    public void testExecute() {
        try {
            Scheduler scheduler = new StdSchedulerFactory().getScheduler();
            scheduler.getListenerManager().addJobListener(new LoadJobListener());
            JobDetail jobDetail = JobBuilder.newJob(LoadJob.class).withIdentity(LoadJob.class.getName()).build();
            CronTriggerImpl trigger = new CronTriggerImpl();
            trigger.setName(LoadJob.class.getName());
            trigger.setCronExpression("*/1 * * * * ?");
            scheduler.scheduleJob(jobDetail, trigger);
            scheduler.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
