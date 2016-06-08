package pl.dreamteam.cc.skype.server;

import org.activiti.engine.RuntimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import pl.dreamteam.cc.dto.Status;
import pl.dreamteam.cc.model.Applicant;
import pl.dreamteam.cc.model.Caller;
import pl.dreamteam.cc.model.Consultant;
import pl.dreamteam.cc.service.ConsultantService;
import pl.dreamteam.cc.service.NoConsultantAvailableException;
import pl.dreamteam.cc.service.repository.ApplicantRepository;
import pl.dreamteam.cc.service.repository.ConsultantRepository;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by abu on 23.05.2016.
 */

@Service
@Transactional
public class ActivitiFacadeImpl implements ActivitiService{

  @Autowired
  ConsultantRepository consultantRepository;

  public void startKolejkaGlownaProcess(String skypeId){
    Caller caller = Caller.builder().skypeId(skypeId).build();

    Map<String, Object> vars = Collections.<String, Object>singletonMap("caller", caller);
    runtimeService.startProcessInstanceByKey("procesGlowny", vars);
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
