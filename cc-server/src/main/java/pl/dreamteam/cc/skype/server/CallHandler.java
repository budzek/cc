package pl.dreamteam.cc.skype.server;

import com.skype.*;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by abu on 23.05.2016.
 */

@Component
public class CallHandler implements org.springframework.context.ApplicationListener<ApplicationStartedEvent> {

    @Autowired
    ActivitiFacadeImpl activitiFacade;

    @Override
    public void onApplicationEvent(final ApplicationStartedEvent event) {

    }


    private static final Logger logger = LogManager
            .getLogger(CallHandler.class);

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
                onCall(receivedCall.getPartnerDisplayName());

//                Profile.CallForwardingRule[] oldRules = Skype.getProfile().getAllCallForwardingRules();
//                System.out.println(receivedCall.getPartnerDisplayName() + " CALLIN");
//                Skype.getProfile().setAllCallForwardingRules(new Profile.CallForwardingRule[]{new Profile.CallForwardingRule(0, 30, "echo123")});
//                receivedCall.forward();
//                try {
//                    Thread.sleep(10000); // to prevent finishing this call
//                } catch (InterruptedException e) {
//                }
//                Skype.getProfile().setAllCallForwardingRules(oldRules);
            }
        });

        Skype.addChatMessageListener(new ChatMessageAdapter() {
            public void chatMessageReceived(ChatMessage received)
                    throws SkypeException {
                if (received.getType().equals(ChatMessage.Type.SAID)) {

                    // Sender
                    // User sender =received.getSender();

                    System.out.println(received.getSender().getId() +" say:");
                    System.out.println(" "+received.getContent() );

                    received.getSender().send(
                            "I'm working. Please, wait a moment.");

                    System.out.println(" - Auto answered!");
                }
            }
        });
    }

    public void onCall(String skypeId){
//        activitiFacade.startKolejkaGlownaProcess(skypeId);
        try {
            SkypeUtils.playSound();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
