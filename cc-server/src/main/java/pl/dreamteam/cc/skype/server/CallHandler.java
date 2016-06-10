package pl.dreamteam.cc.skype.server;

import com.skype.*;
import org.activiti.engine.RuntimeService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.stereotype.Component;
import pl.dreamteam.cc.model.Caller;
import pl.dreamteam.cc.service.MessageService;
import pl.dreamteam.cc.service.repository.CallerRepository;

import javax.annotation.PostConstruct;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

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
        activitiFacade.startKolejkaGlownaProcess(receivedCall.getPartnerId());
    }

    public void onMessage(ChatMessage received) throws SkypeException {
        LOGGER.info("RECEIVED FROM: " + received.getSenderDisplayName() + "[" + received.getSenderId() + "] MSG: " + received.getContent());

        if (received.getType().equals(ChatMessage.Type.SAID)) {
            messageService.translate(received.getSenderId(), received.getContent());
        }  else {
            LOGGER.info(received.getType());
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
