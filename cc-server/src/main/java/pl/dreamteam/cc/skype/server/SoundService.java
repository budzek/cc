package pl.dreamteam.cc.skype.server;

import org.springframework.stereotype.Component;

import java.io.File;

/**
 * Created by abu on 09.06.2016.
 */
@Component
public class SoundService {

    public static void playWelcomePhrase(){
        SkypeUtils.playSound(new File("c:\\cc-server\\audio\\intro.wav"));
    }
}
