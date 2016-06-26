package pl.dreamteam.cc.service;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RuntimeService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import pl.dreamteam.cc.service.repository.ServiceUserRepository;

/**
 * Created by abu on 09.06.2016.
 */

@Component
public class AuthService {

    private static final Logger LOGGER = LogManager.getLogger(AuthService.class);
    @Autowired
    ServiceUserRepository serviceUserRepository;

    public boolean checkCredentials(String login, String password){
        //TODO move filtering to db
        return serviceUserRepository.findAll().stream().anyMatch(u -> u.getLogin().equals(login) && u.getHaslo().equals(password));
    }
}
