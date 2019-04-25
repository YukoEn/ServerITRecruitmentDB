package fi.haagahelia.project.ITRecruitment.repository;

import fi.haagahelia.project.ITRecruitment.domain.Category;

import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;


public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findByName(String categoryName);
	
}