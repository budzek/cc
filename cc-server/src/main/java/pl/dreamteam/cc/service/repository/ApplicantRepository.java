package pl.dreamteam.cc.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.dreamteam.cc.model.Applicant;

@Repository
public interface ApplicantRepository extends JpaRepository<Applicant, Long> {

}