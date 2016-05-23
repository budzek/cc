package pl.dreamteam.cc.skype.server;

import com.skype.*;

import javax.annotation.PostConstruct;

/**
 * Created by abu on 23.05.2016.
 */


public class CallHandler {

//  public static void main(String[] args) throws Exception {
  @PostConstruct
  public void callHanlder() throws Exception {
    Skype.setDaemon(false);
    Skype.addCallListener(new CallAdapter() {
      @Override
      public void callReceived(Call receivedCall) throws SkypeException {
        Profile.CallForwardingRule[] oldRules = Skype.getProfile().getAllCallForwardingRules();
        Skype.getProfile().setAllCallForwardingRules(new Profile.CallForwardingRule[] { new Profile.CallForwardingRule(0, 30, "echo123") });
        receivedCall.forward();
        try {
          Thread.sleep(10000); // to prevent finishing this call
        } catch (InterruptedException e) {
        }
        Skype.getProfile().setAllCallForwardingRules(oldRules);
      }
    });
  }
}
