package fi.haagahelia.project.ITRecruitment.domain;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.EmbeddedId;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name = "skill_candidate")
public class SkillCandidate implements Serializable {
    
    @AttributeOverrides({
        @AttributeOverride(
            name = "skillId",
            column = @Column(name = "skill_Id", columnDefinition="BigInt(20)")),
        @AttributeOverride(
            name = "candidateId",
            column = @Column(name = "candidate_Id", columnDefinition="BigInt(20)")
            )
    })
    @EmbeddedId
    private SkillCandidateId skillCandidateId;
    
    @Size(max = 50)
    @Column(name = "skill_level")
    private String skillLevel;
	
    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    @JoinColumn(name = "candidate_Id", insertable = false, updatable = false)
    private Candidate candidate;
	
    //@ManyToOne(fetch = FetchType.EAGER)
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "skill_Id", insertable = false, updatable = false)
    private Skill skill;

    
    public SkillCandidate() {}

    public SkillCandidate(SkillCandidateId skillCandidateId, String skillLevel) {
        this.skillCandidateId = skillCandidateId;
        this.skillLevel = skillLevel;
    }


	public Candidate getCandidate() {
		return candidate;
	}

	public void setCandidate(Candidate candidate) {
		this.candidate = candidate;
	}

	public Skill getSkill() {
		return skill;
	}

	public void setSkill(Skill skill) {
		this.skill = skill;
	}

	public SkillCandidateId getSkillCandidateId() {
		return skillCandidateId;
	}

	public void setSkillCandidateId(SkillCandidateId skillCandidateId) {
		this.skillCandidateId = skillCandidateId;
	}

	public String getSkillLevel() {
		return skillLevel;
	}

	public void setSkillLevel(String skillLevel) {
		this.skillLevel = skillLevel;
	}

	@Override	
	public String toString() {
		if (this.skill != null)
			return "SkillCandidate [candidate=" + this.getCandidate() + " skill =" + this.getSkill() + " skillLevel =" + skillLevel + "]";		
		else
			return "Empty dataset";
	}
	
}
