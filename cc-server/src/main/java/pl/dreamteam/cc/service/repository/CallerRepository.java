package pl.dreamteam.cc.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.dreamteam.cc.model.Applicant;
import pl.dreamteam.cc.model.Caller;

@Repository
public interface CallerRepository extends JpaRepository<Caller, Long> {

}