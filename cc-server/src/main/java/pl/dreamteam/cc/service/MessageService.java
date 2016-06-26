package pl.dreamteam.cc.service;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.delegate.event.ActivitiEventType;
import org.activiti.engine.delegate.event.impl.ActivitiEventBuilder;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
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

        String processId;
        ProcessInstance currentSubProcess = runtimeService.createProcessInstanceQuery().superProcessInstanceId(caller.getProcessInstanceId()).singleResult();
        if(currentSubProcess != null)
            processId = currentSubProcess.getId();
        else
            processId = caller.getProcessInstanceId();

        List<String> activeActivitiesIds = runtimeService.getActiveActivityIds(processId);
        String activityId = activeActivitiesIds.get(0);

        //This query assumes there is only one active activity
        Execution execution = findExecution(processId, activityId);
//
//        Execution e = runtimeService
//                .createExecutionQuery()
////                .activityId(activityId)
//                .parentId(caller.getProcessInstanceId())
//                .singleResult();
//
//        Execution e2 = runtimeService
//                .createExecutionQuery()
////                .activityId(activityId)
//                .parentId(e.getId())
//                .singleResult();
//
//        if(e2 != null) {
//            Execution e3 = runtimeService
//                    .createExecutionQuery()
//                    .parentId(e2.getId())
//                    .singleResult();
//        }
//
////        runtimeService.getActiveActivityIds("125018");
//
//        runtimeService.createProcessInstanceQuery().superProcessInstanceId(caller.getProcessInstanceId()).list()
//
//        runtimeService
//                .createExecutionQuery()
//                .executionId("125018")
//                .list();
//        runtimeService
//                .createExecutionQuery()
//                .parentId("125001")
//                .list();
//        runtimeService
//                .createExecutionQuery()
//                .parentId("125007")
//                .list();
//        runtimeService
//                .createExecutionQuery()
////                .parentId("125016")
//                .executionTenantId()
//                .list();
//
////        .parentId("125016")
//
//
//        runtimeService
//                .createExecutionQuery()
//                .variableValueEquals(VARS.SKYPE_ID.name(), skypeId)
//                .parentId(caller.getProcessInstanceId())
//                .list();
////                .singleResult();
//
//
//        if(runtimeService.getActiveActivityIds(execution.getId()).size() > 0) { //probably means this is a subprocess -> get actiity from it
//            activeActivitiesIds = runtimeService.getActiveActivityIds(execution.getProcessInstanceId());
//            activityId = activeActivitiesIds.get(0);
//
//            //This query assumes there is only one active activity
//            execution = findExecution(caller.getProcessInstanceId(), activityId);
//        }


        //This is not type safe, can change any time
        ExecutionEntity ee = (ExecutionEntity) execution;
        ee.getProcessDefinitionKey();


        send(activeActivitiesIds, ee.getProcessDefinitionKey(), ee.getProcessDefinitionId(), ee.getProcessInstanceId(), input);

    }

    public void send(List<String> acitiviyIds, String processDefinitionKey, String processDefinitionId, String processInstanceId, String input) {
        //POOR solution
        if (acitiviyIds.contains(ACTIVITY.PROCES_GLOWNY_ODBIERZ_WYBOR_TASK.getActivityId())) {
            String activityId = ACTIVITY.PROCES_GLOWNY_ODBIERZ_WYBOR_TASK.getActivityId();
            Execution execution = findExecution(processInstanceId, activityId);

            runtimeService.setVariable(processInstanceId, VARS.CHOICE_FAILURE.name(), false);

            try {
                Message msg = PROCESS.get(processDefinitionKey).getMessage(input);
                runtimeService.setVariable(processInstanceId, VARS.INPUT.name(), msg.getChoice());
                runtimeService.signal(execution.getId());
            } catch (ChoiceFailureException e) {
                Integer failureCount = (Integer) runtimeService.getVariable(processInstanceId, VARS.CHOICE_FAILURE_COUNT.name());

                runtimeService.setVariable(processInstanceId, VARS.CHOICE_FAILURE.name(), true);
                if (failureCount == 0)
                    runtimeService.setVariable(processInstanceId, VARS.CHOICE_FAILURE_COUNT.name(), Integer.valueOf(1));


            }
        } else if (acitiviyIds.contains(ACTIVITY.PROCES_LOGOWANIE_ODBIERZ_IDENTYFIKATOR_TASK.getActivityId())) {
            String activityId = ACTIVITY.PROCES_LOGOWANIE_ODBIERZ_IDENTYFIKATOR_TASK.getActivityId();
            Execution execution = findExecution(processInstanceId, activityId);

            runtimeService.setVariable(processInstanceId, VARS.LOGIN.name(), input);
            runtimeService.signal(execution.getId());
        } else if (acitiviyIds.contains(ACTIVITY.PROCES_LOGOWANIE_ODBIERZ_HASLO_TASK.getActivityId())) {
            String activityId = ACTIVITY.PROCES_LOGOWANIE_ODBIERZ_HASLO_TASK.getActivityId();
            Execution execution = findExecution(processInstanceId, activityId);

            runtimeService.setVariable(processInstanceId, VARS.HASLO.name(), input);
            runtimeService.signal(execution.getId());
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
