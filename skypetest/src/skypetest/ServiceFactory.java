package skypetest;

import com.caucho.hessian.client.HessianProxyFactory;
import java.net.MalformedURLException;


public class ServiceFactory {
	
	    public static final String URL_CONSULTANT_SERVICE = "http://localhost:8080/ConsultantService";
	
	    private static ServiceFactory instance;
	    private HessianProxyFactory factory;
	
	    private ConsultantService consultantService;
	
	    protected ServiceFactory() throws MalformedURLException {
	        factory = new HessianProxyFactory();
	
                consultantService = (ConsultantService) factory.create(ConsultantService.class, URL_CONSULTANT_SERVICE);
	    }
	
	    public static ServiceFactory getInstance() throws MalformedURLException {
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