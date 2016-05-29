package pl.dreamteam.cc.skype.server;

import com.skype.*;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by abu on 23.05.2016.
 */

@Component
public class CallHandler implements org.springframework.context.ApplicationListener<ApplicationStartedEvent> {

  @Override
  public void onApplicationEvent(final ApplicationStartedEvent event) {

  }


    private static final Logger logger = LogManager
            .getLogger(CallHandler.class);

    private ExecutorService executorService;

    @PostConstruct
    public void init() {

      BasicThreadFactory factory = new BasicThreadFactory.Builder()
              .namingPattern("myspringbean-thread-%d").build();

      executorService = Executors.newSingleThreadExecutor(factory);
      executorService.execute(new Runnable() {

        @Override
        public void run() {
          try {
            // do something
            System.out.println("thread started 0");

            callHanlder();
            System.out.println("thread started 1");
          } catch (Exception e) {
            logger.error("error: ", e);
          }
        }
      });

      executorService.shutdown();

    }

    @PreDestroy
    public void beandestroy() {
      if (executorService != null) {
        executorService.shutdownNow();
      }
    }


  public void callHanlder()  {
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
