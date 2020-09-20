package com.backyourcompany;

import com.backyourcompany.dto.ActivityRequest;
import com.backyourcompany.entities.Activity;

import java.util.List;

public interface ActivityService {

    Activity createActivity(ActivityRequest activityRequest);
    List<Activity> getAllActivities();
}
