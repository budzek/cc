package pl.dreamteam.cc.model;

import pl.dreamteam.cc.exception.ChoiceFailureException;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * Created by abu on 09.06.2016.
 */
public enum PROCESS {
    PROCES_GLOWNY("procesGlowny", () -> {
        Map<String, Message> map = new HashMap<>();
        map.put("1", Message.PROCESS_GLOWNY_LOGOWANIE);
        map.put("2", Message.PROCESS_GLOWNY_POLACZENIE_Z_KONSULTANTEM);
        map.put("3", Message.PROCESS_GLOWNY_POWTORZ_POWITANIE);
        return map;
    });

    private final String processId;
    Map<String, Message> map;

    PROCESS(String processId, Supplier<Map<String, Message>> messageSupplier) {
        this.processId = processId;
        this.map = messageSupplier.get();
    }

    public static PROCESS get(String processId){
        return Arrays.stream(values()).filter(p -> p.processId.equals(processId)).findFirst().orElseThrow(() -> new RuntimeException("No PROCESS found for :" + processId));
    }
    
    public Message getMessage(String id){
        Message msg = map.get(id);

        if(msg == null)
            throw new ChoiceFailureException("Can't match messageId: " + id + " process: " + processId);

        return msg;
    }
}
