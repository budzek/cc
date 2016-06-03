package pl.dreamteam.cc.client;

import pl.dreamteam.cc.service.ConsultantService;

/**
 * Created by abu on 03.06.2016.
 */
public class ServiceFactory {

    private ConsultantService consultantService;

    public void setConsultantService(ConsultantService consultantService) {
        this.consultantService = consultantService;
    }

    public ConsultantService getConsultantService() {
        return consultantService;
    }

// additional methods using the ConsultantService

}