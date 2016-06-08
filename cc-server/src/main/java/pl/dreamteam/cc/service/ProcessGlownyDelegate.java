package pl.dreamteam.cc.service;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by abu on 08.06.2016.
 */


@Component
//@Component
public class ProcessGlownyDelegate implements JavaDelegate {

//    @Autowired
//    @Qualifier("myOtherSpringBean")
//    private MyOtherSpringBean otherSpringBean;

    @PostConstruct
    public void init() {
        System.out.println("Constructed ProcessGlownyDelegate");
    }

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        System.out.println("IM reading phrase");
    }
}