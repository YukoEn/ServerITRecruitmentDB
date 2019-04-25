package fi.haagahelia.project.ITRecruitment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import fi.haagahelia.project.ITRecruitment.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
	User findByUsername(String username);
}