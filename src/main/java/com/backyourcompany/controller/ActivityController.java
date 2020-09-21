package com.backyourcompany.controller;

import com.backyourcompany.dto.ActivityRequest;
import com.backyourcompany.entities.Activity;
import com.backyourcompany.service.ActivityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("activity")
public class ActivityController {

    private final ActivityService activityService;

    @Autowired
    public ActivityController(ActivityService activityService){
        this.activityService = activityService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Activity createActivity(@Valid @RequestBody ActivityRequest activityRequest) {
        log.info("Creating the new activity {}", activityRequest);
        return activityService.createActivity(activityRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Activity> getAllActivities() {
        log.info("Show all activities");
        return activityService.getAllActivities();
    }
}
