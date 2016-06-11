package pl.dreamteam.cc.service;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.delegate.event.ActivitiEventType;
import org.activiti.engine.delegate.event.impl.ActivitiEventBuilder;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.runtime.Execution;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.dreamteam.cc.exception.ChoiceFailureException;
import pl.dreamteam.cc.model.*;
import pl.dreamteam.cc.service.repository.CallerRepository;

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

    public void translate(String skypeId, String input) {
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


        send(activeActivitiesIds, ee.getProcessDefinitionKey(), ee.getProcessDefinitionId(), caller.getProcessInstanceId(), input);

    }

    public void send(List<String> acitiviyIds, String processDefinitionKey, String processDefinitionId, String processInstanceId, String input) {
        //POOR solution
        if (acitiviyIds.contains(ACTIVITY.PROCES_GLOWNY_ODBIERZ_WYBOR_TASK.getActivityId())) {
            String activityId = ACTIVITY.PROCES_GLOWNY_ODBIERZ_WYBOR_TASK.getActivityId();
            Execution execution = findExecution(processInstanceId, activityId);

            try {
                Message msg = PROCESS.get(processDefinitionKey).getMessage(input);
                runtimeService.setVariable(processInstanceId, VARS.INPUT.name(), msg.getChoice());
                runtimeService.signal(execution.getId());
            } catch (ChoiceFailureException e) {
                Integer failureCount = (Integer) runtimeService.getVariable(processInstanceId, VARS.CHOICE_FAILURE_COUNT.name());


//                runtimeService.createExecutionQuery().processInstanceId(processInstanceId).eve
//                Execution msgExec = runtimeService.createExecutionQuery().processInstanceId(processInstanceId).messageEventSubscriptionName("message1").singleResult();


                if (failureCount == 0)
                    runtimeService.setVariable(processInstanceId, VARS.CHOICE_FAILURE_COUNT.name(), Integer.valueOf(1));


//                runtimeService.messageEventReceived(ERROR.processGlownyErrorBlednyWybor.name(), execution.getId());
//                runtimeService.messageEventReceived(ERROR.processGlownyErrorBlednyWybor.name(), execution.getId());
                runtimeService.dispatchEvent(ActivitiEventBuilder.createErrorEvent(
                        ActivitiEventType.ACTIVITY_ERROR_RECEIVED,
                        activityId,
                        ERROR.processGlownyErrorBlednyWybor.name(),
                        execution.getId(),
                        processInstanceId,
                        processDefinitionId));
//                }
            }
        }
    }

    public Execution findExecution(String processId, String activityId) {
        return runtimeService
                .createExecutionQuery()
                .activityId(activityId)
                .processInstanceId(processId)
                .singleResult();
    }


}
