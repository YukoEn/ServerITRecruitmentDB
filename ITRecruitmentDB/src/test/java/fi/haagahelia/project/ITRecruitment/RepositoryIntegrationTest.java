package fi.haagahelia.project.ITRecruitment;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;


import fi.haagahelia.project.ITRecruitment.domain.Candidate;
import fi.haagahelia.project.ITRecruitment.domain.Category;
import fi.haagahelia.project.ITRecruitment.domain.Skill;
import fi.haagahelia.project.ITRecruitment.domain.User;
import fi.haagahelia.project.ITRecruitment.domain.SkillCandidate;
import fi.haagahelia.project.ITRecruitment.domain.SkillCandidateId;
import fi.haagahelia.project.ITRecruitment.repository.CandidateRepository;
import fi.haagahelia.project.ITRecruitment.repository.CategoryRepository;
import fi.haagahelia.project.ITRecruitment.repository.UserRepository;
import fi.haagahelia.project.ITRecruitment.repository.SCRepository;
import fi.haagahelia.project.ITRecruitment.repository.SkillRepository;


@RunWith(SpringRunner.class)
@DataJpaTest
public class RepositoryIntegrationTest {
	
    private final String USER_NAME_YUKO = "Yuko";
	
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CandidateRepository repository;
    
    @Autowired
    private CategoryRepository crepository;
    
    @Autowired
    private SkillRepository srepository;
    
    @Autowired
    private UserRepository urepository;
    
    @Autowired
    private SCRepository screpository;
    
    
    @Test
    public void givenEmptyDBWhenFindOneByNameThenReturnEmptyOptional() {
        Optional<Candidate> foundCandidate = repository.findOneByFirstName(USER_NAME_YUKO);

        assertThat(foundCandidate.isPresent()).isEqualTo(false);
    }
    

    @Test
    public void userFindAll() {
        User user = dummyUser();
        entityManager.persist(user);
        entityManager.flush();
        List<User> userList = urepository.findAll();

        assertThat(userList).hasSize(1);
        assertThat(userList.contains(user));
    }

    public static User dummyUser() {
        User user = new User();
        user.setUsername("user");
        user.setPasswordHash("$2a$06$3jYRJrg0ghaaypjZ/.g4SethoeA51ph3UD4kZi9oPkeMTpjKU5uo6");
        user.setRole("USER");
        return user;
    }


    @Test
    public void categoryFindAll() {
        Category category = dummyCategory();
        entityManager.persist(category);
        entityManager.flush();
        List<Category> categoryList = crepository.findAll();

        assertThat(categoryList).hasSize(1);
        assertThat(categoryList.get(0).getId()).isNotNull();
        assertThat(categoryList.get(0).getName()).isEqualTo("Validation/ QA Engineer");
    }

    public static Category dummyCategory() {
        Category category = new Category();
        category.setName("Validation/ QA Engineer");
        return category;
    }


    @Test
    public void skillFindAll() {
        Skill skill = dummySkill();
        entityManager.persist(skill);
        entityManager.flush();
        List<Skill> skillList = srepository.findAll();

        assertThat(skillList).hasSize(1);
        assertThat(skillList.get(0).getId()).isNotNull();
        assertThat(skillList.get(0).getName()).isEqualTo("Agile/ Scrum skills");
    }

    public static Skill dummySkill() {
        Skill skill = new Skill();
        skill.setName("Agile/ Scrum skills");
        return skill;
    }


    @Test
    public void candidateFindAll() {
        Candidate candidate = dummyCandidate();
        entityManager.persist(candidate);
        entityManager.flush();
        List<Candidate> candidateList = repository.findAll();

        assertThat(candidateList.get(0).getId()).isNotNull();
        assertThat(candidateList).hasSize(1);
        assertThat(candidateList.get(0).getFirstName()).isEqualTo("Emma");
        assertThat(candidateList.get(0).getScandidates()).hasSize(1);
    }

    public Candidate dummyCandidate() {
        Category category = new Category();
        category.setName("Cloud Computing Engineer");
        entityManager.persist(category);
        entityManager.flush();
        List<Category> categoryList = crepository.findAll();

    	Candidate candidate = new Candidate();
    	candidate.setFirstName("Emma");
    	candidate.setLastName("King");
    	candidate.setStreetaddress("Asematie 13");
    	candidate.setPostcode("00130");
    	candidate.setPostoffice("Vantaa");
    	candidate.setCountry("Finland");
    	candidate.setPhone("0519860");
    	candidate.setEmail("emma@vantaa.fi");
    	candidate.setCategory(new Category((Long) categoryList.get(0).getId(), categoryList.get(0).getName()));
    	List<SkillCandidate> scandidates = new ArrayList<SkillCandidate>();
    	SkillCandidateId scId = new SkillCandidateId(Long.valueOf(1), Long.valueOf(1));
    	SkillCandidate sc = new SkillCandidate(scId, "good");
    	scandidates.add(sc);
    	candidate.setScandidates(scandidates);
        return candidate;
    }
    
   
    @After
    public void cleanUp() {
        repository.deleteAll();
        crepository.deleteAll();
        srepository.deleteAll();
        urepository.deleteAll();
    }
    
    
    /*
    @Test
    public void scFindAll() {
    	SkillCandidate sc = dummySc();
        entityManager.persist(sc);
        entityManager.flush();

        List<SkillCandidate> scList = screpository.findAll();;
        assertThat(scList.get(0).getSkillCandidateId().getCandidateId()).isNotNull();

    }
    
    public SkillCandidate dummySc() {
    	Category categorySC = new Category("Web Developer");
        entityManager.persist(categorySC);
        entityManager.flush();
        List<Category> catList = crepository.findAll();
        Candidate candidateSC = new Candidate("Emma", "King", "Asematie 13", "00130", "Vantaa", "Finland", "0519860", "emma@vantaa.fi", new Category((Long) catList.get(0).getId(), catList.get(0).getName()));
    	entityManager.persist(candidateSC);
        entityManager.flush();
        Skill skillSC = new Skill();
        skillSC.setName("Software development skills");
        entityManager.persist(skillSC);
        entityManager.flush();
        List<Candidate> cList= repository.findAll();
        List<Skill> sList = srepository.findAll();
        SkillCandidate sc = new SkillCandidate(new SkillCandidateId((Long) cList.get(0).getId(), (Long) sList.get(0).getId()), "good");
        return sc;
    }
	*/


}