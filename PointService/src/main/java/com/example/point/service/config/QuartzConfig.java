//package com.example.point.service.config;
//
//import com.example.point.service.job.SampleJob;
//import com.example.point.service.job.SecondJob;
//import org.quartz.*;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.scheduling.quartz.JobDetailFactoryBean;
//import org.springframework.scheduling.quartz.SchedulerFactoryBean;
//import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;
//
//@Configuration
//public class QuartzConfig {
//    @Bean
//    public SchedulerFactoryBean schedulerFactoryBean(Trigger trigger, JobDetail job) {
//        SchedulerFactoryBean factory = new SchedulerFactoryBean();
//        factory.setOverwriteExistingJobs(false);  // Replace jobs with the same key
//        factory.setAutoStartup(true);  // Start the scheduler when the application starts
//        factory.setJobDetails(job);
//        factory.setTriggers(trigger);
//
//        // Other Quartz properties can be set here
//        return factory;
//    }
//    @Bean(name = "secondDetail")
//    public JobDetailFactoryBean secondDetail() {
//        JobDetailFactoryBean jobDetailFactory = new JobDetailFactoryBean();
//        jobDetailFactory.setJobClass(SecondJob.class);
//        jobDetailFactory.setDescription("Invoke Sample Job service...");
//        jobDetailFactory.setName("Test Job");
//        jobDetailFactory.setGroup("Test group");
//        jobDetailFactory.setDurability(true);
//        return jobDetailFactory;
//    }
//
//    @Bean(name = "sampleDetail")
//    public JobDetailFactoryBean sampleDetail() {
//        JobDetailFactoryBean jobDetailFactory = new JobDetailFactoryBean();
//        jobDetailFactory.setJobClass(SampleJob.class);
//        jobDetailFactory.setDescription("Invoke Sample Job service...");
//        jobDetailFactory.setName("Test Job");
//        jobDetailFactory.setGroup("Test group");
//        jobDetailFactory.setDurability(true);
//        return jobDetailFactory;
//    }
//    @Bean
//    public SimpleTriggerFactoryBean trigger(JobDetail job) {
//        SimpleTriggerFactoryBean trigger = new SimpleTriggerFactoryBean();
//        trigger.setJobDetail(job);
//        trigger.setRepeatInterval(5000);
//        trigger.setName("Test trigger");
//        trigger.setDescription("Test trigger description");
//        trigger.setRepeatCount(SimpleTrigger.REPEAT_INDEFINITELY);
//        return trigger;
//    }
//}
