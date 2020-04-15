package com.cleventy.springboilerplate.quartz;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigureJob {

	@Bean
	public static JobDetail printVersionJobDetails() {
		return JobBuilder.newJob(PrintVersionJob.class)
				.withIdentity("printVersionJob")
				.storeDurably()
				.build();
	}

	@Bean
	public static Trigger printVersionJobTrigger(JobDetail jobADetails) {

		return TriggerBuilder.newTrigger()
				.forJob(jobADetails)
				.withIdentity("printVersionJobTrigger")
				.withSchedule(CronScheduleBuilder.cronSchedule("0 * * ? * * *"))
				.build();
	}
}