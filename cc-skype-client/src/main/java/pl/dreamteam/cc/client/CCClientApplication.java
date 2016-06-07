package pl.dreamteam.cc.client;

import pl.dreamteam.cc.dto.Status;

/**
 * Created by abu on 03.06.2016.
 */

public class CCClientApplication {

    public static void main(String[] args) {
        ServiceFactory.getInstance().getConsultantService().setStatus("LOL", Status.OFFLINE);
    }

}
