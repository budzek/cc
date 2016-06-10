package pl.dreamteam.cc.service;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.ActivitiEventType;
import org.activiti.engine.delegate.event.impl.ActivitiEventBuilder;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.runtime.Execution;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.dreamteam.cc.model.*;
import pl.dreamteam.cc.service.repository.CallerRepository;
import pl.dreamteam.cc.skype.server.CallHandler;

import java.util.List;

/**
 * Created by abu on 09.06.2016.
 */

@Component
public class MessageService {

    private static final Logger LOGGER = LogManager.getLogger(MessageService.class);


    @Autowired
    RuntimeService runtimeService;

    @Autowired
    ProcessEngine processEngine;

    @Autowired
    CallerRepository callerRepository;

    public Pair<String, Message> translate(String skypeId, String input){
        //TODO move filtering to query
        Caller caller = callerRepository.findAll().stream().
                filter(c -> c.getSkypeId().equals(skypeId)).findFirst().
                orElseThrow(() -> new RuntimeException("No such caller active : " + skypeId));

        List<String> activeActivitiesIds = runtimeService.getActiveActivityIds(caller.getProcessInstanceId());
        String activityId = activeActivitiesIds.get(0);

        //This query assumes there is only one active activity
        Execution execution = findExecution(caller.getProcessInstanceId(), activityId);

        //This is not type safe, can change any time
        ExecutionEntity ee = (ExecutionEntity) execution;
        ee.getProcessDefinitionKey();

        Message msg = PROCESS.get(ee.getProcessDefinitionKey()).getMessage(input);

        send(activeActivitiesIds, caller.getProcessInstanceId(), msg);


        return Pair.of(execution.getId(), msg);
    }

    public void send(List<String> acitiviyIds, String processId, Message msg){
      //POOR solution
        if(acitiviyIds.contains(ACTIVITY.PROCES_GLOWNY_ODBIERZ_WYBOR_TASK.getActivityId())){
            Execution execution = findExecution(processId, ACTIVITY.PROCES_GLOWNY_ODBIERZ_WYBOR_TASK.getActivityId());
//            runtimeService.setVariable(execution.getId(), VARS.INPUT.name(), msg.getChoice());
            runtimeService.setVariable(processId, VARS.INPUT.name(), msg.getChoice());
            runtimeService.signal(execution.getId());
            //or send message to the bound event
        }

        /** on error
         runtimeService.dispatchEvent(ActivitiEventBuilder.createErrorEvent(
         ActivitiEventType.ACTIVITY_ERROR_RECEIVED,
         activityId,
         "errorCode",
         execution.getId(),
         execution.getProcessInstanceId(),
         ee.getProcessDefinitionId()));
         */
    }

    public Execution findExecution(String processId, String activityId){
        return runtimeService
                .createExecutionQuery()
                .activityId(activityId)
                .processInstanceId(processId)
                .singleResult();
    }


}
