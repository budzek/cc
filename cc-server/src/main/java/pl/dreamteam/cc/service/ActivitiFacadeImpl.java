package pl.dreamteam.cc.service;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.ActivitiEventListener;
import org.activiti.engine.runtime.ProcessInstance;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.dreamteam.cc.model.Caller;
import pl.dreamteam.cc.model.ERROR;
import pl.dreamteam.cc.model.VARS;
import pl.dreamteam.cc.service.repository.CallerRepository;
import pl.dreamteam.cc.service.repository.ConsultantRepository;
import pl.dreamteam.cc.skype.server.ActivitiService;
import pl.dreamteam.cc.skype.server.CallHandler;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by abu on 23.05.2016.
 */

@Service
@Transactional
public class ActivitiFacadeImpl implements ActivitiService {

    private static final Logger LOGGER = LogManager.getLogger(ActivitiFacadeImpl.class);

    @Autowired
    ConsultantRepository consultantRepository;

    @Autowired
    CallerRepository callerRepository;


    @Autowired
    private RuntimeService runtimeService;

    @PostConstruct
    public void init() {
        runtimeService.addEventListener(new ActivitiEventListener() {

            @Override
            public void onEvent(ActivitiEvent event) {
                LOGGER.info("EVENT: " + event.getType() + " " + event.getProcessInstanceId() + " " + event.getExecutionId() + " " + event.getProcessDefinitionId());
            }

            @Override
            public boolean isFailOnException() {
                return false;
            }
        });
    }

    public void startKolejkaGlownaProcess(String skypeId) {
        Caller caller = Caller.builder().skypeId(skypeId).build();

        Map<String, Object> vars = new HashMap<>();
        vars.put("caller", caller);
        vars.put(VARS.CHOICE_FAILURE_COUNT.name(), Integer.valueOf(0));
        vars.put(VARS.SKYPE_ID.name(), caller.getSkypeId());


        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("procesGlowny", vars);

        caller.setProcessInstanceId(processInstance.getId());
        callerRepository.save(caller);
    }


    public void endAllProcesses(String skypeId) {
        //TODO THIS SHOULD RATHER GO AS EVENT
        runtimeService.createProcessInstanceQuery().variableValueEquals(VARS.SKYPE_ID.name(), skypeId).list().stream().forEach(p -> runtimeService.deleteProcessInstance(p.getId(), "CALL FINISHED"));

        callerRepository.findAll().stream().filter(c -> c.getSkypeId().equals(skypeId)).forEach(c -> callerRepository.delete(c));
    }

}
