package pl.dreamteam.cc.skype.server;

import com.skype.*;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.Execution;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.stereotype.Component;
import pl.dreamteam.cc.model.VARS;
import pl.dreamteam.cc.service.ActivitiFacadeImpl;
import pl.dreamteam.cc.service.MessageService;

import javax.annotation.PostConstruct;
import java.util.Arrays;

/**
 * Created by abu on 23.05.2016.
 */

@Component
public class CallHandler implements org.springframework.context.ApplicationListener<ApplicationStartedEvent> {

    private static final Logger LOGGER = LogManager.getLogger(CallHandler.class);

    @Autowired
    ActivitiFacadeImpl activitiFacade;

    @Autowired
    RuntimeService runtimeService;

    @Autowired
    MessageService messageService;

    @Override
    public void onApplicationEvent(final ApplicationStartedEvent event) {
    }

    @PostConstruct
    public void init() {
        callHanlder();
    }

    public void callHanlder() {
        try {
            attachCallHandler();
        } catch (NotAttachedException e) {
            e.printStackTrace();
        } catch (SkypeException e) {
            e.printStackTrace();
        }
    }

    public void attachCallHandler() throws SkypeException {
        Skype.setDaemon(false);
        Skype.setDebug(true);
        Skype.addCallListener(new CallAdapter() {
            @Override
            public void callReceived(Call receivedCall) throws SkypeException {
                onCall(receivedCall);
                receivedCall.addCallStatusChangedListener(status -> onCallStatusChange(receivedCall, status));
            }
        });

        Skype.addChatMessageListener(new ChatMessageAdapter() {
            public void chatMessageReceived(ChatMessage received)
                    throws SkypeException {
                onMessage(received);
            }
        });
    }

    public void onCall(Call receivedCall) throws SkypeException {
        receivedCall.answer();
        onCall(receivedCall.getPartnerId());
    }

    public void onCall(String skypeId) {
        activitiFacade.startKolejkaGlownaProcess(skypeId);
    }

    public void onCallStatusChange(Call receivedCall, Call.Status status) throws SkypeException {
        if (Call.Status.FINISHED.equals(status))
            onCallFinished(receivedCall.getPartnerId());
    }

    public void onCallFinished(String skypeId) {
        activitiFacade.endAllProcesses(skypeId);
    }

    public void onMessage(ChatMessage received) throws SkypeException {
        LOGGER.info("RECEIVED FROM: " + received.getSenderDisplayName() + "[" + received.getSenderId() + "] MSG: " + received.getContent());

        if (received.getType().equals(ChatMessage.Type.SAID)) {
            onMessage(received.getSenderId(), received.getContent());
        } else {
            LOGGER.info(received.getType());
        }
    }

    public void onMessage(String skypeId, String input) {
        messageService.translate(skypeId, input);
    }

    public void endCall(Execution e) {
        endCall(runtimeService.getVariable(e.getId(), VARS.SKYPE_ID.name(), String.class));
    }

    //SO EASY TO READ xD
    public void endCall(String skypeId) {
        try {
            Arrays.stream(Skype.getAllActiveCalls()).filter(call -> {
                try {
                    return call.getPartnerId().equals(skypeId);
                } catch (SkypeException e) {
                    e.printStackTrace();
                }
                return false;
            }).forEach(call -> {
                try {
                    call.cancel();
                } catch (SkypeException e) {
                    e.printStackTrace();
                }
            });
        } catch (SkypeException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(Execution e, String text) {
        sendMessage(runtimeService.getVariable(e.getId(), VARS.SKYPE_ID.name(), String.class), text);
    }

    public void sendMessage(Execution e) {
        sendMessage(runtimeService.getVariable(e.getId(), VARS.SKYPE_ID.name(), String.class), "dupa");
    }

    public void sendMessage() {
        System.out.println("test");
//        sendMessage(runtimeService.getVariable(e.getId(), VARS.SKYPE_ID.name(), String.class), "dupa");
    }

    public void sendMessage(String skypeId, String text) {
        try {
            Skype.chat(skypeId).send(text);
        } catch (SkypeException e) {
            e.printStackTrace();
        }
    }

    public void forwardCall(Call receivedCall) throws SkypeException {
        Profile.CallForwardingRule[] oldRules = Skype.getProfile().getAllCallForwardingRules();
        System.out.println(receivedCall.getPartnerDisplayName() + " CALLIN");
        Skype.getProfile().setAllCallForwardingRules(new Profile.CallForwardingRule[]{new Profile.CallForwardingRule(0, 30, "echo123")});
        receivedCall.forward();
        try {
            Thread.sleep(10000); // to prevent finishing this call
        } catch (InterruptedException e) {
        }
        Skype.getProfile().setAllCallForwardingRules(oldRules);
    }
}
