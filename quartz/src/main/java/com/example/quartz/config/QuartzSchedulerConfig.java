package com.example.quartz.config;

import com.example.quartz.job.Job1;
import com.example.quartz.job.Job2;
import lombok.extern.slf4j.Slf4j;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.spi.JobFactory;
import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Mr.Yangxiufeng
 * Date: 2018-09-19
 * Time: 14:32
 */
@Configuration
@Slf4j
public class QuartzSchedulerConfig {
    @Autowired
    private DataSource dataSource;

    private static final String QUARTZ_PROPERTIES_NAME = "/quartz.properties";

    @Bean
    public JobFactory jobFactory(ApplicationContext applicationContext) {
        AutowiringSpringBeanJobFactory jobFactory = new AutowiringSpringBeanJobFactory();
        jobFactory.setApplicationContext(applicationContext);
        return jobFactory;
    }

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(JobFactory jobFactory,CronTrigger[] trigger,JobDetail[] jobDetails) {
        SchedulerFactoryBean factoryBean = new SchedulerFactoryBean();
        try {
            factoryBean.setQuartzProperties(quartzProperties());
            factoryBean.setDataSource(dataSource);
            factoryBean.setJobFactory(jobFactory);
            factoryBean.setTriggers(trigger);
            factoryBean.setJobDetails(jobDetails);
            factoryBean.setOverwriteExistingJobs(true);
        } catch (Exception e) {
            log.error("加载 {} 配置文件失败.", QUARTZ_PROPERTIES_NAME, e);
            throw new RuntimeException("加载配置文件失败", e);
        }
        return factoryBean;
    }
    @Bean(name = "jobTrigger")
    public CronTriggerFactoryBean job1Trigger(@Qualifier("jobDetail") JobDetail jobDetail) {
        CronTriggerFactoryBean cronTriggerFactoryBean = new CronTriggerFactoryBean();
        cronTriggerFactoryBean.setJobDetail(jobDetail);
        cronTriggerFactoryBean.setCronExpression("0/15 * * * * ?");
//        cronTriggerFactoryBean.setName("TEST_TRIGGER");
//        cronTriggerFactoryBean.setGroup("TEST_TRIGGER_GROUP");
        return cronTriggerFactoryBean;
    }

    @Bean(name = "jobDetail")
    public JobDetailFactoryBean job1Detail() {
        JobDetailFactoryBean jobDetailFactoryBean = new JobDetailFactoryBean();
        jobDetailFactoryBean.setJobClass(Job1.class);
//        jobDetailFactoryBean.setGroup("TEST_GROUP_01");
//        jobDetailFactoryBean.setName("TEST_JOB_DETAIL_01");
        jobDetailFactoryBean.setDurability(true);
        return jobDetailFactoryBean;
    }

    @Bean
    public Properties quartzProperties() throws IOException {
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        propertiesFactoryBean.setLocation(new ClassPathResource(QUARTZ_PROPERTIES_NAME));
        propertiesFactoryBean.afterPropertiesSet();
        return propertiesFactoryBean.getObject();
    }

    class AutowiringSpringBeanJobFactory extends SpringBeanJobFactory implements ApplicationContextAware {

        private transient AutowireCapableBeanFactory beanFactory;

        @Override
        public void setApplicationContext(final ApplicationContext context) {
            beanFactory = context.getAutowireCapableBeanFactory();
        }

        @Override
        protected Object createJobInstance(final TriggerFiredBundle bundle)
                throws Exception {
            final Object job = super.createJobInstance(bundle);
            beanFactory.autowireBean(job);
            return job;
        }
    }

}
