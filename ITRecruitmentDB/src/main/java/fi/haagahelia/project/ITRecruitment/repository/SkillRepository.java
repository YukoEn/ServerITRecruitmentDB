package fi.haagahelia.project.ITRecruitment.repository;

import fi.haagahelia.project.ITRecruitment.domain.Skill;

import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;


public interface SkillRepository extends JpaRepository<Skill, Long> {

    List<Skill> findByName(String skillName);
	
}