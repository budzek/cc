package pl.dreamteam.cc.skype.server;

import com.skype.*;
import org.apache.commons.lang3.StringUtils;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

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

    protected final static String VB_AUDIO = "VB-Audio";
    protected final static String PORT = "Port";
    protected final static String PC_OUT = "Port CABLE Output";
    protected final static String PC_IN = "Port CABLE Input";
    protected final static String C_IN = "CABLE Input";
    protected final static String C_OUT = "CABLE Output";
    protected final static String[] C_ALL = {PC_OUT, PC_IN, C_IN, C_OUT};

    public static Mixer.Info findVBCableInput() {
        return Arrays.stream(AudioSystem.getMixerInfo()).
//                filter(i -> i.getName() != null && i.getName().contains(VB_AUDIO) && StringUtils.indexOfAny(i.getName(), C_ALL) != -1).
                filter(i -> i.getName() != null && i.getName().contains(VB_AUDIO) && i.getName().contains(C_IN) && !i.getName().contains(PORT)).
                findFirst().orElseThrow(() -> new RuntimeException("Can't find VB-AUDIO input"));
    }

    public static void playSound(File file, Mixer.Info mixerInfo) throws IOException, UnsupportedAudioFileException, LineUnavailableException, InterruptedException {
        AudioInputStream sound = AudioSystem.getAudioInputStream(file);
        DataLine.Info info = new DataLine.Info(Clip.class, sound.getFormat());
        Clip cl = (Clip) AudioSystem.getMixer(mixerInfo).getLine(info);
        cl.open(sound);
        cl.start();
        Thread.sleep(1000);
    }

    public static void playSound(Mixer.Info mixerInfo) throws IOException, UnsupportedAudioFileException, LineUnavailableException, InterruptedException {
        System.out.println(AudioSystem.getMixerInfo());
        File file = new File("c:\\cc-server\\intro.wav");
        AudioInputStream sound = AudioSystem.getAudioInputStream(file);
        DataLine.Info info = new DataLine.Info(Clip.class, sound.getFormat());
        Clip cl = (Clip) AudioSystem.getMixer(mixerInfo).getLine(info);
        cl.open(sound);
        cl.start();
        Thread.sleep(1000);
    }

    public static void playSound(File file){
        try {
            playSound(findVBCableInput());
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





    public static ArrayList<Mixer.Info> findAvailableMixers(){
        ArrayList<Mixer.Info> available = new ArrayList<>();
        Arrays.stream(AudioSystem.getMixerInfo()).
                filter(i -> i.getName() != null && i.getName().contains(VB_AUDIO) && StringUtils.indexOfAny(i.getName(), C_ALL) != -1).forEach(i -> {
            try {
                playSound(i);
                available.add(i);
                System.out.println(i.getName());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (UnsupportedAudioFileException e) {
                e.printStackTrace();
            } catch (LineUnavailableException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        });

        return available;
    }




}

