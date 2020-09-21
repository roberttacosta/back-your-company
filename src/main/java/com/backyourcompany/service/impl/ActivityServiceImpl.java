package com.backyourcompany.service.impl;

import com.backyourcompany.dto.ActivityRequest;
import com.backyourcompany.entities.Activity;
import com.backyourcompany.entities.Visit;
import com.backyourcompany.exceptions.ObjectNotFoundException;
import com.backyourcompany.repositories.ActivityRepository;
import com.backyourcompany.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public Activity findByTitleActivity(String titleActivity) {
        return activityRepository.findByActivityTitle(titleActivity)
                .orElseThrow(() -> new ObjectNotFoundException("The activity: " + titleActivity + " not exists."));
    }

    @Override
    public Activity findById(Long activityId) {
        return activityRepository.findById(activityId)
                .orElseThrow(() -> new ObjectNotFoundException("The activity: " + activityId + " not exists."));
    }

}
