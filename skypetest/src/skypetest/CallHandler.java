package skypetest;

import com.skype.Call;
import com.skype.CallAdapter;
import com.skype.ChatMessage;
import com.skype.ChatMessageAdapter;
import com.skype.NotAttachedException;
import com.skype.Profile;
import com.skype.Skype;
import com.skype.SkypeException;
import java.awt.event.ActionEvent;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;

public class CallHandler {

    inCall popup;
    //private static final Logger logger = LogManager
    //      .getLogger(CallHandler.class);

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
            public void callReceived(Call receivedCall) throws SkypeException {
                //Profile.CallForwardingRule[] oldRules = Skype.getProfile().getAllCallForwardingRules();
                System.out.println(receivedCall.getPartnerDisplayName() + " CALLIN");
                popup = new inCall();//.setVisible(true);
                popup.setVisible(true);
     
                do{

                }while(popup.accept==0);
                
                if(popup.accept==1)
                {
                    receivedCall.answer();
                }
                else
                {
                     receivedCall.cancel();   
                }
                popup.setVisible(false); //you can't see me!
                popup.dispose();
                //Skype.getProfile().setAllCallForwardingRules(new Profile.CallForwardingRule[]{new Profile.CallForwardingRule(0, 30, "echo123")});
                // receivedCall.forward();
                //try {
                //   Thread.sleep(10000); // to prevent finishing this call
                //} catch (InterruptedException e) {
                //}
                //Skype.getProfile().setAllCallForwardingRules(oldRules);
            }
        });

        /*Skype.addChatMessageListener(new ChatMessageAdapter() {
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
	        });*/
    }

}
