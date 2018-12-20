package com.example.quartz.job;

import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Mr.Yangxiufeng
 * Date: 2018-09-19
 * Time: 14:35
 */
@Component
@Slf4j
public class Job1 extends QuartzJobBean {
    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        log.info("@@@@@@@@@@@@        "+(new SimpleDateFormat("yyyy-mm-dd HH:mm:ss.SSSSSS")).format(new Date())+"         @@@@@@@@@@@");
        log.info("@@@@@@@@@@@@@@@@@@@@@@@        Job1         @@@@@@@@@@@@@@@@@@@@@@");
        log.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
    }
}
