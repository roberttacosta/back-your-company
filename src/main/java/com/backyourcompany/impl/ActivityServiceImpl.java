package com.backyourcompany.impl;

import com.backyourcompany.ActivityService;
import com.backyourcompany.dto.ActivityRequest;
import com.backyourcompany.entities.Activity;
import com.backyourcompany.repositories.ActivityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ActivityServiceImpl implements ActivityService {

    private final ActivityRepository activityRepository;

    @Autowired
    public ActivityServiceImpl (ActivityRepository activityRepository){
        this.activityRepository = activityRepository;
    }

    @Override
    public Activity createActivity(ActivityRequest activityRequest){
        return activityRepository.saveAndFlush(new Activity(null, activityRequest.getActivityTitle(), activityRequest.getActivitySubtitle(), activityRequest.getSla()));
    }

    @Override
    public List<Activity> getAllActivities(){
        return activityRepository.findAll();
    }

}
