package pl.dreamteam.cc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.dreamteam.cc.model.Consultant;
import pl.dreamteam.cc.service.repository.ConsultantRepository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by abu on 23.05.2016.
 */

@Service
@Transactional
public class ConsultantService {

  @Autowired
  ConsultantRepository consultantRepository;

  public Consultant getFirstAvailable() throws NoConsultantAvailableException{
    List<Consultant> list = getAllAvailable();

    if(list.isEmpty())
      throw new NoConsultantAvailableException();

    return list.get(0);
  }


  //TODO move filtering to db query
  public List<Consultant> getAllAvailable(){
    return consultantRepository.findAll().stream().filter(c -> c.isLoggedIn() && !c.isTalking()).collect(Collectors.<Consultant>toList());
  }

}
