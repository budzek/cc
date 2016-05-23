package pl.dreamteam.cc.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.dreamteam.cc.model.Consultant;

public interface ConsultantRepository extends JpaRepository<Consultant, Long> {

}