package com.jimas.quartz.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;

/**
 * @author liuqj
 */
public class LoadJob implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        System.out.println("execute^^^^^^");
        throw new NullPointerException();
    }
}
