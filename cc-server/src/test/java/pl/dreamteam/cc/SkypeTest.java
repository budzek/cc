package pl.dreamteam.cc;

import com.skype.Call;
import com.skype.CallAdapter;
import com.skype.Skype;
import com.skype.SkypeException;
import org.junit.Test;
import pl.dreamteam.cc.skype.server.SkypeUtils;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

/**
 * Created by abu on 11.06.2016.
 */
public class SkypeTest {
    @Test
    public void testIncommingCall() {
//        callHandler.onCall("facebook:aruyig");
    }

    @Test
    public void testSkypeAudioInputOnCall() throws SkypeException {
//        findAvailableMixers();
        Skype.setDaemon(false);
        Skype.setDebug(true);
        Skype.addCallListener(new CallAdapter() {
            @Override
            public void callReceived(Call receivedCall) throws SkypeException {
                try {
                    SkypeUtils.playSound(SkypeUtils.findVBCableInput());
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
        });
    }
}
