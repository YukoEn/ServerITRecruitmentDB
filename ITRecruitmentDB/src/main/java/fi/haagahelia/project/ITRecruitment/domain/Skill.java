package fi.haagahelia.project.ITRecruitment.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "skill")
public class Skill implements Serializable {
    @Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "skill_id", columnDefinition="BigInt(20)")
    private Long id;

   // @NotNull
    @Size(max = 50)
    @Column(name = "skill_name")
    private String name;
    
	//@OneToMany(fetch = FetchType.EAGER, mappedBy = "skill")
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "skill")
	private List<SkillCandidate> scandidates;

    public Skill() {
    }
    
	public Skill(String name) {
		this.name = name;
    }

	public Skill(Long id, String name) {
		this.id = id;
		this.name = name;
    }
	
	public Long getId() {
		return id;
	}

	public void setId(Long skillId) {
		this.id = skillId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<SkillCandidate> getScandidates() {
		return scandidates;
	}

	public void setScandidates(List<SkillCandidate> scandidates) {
		this.scandidates = scandidates;
	}

}

