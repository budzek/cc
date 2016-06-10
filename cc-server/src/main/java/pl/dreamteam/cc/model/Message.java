package pl.dreamteam.cc.model;

/**
 * Created by abu on 09.06.2016.
 */
public enum Message {
    //TODO remove choice
    PROCESS_GLOWNY_LOGOWANIE(1),
    PROCESS_GLOWNY_POLACZENIE_Z_KONSULTANTEM(2),
    PROCESS_GLOWNY_POWTORZ_POWITANIE(3);

    private final int choice;

    Message(int choice) {
        this.choice = choice;
    }

    public int getChoice(){
        return choice;
    }
}
