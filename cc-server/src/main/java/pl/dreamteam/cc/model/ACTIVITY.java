package pl.dreamteam.cc.model;

import java.util.Arrays;

/**
 * Created by abu on 11.06.2016.
 * 
 * The class contains ids of activities, to which messages must be dispatched.
 */
public enum ACTIVITY {
    PROCES_GLOWNY_ODBIERZ_WYBOR_TASK("odbierzWyborTask");

    private final String activityId;

    ACTIVITY(String activityId) {
        this.activityId = activityId;
    }

    public String getActivityId() {
        return activityId;
    }

    public static ACTIVITY get(String activityId){
        return Arrays.stream(values()).filter(a -> a.activityId.equals(activityId)).findFirst().orElseThrow(() -> new RuntimeException("No activity found for: " + activityId));
    }
}
