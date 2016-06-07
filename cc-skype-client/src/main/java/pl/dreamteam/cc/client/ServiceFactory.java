package pl.dreamteam.cc.client;

import pl.dreamteam.cc.service.ConsultantService;
import com.caucho.hessian.client.HessianProxyFactory;

import java.net.MalformedURLException;

/**
 * Created by abu on 03.06.2016.
 */
public class ServiceFactory {

    public static final String URL_CONSULTANT_SERVICE = "http://localhost:8080/ConsultantService";

    private static ServiceFactory instance;
    private HessianProxyFactory factory;

    private ConsultantService consultantService;

    protected ServiceFactory() {
        factory = new HessianProxyFactory();

        try {
            consultantService = (ConsultantService) factory.create(ConsultantService.class, URL_CONSULTANT_SERVICE);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public static ServiceFactory getInstance() {
        if (instance == null)
            instance = new ServiceFactory();

        return instance;
    }
    public void setConsultantService(ConsultantService consultantService) {
        this.consultantService = consultantService;
    }

    public ConsultantService getConsultantService() {
        return consultantService;
    }


// additional methods using the ConsultantService

}