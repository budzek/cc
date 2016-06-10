package pl.dreamteam.cc.skype.server;

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
import pl.dreamteam.cc.model.VARS;
import pl.dreamteam.cc.service.repository.CallerRepository;
import pl.dreamteam.cc.service.repository.ConsultantRepository;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.Map;

/**
 * Created by abu on 23.05.2016.
 */

@Service
@Transactional
public class ActivitiFacadeImpl implements ActivitiService{

  private static final Logger LOGGER = LogManager.getLogger(CallHandler.class);

  @Autowired
  ConsultantRepository consultantRepository;

  @Autowired
  CallerRepository callerRepository;

  @PostConstruct
  public void init(){
    runtimeService.addEventListener(new ActivitiEventListener(){

      @Override
      public void onEvent(ActivitiEvent event) {
        LOGGER.info("EVENT: " + event.getType() + " " + event.getProcessInstanceId() + " " + event.getExecutionId() + " " + event.getProcessDefinitionId());
      }

      @Override
      public boolean isFailOnException() {
        return false;
      }
    } );
  }

  public void startKolejkaGlownaProcess(String skypeId){
    Caller caller = Caller.builder().skypeId(skypeId).build();

    Map<String, Object> vars = Collections.<String, Object>singletonMap("caller", caller);
    ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("procesGlowny", vars);

    caller.setProcessInstanceId(processInstance.getId());
    callerRepository.save(caller);

    runtimeService.setVariable(processInstance.getId(), VARS.SKYPE_ID.name(), caller.getSkypeId());
  }

  @Autowired
  private RuntimeService runtimeService;

//  @Autowired
//  private ApplicantRepository applicantRepository;

//  public void startHireProcess(@RequestBody Map<String, String> data) {
//
//    Applicant applicant = new Applicant(data.get("name"), data.get("email"), data.get("phoneNumber"));
//    applicantRepository.save(applicant);
//
//    Map<String, Object> vars = Collections.<String, Object>singletonMap("applicant", applicant);
//    runtimeService.startProcessInstanceByKey("hireProcessWithJpa", vars);
//  }

}
