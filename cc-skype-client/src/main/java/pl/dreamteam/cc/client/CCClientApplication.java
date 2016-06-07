package pl.dreamteam.cc.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ImportResource;
import pl.dreamteam.cc.dto.Status;

/**
 * Created by abu on 03.06.2016.
 */
@SpringBootApplication

@ImportResource("classpath:/spring/spring-config.xml")

public class CCClientApplication {
//    @Autowired
//    ServiceFactory serviceFactory;


    public static void main(String[] args) {
        ServiceFactory.getInstance().getConsultantService().setStatus("LOL", Status.OFFLINE);


    }

}
