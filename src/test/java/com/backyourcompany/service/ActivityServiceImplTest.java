package com.backyourcompany.service;

import com.backyourcompany.dto.ActivityRequest;
import com.backyourcompany.entities.Activity;
import com.backyourcompany.impl.ActivityServiceImpl;
import com.backyourcompany.repositories.ActivityRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
public class ActivityServiceImplTest {

    @InjectMocks
    private ActivityServiceImpl activityServiceImpl;

    @Mock
    private ActivityRepository activityRepository;

    private Activity activity, secondActivity;

    @Before
    public void setUp(){
        this.activity = this.buildActivity();
        this.secondActivity = this.buildSecondActivity();
    }

    @Test
    public void createActivity_withSuccess(){
        Mockito.when(activityRepository.saveAndFlush(activity)).thenReturn(activity);

        ArgumentCaptor<Activity> argumentCaptor = ArgumentCaptor.forClass(Activity.class);

        activityServiceImpl.createActivity(new ActivityRequest("Auditoria", "Limpar conta", 2));

        Mockito.verify(activityRepository, Mockito.times(1)).saveAndFlush(argumentCaptor.capture());
    }

    @Test
    public void getAllActivities_withSuccess(){
        ArrayList<Activity> activities = new ArrayList<>();
        activities.add(activity);
        activities.add(secondActivity);

        Mockito.when(activityRepository.findAll()).thenReturn(activities);

        List<Activity> response = activityServiceImpl.getAllActivities();

        Assert.assertEquals(activities, response);
    }

    private Activity buildActivity(){
        return new Activity(1L, "Auditoria", "Limpar conta", 2);
    }

    private Activity buildSecondActivity(){
        return new Activity(2L, "Berçario", "Organizar prontuário", 1);
    }
}
