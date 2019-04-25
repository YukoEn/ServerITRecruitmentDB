package fi.haagahelia.project.ITRecruitment.repository;

import fi.haagahelia.project.ITRecruitment.domain.SkillCandidate;
import fi.haagahelia.project.ITRecruitment.domain.SkillCandidateId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;


@RepositoryRestResource
public interface SCRepository extends JpaRepository<SkillCandidate, SkillCandidateId> {

	List<SkillCandidate> findByCandidateId(Long candidateId);
	
}