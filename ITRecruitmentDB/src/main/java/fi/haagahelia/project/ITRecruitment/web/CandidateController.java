package fi.haagahelia.project.ITRecruitment.web;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import fi.haagahelia.project.ITRecruitment.repository.CandidateRepository;

import fi.haagahelia.project.ITRecruitment.domain.Candidate;
import fi.haagahelia.project.ITRecruitment.domain.SkillCandidate;
import fi.haagahelia.project.ITRecruitment.domain.SkillCandidateId;
import fi.haagahelia.project.ITRecruitment.domain.SkillSet;
import fi.haagahelia.project.ITRecruitment.domain.Category;


@RestController
public class CandidateController {

	@Autowired
	private CandidateRepository repository;
	
	//RESTful service to get all candidates 
	@GetMapping(value = "/candidate")
    public @ResponseBody List<Candidate> candidateFindAll() {
		return (List<Candidate>) repository.findAll();
	}
	
	//RESTful service to get the candidate by id  
	@GetMapping(value = "/candidate/{id}")
    public @ResponseBody Optional<Candidate> candidatefindId(@PathVariable("id") Long id) {		
		return repository.findById(id);
	}

}
