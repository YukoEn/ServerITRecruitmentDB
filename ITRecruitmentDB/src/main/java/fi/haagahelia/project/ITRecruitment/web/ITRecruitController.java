package fi.haagahelia.project.ITRecruitment.web;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import fi.haagahelia.project.ITRecruitment.domain.Candidate;
import fi.haagahelia.project.ITRecruitment.domain.SkillCandidate;
import fi.haagahelia.project.ITRecruitment.domain.SkillCandidateId;
import fi.haagahelia.project.ITRecruitment.domain.SkillSet;
import fi.haagahelia.project.ITRecruitment.domain.Category;
import fi.haagahelia.project.ITRecruitment.web.CandidateRowMapper;
import fi.haagahelia.project.ITRecruitment.domain.CandidateForm;


@Controller
public class ITRecruitController {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
	// Go to login page
    @RequestMapping(value="/login")
    public String login() {	
        return "login";
    }

    @RequestMapping(value="/candidatelist")
    public String candidatetList(Model model) {	
        // Fetch all candidates
        List<Map<String, Object>> candidates = jdbcTemplate.queryForList("SELECT candidate_id, first_name, last_name, streetaddress, postcode, postoffice, country, phone, email, category_id FROM candidate");
        
        // Add candidatelist to model and return to view
        model.addAttribute("candidates", candidates);
        List<Category> categoryList = new ArrayList<Category>();    	
        Long catId;
        String catName;
        
        for (int i = 0; i < candidates.size(); i++) {
        	catId = ((Long) candidates.get(i).get("category_id")).longValue();;
            catName = jdbcTemplate.queryForObject("SELECT category_name FROM category WHERE category_id = " + catId, String.class); 
            categoryList.add(new Category(catId, catName));
        }

        model.addAttribute("caregoryList", categoryList);
        return "candidatelist";
    }
    
    // Add new candidate with form validation
    @RequestMapping(value = "/add")
    public String addCandidate(Model model){
    	//
    	model.addAttribute("candidateForm", new CandidateForm());
    	List<Map<String, Object>> categories = jdbcTemplate.queryForList("SELECT category_id, category_name FROM category");
    	model.addAttribute("categories", categories);
        return "addcandidate";
    }
    
 // Save new candidate and continue
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String saveCandidate(@Valid @ModelAttribute("candidateForm") CandidateForm candidateForm, BindingResult bindingResult, Model model){
    	
        if (bindingResult.hasErrors()) {
        	List<Map<String, Object>> categories = jdbcTemplate.queryForList("SELECT category_id, category_name FROM category");
        	model.addAttribute("categories", categories);
            return "addcandidate";
        }
    	
    	jdbcTemplate.update("INSERT IGNORE INTO candidate(first_name, last_name, streetaddress, postcode, postoffice, country, phone, email, category_id) "
        		+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)", candidateForm.getFirstName(), candidateForm.getLastName(), candidateForm.getStreetaddress(), 
        		candidateForm.getPostcode(), candidateForm.getPostoffice(), candidateForm.getCountry(), candidateForm.getPhone(), candidateForm.getEmail(), candidateForm.getCategory().getId());

    	List<Map<String, Object>> skills = jdbcTemplate.queryForList("SELECT skill_id, skill_name FROM skill");
    	model.addAttribute("skills", skills);
        Long id = jdbcTemplate.queryForObject("SELECT candidate_id FROM candidate WHERE first_name = '" + candidateForm.getFirstName() + "' AND last_name = '" 
    			+ candidateForm.getLastName() + "'", Long.class);
    	int numberOfRows = jdbcTemplate.queryForObject("SELECT count(*) FROM skill", Integer.class);
    	SkillSet skillsForm = new SkillSet();
        for ( int i = 0; i < numberOfRows; i++) {
        	SkillCandidateId scIdObj =  new SkillCandidateId();
        	scIdObj.setSkillId(((Long) skills.get(i).get("skill_id")).longValue());
        	scIdObj.setCandidateId(id);        	
        	skillsForm.addItem(new SkillCandidate(scIdObj, ""));       	
        }
    	model.addAttribute("skillset", skillsForm);
        return "addskill";
    }
    
    // Cancel to add new candidate
    @RequestMapping(value = "/canceladd")
    public String cancelAdd(){
        return "redirect:candidatelist";
    }
    
    // Save skill
    @RequestMapping(value = "/saveskill", method = RequestMethod.POST)
    public String save(SkillSet skillset, Model model){
    	List<SkillCandidate> li = skillset.getSkillitems();
        for (int i = 0; i < li.size(); i++) {
        	jdbcTemplate.update("INSERT INTO skill_candidate(skill_id, candidate_id, skill_level) "
            		+ "values (?, ?, ?)", li.get(i).getSkillCandidateId().getSkillId(), li.get(i).getSkillCandidateId().getCandidateId(), li.get(i).getSkillLevel());
        }
        Long cId = li.get(0).getSkillCandidateId().getCandidateId();
        
        // Fetch the candidate
        String sql = "SELECT * FROM candidate WHERE candidate_id = " + cId;
        Candidate candidate = jdbcTemplate.queryForObject(sql, new CandidateRowMapper());
    	model.addAttribute("candidateUpload", candidate);
        return "upload";
    }

    // Delete candidate
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('ADMIN')")
    public String deleteCandidate(@PathVariable("id") Long candidateId, Model model) {
        jdbcTemplate.update("DELETE FROM file_model WHERE candidate_id = ?", candidateId);
        jdbcTemplate.update("DELETE FROM skill_candidate WHERE candidate_id = ?", candidateId);
        jdbcTemplate.update("DELETE FROM candidate WHERE candidate_id = ?", candidateId);
        return "redirect:../candidatelist";
    }
    
    // Edit candidate with form validation
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String editCandidate(@PathVariable("id") Long candidateId, Model model) {
        String sql = "SELECT * FROM candidate WHERE candidate_id = " + candidateId ;
        Candidate candidate = jdbcTemplate.queryForObject(sql, new CandidateRowMapper());
        CandidateForm candidateFormF = new CandidateForm();
        candidateFormF.setId(candidateId);
        candidateFormF.setFirstName(candidate.getFirstName());
        candidateFormF.setLastName(candidate.getLastName());
        candidateFormF.setStreetaddress(candidate.getStreetaddress());
        candidateFormF.setPostoffice(candidate.getPostcode());
        candidateFormF.setCountry(candidate.getCountry());
        candidateFormF.setPhone(candidate.getPhone());
        candidateFormF.setEmail(candidate.getEmail());
		Category category = new Category();
		category.setId(candidate.getCategory().getId());
        candidateFormF.setCategory(category);
        
    	model.addAttribute("candidateFormF", candidateFormF);
    	List<Map<String, Object>> categories = jdbcTemplate.queryForList("SELECT category_id, category_name FROM category");
    	model.addAttribute("categories", categories);
    	Long id = jdbcTemplate.queryForObject("SELECT category_id FROM candidate WHERE candidate_id = " + candidateId, Long.class);
    	String categoryName = jdbcTemplate.queryForObject("SELECT category_name FROM category WHERE category_id = " + id, String.class);
    	model.addAttribute("categoryName", categoryName);
    	return "editcandidate"; 
    }
    
    // Cancel to edit candidate
    @RequestMapping(value = "/canceledit")
    public String cancelEdit(){
        return "redirect:candidatelist";
    }
    
    // Edit candidate skill
    @RequestMapping(value = "/editskill", method = RequestMethod.POST)
    public String editSkill(@Valid @ModelAttribute("candidateFormF") CandidateForm candidateFormF, BindingResult bindingResult, Model model){
    	
        if (bindingResult.hasErrors()) {
        	List<Map<String, Object>> categories = jdbcTemplate.queryForList("SELECT category_id, category_name FROM category");
        	model.addAttribute("categories", categories);
            return "editcandidate";
        }

        Long id = candidateFormF.getId();
    	String query = "UPDATE candidate SET first_name = ?, last_name = ?, streetaddress = ?, postcode = ?, postoffice = ?, country = ?, "
    			+ "phone = ?, email = ?, category_id = ? WHERE candidate_id = ?";
    	
        Object[] params = {candidateFormF.getFirstName(), candidateFormF.getLastName(), candidateFormF.getStreetaddress(), candidateFormF.getPostcode(),
        		candidateFormF.getPostoffice(), candidateFormF.getCountry(), candidateFormF.getPhone(), candidateFormF.getEmail(), candidateFormF.getCategory().getId(), id};
        jdbcTemplate.update(query, params);
    	
    	int numberOfRows = jdbcTemplate.queryForObject("SELECT count(*) FROM skill", Integer.class);
        
    	String sqlskill= "SELECT s.skill_id, s.skill_name, sc.candidate_id, sc.skill_level FROM skill s inner join skill_candidate sc on s.skill_id = sc.skill_id WHERE sc.candidate_id = ? order by s.skill_id";
    	List<Map<String, Object>> skillsEdit = jdbcTemplate.queryForList(sqlskill, id);

    	SkillSet skillsForm = new SkillSet();
        for ( int i = 0; i < numberOfRows; i++) {
        	Long sId = ((Long) skillsEdit.get(i).get("skill_id")).longValue();
        	String sLevel = (String) skillsEdit.get(i).get("skill_level");
        	SkillCandidateId scObj = new SkillCandidateId();
        	scObj.setSkillId(sId);
        	scObj.setCandidateId(id);        	
        	skillsForm.addItem(new SkillCandidate(scObj, sLevel));
        }
    	model.addAttribute("skillset", skillsForm);
    	model.addAttribute("skillsEdit", skillsEdit);    	
    	model.addAttribute("cId", id);

    	String[] evaluation = new String[5];
    	evaluation[0] = "excellent";
    	evaluation[1] = "good";
    	evaluation[2] = "satisfactory";
    	evaluation[3] = "some experience";
    	evaluation[4] = "no experience";
    	model.addAttribute("evals", evaluation);

    	return "editskill"; 
    }
  
    // Cancel to edit new candidate and skill
    @RequestMapping(value = "/canceleditskill/{id}", method = RequestMethod.GET)
    public String cancelEditSkill(@PathVariable("id") Long cId){
    	jdbcTemplate.update("DELETE FROM candidate WHERE candidate_id = ?", cId);
    	return "redirect:../candidatelist";
    }
    
    // Update skill
    @RequestMapping(value = "/update", method = {RequestMethod.POST})
    public String update(SkillSet skillset, Model model, RedirectAttributes redirectAttributes){
    	List<SkillCandidate> li = skillset.getSkillitems();
        Long id = li.get(0).getSkillCandidateId().getCandidateId();
        
        String query = "UPDATE skill_candidate SET skill_level = ? WHERE candidate_id = ? AND skill_id = ? ";
        for (int i = 0; i < li.size(); i++) {
            Object[] params = {li.get(i).getSkillLevel(), id, li.get(i).getSkillCandidateId().getSkillId()};
            jdbcTemplate.update(query, params);
        }
        
    	redirectAttributes.addAttribute("cId", id);
    	return "redirect:/files";
    }


}
