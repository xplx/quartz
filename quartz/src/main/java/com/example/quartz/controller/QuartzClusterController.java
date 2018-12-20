package com.example.quartz.controller;

import com.example.quartz.service.ISchedulerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Mr.Yangxiufeng
 * Date: 2018-09-19
 * Time: 15:30
 */
@RestController
public class QuartzClusterController {
    @Autowired
    private ISchedulerService schedulerService;
    /**
     * 每隔多少秒调度一次。
     *
     * @param seconds
     * @return
     */
    @GetMapping("/modify/{seconds}")
    public String modifyStartQuartz(@PathVariable String seconds){
        // eg: 0/10 * * ? * * *
        try {
            schedulerService.schedule("jobTrigger", "DEFAULT", "0/" + seconds + " * * ? * * *");
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed";
        }
        return "Successful";
    }
}
