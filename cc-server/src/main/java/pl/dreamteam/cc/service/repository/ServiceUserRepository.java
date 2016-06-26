package pl.dreamteam.cc.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.dreamteam.cc.model.Caller;
import pl.dreamteam.cc.model.ServiceUser;

@Repository
public interface ServiceUserRepository extends JpaRepository<ServiceUser, Long> {

}