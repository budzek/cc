package pl.dreamteam.cc.skype.server;

import com.skype.*;

/**
 * Created by abu on 23.05.2016.
 */
public class SkypeUtils {

  public void forwarder() throws SkypeException {
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
