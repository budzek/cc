package pl.dreamteam.cc;

import com.skype.Call;
import com.skype.CallAdapter;
import com.skype.Skype;
import com.skype.SkypeException;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.subethamail.wiser.Wiser;
import pl.dreamteam.cc.model.Applicant;
import pl.dreamteam.cc.service.ProcessGlownyDelegate;
import pl.dreamteam.cc.service.repository.ApplicantRepository;
import pl.dreamteam.cc.skype.server.CallHandler;
import pl.dreamteam.cc.skype.server.SkypeUtils;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {CCApplication.class})
@WebAppConfiguration
@IntegrationTest
public class ProcesGlownyTest {

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private HistoryService historyService;

    @Autowired
    private ApplicantRepository applicantRepository;

    @Autowired
    private CallHandler callHandler;

    private Wiser wiser;

//    @Before
//    public void setup() {
//        wiser = new Wiser();
//        wiser.setPort(1025);
//        wiser.start();
//    }
//
//    @After
//    public void cleanup() {
//        wiser.stop();
//    }


    @Test
    public void doubleWrongChoiceTest(){
        String skypeId = "testSkypeId";
        String msg = "stupid choice";
        callHandler.onCall(skypeId);
        callHandler.onMessage(skypeId, msg);
        callHandler.onMessage(skypeId, msg);
    }


}
