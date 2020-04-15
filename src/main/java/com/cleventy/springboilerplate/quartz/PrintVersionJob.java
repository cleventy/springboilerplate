package com.cleventy.springboilerplate.quartz;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cleventy.springboilerplate.commons.properties.CommonProperties;

import lombok.extern.slf4j.Slf4j;

@Component
@DisallowConcurrentExecution
@Slf4j
public class PrintVersionJob implements Job {

    @Autowired
    private CommonProperties commonProperties;

    @Override
    public void execute(JobExecutionContext context) {
    	log.debug("Executing app version " + this.commonProperties.getVersion());
    }
    
}