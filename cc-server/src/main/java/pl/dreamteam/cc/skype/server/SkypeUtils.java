package pl.dreamteam.cc.skype.server;

import com.skype.*;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

import static org.apache.coyote.http11.Constants.a;

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
                Skype.getProfile().setAllCallForwardingRules(new Profile.CallForwardingRule[]{new Profile.CallForwardingRule(0, 30, "echo123")});
                receivedCall.forward();
                try {
                    Thread.sleep(10000); // to prevent finishing this call
                } catch (InterruptedException e) {
                }
                Skype.getProfile().setAllCallForwardingRules(oldRules);
            }
        });
    }

    public static void playSound() throws IOException, UnsupportedAudioFileException, LineUnavailableException, InterruptedException {
        System.out.println(AudioSystem.getMixerInfo());
//                AudioSystem.getMixerInfo());
//                for(Mixer.Info a : AudioSystem.getMixerInfo()){
//                    AudioSystem.isLineSupported(a);
//                }


        File file = new File("c:\\cc-server\\intro.wav");
        AudioInputStream sound = AudioSystem.getAudioInputStream(file);
        DataLine.Info info = new DataLine.Info(Clip.class, sound.getFormat());
        Clip cl = (Clip) AudioSystem.getMixer(AudioSystem.getMixerInfo()[3]).getLine(info);
        cl.open(sound);
        cl.start();
        Thread.sleep(1000);
    }

    public static void main(String[] args) throws Exception {
        playSound();

        Skype.setDaemon(false);
        Skype.setDebug(true);
        Skype.addCallListener(new CallAdapter() {
            @Override
            public void callReceived(Call receivedCall) throws SkypeException {
                try {
                    playSound();
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

