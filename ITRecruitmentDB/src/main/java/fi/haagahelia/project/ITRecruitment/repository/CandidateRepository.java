package fi.haagahelia.project.ITRecruitment.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import fi.haagahelia.project.ITRecruitment.domain.Candidate;


@RepositoryRestResource
public interface CandidateRepository extends JpaRepository<Candidate, Long> {

    List<Candidate> findByLastName(String lastName);
    Optional<Candidate> findOneByFirstName(String firstName);
    //Candidate findOne(Long id);
    List<Candidate> findByFirstName(@Param("firstName") String firstName);
    List<Candidate> findByEmail(@Param("email") String email);
	
}
