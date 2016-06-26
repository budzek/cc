package pl.dreamteam.cc.service;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import pl.dreamteam.cc.model.VARS;

import javax.annotation.PostConstruct;

/**
 * Created by abu on 09.06.2016.
 */

@Component
public class LogowanieDelegate implements JavaDelegate {

    @Autowired
    private AuthService authService;

    @PostConstruct
    public void init() {
        System.out.println("Constructed LogowanieDelegate");
    }

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        Integer logowanieProba = execution.getVariable(VARS.LOGOWANIE_PROBA.name(), Integer.class);
        if(logowanieProba == null)
            execution.setVariable(VARS.LOGOWANIE_PROBA.name(), Integer.valueOf(1));
        else
            execution.setVariable(VARS.LOGOWANIE_PROBA.name(), logowanieProba+1);

        execution.setVariable(VARS.LOGOWANE_DANE_POPRAWNE.name(), authService.checkCredentials(execution.getVariable(VARS.LOGIN.name(), String.class), execution.getVariable(VARS.HASLO.name(), String.class)));
//        if(authService.checkCredentials(execution.getVariable(VARS.LOGIN.name(), String.class), execution.getVariable(VARS.HASLO.name(), String.class))){
//            execution.setVariable(VARS.LOGOWANE_DANE_POPRAWNE.name(), true);
//
//        } else {
//            execution.setVariable(VARS.LOGOWANE_DANE_POPRAWNE.name(), false);
//        }

        System.out.println("login done");
    }
}
