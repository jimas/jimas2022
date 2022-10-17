package com.jimas.quartz.listener;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;

/**
 * @author liuqj
 */
public class LoadJobListener implements JobListener {
    @Override
    public String getName() {
        return "loadJobListener";
    }

    @Override
    public void jobToBeExecuted(JobExecutionContext context) {

    }

    @Override
    public void jobExecutionVetoed(JobExecutionContext context) {

    }

    @Override
    public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
        System.out.println(" error 处理中 " + jobException.getLocalizedMessage());
    }
}
