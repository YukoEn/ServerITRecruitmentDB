package fi.haagahelia.project.ITRecruitment.domain;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class SkillCandidateId implements Serializable {
    
	@NotNull
    private long skillId;
	
    @NotNull
    private long candidateId;


    public SkillCandidateId() {

    }

    public SkillCandidateId(Long skillId, Long candidateId) {
        this.skillId = skillId;
        this.candidateId = candidateId;
    }

    public long getSkillId() {
		return skillId;
	}

	public void setSkillId(long skillId) {
		this.skillId = skillId;
	}

	public long getCandidateId() {
		return candidateId;
	}

	public void setCandidateId(long candidateId) {
		this.candidateId = candidateId;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SkillCandidateId that = (SkillCandidateId) o;

        if (skillId != (that.skillId)) return false;
        return candidateId == that.candidateId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(skillId, candidateId);
    }

    @Override
    public String toString() {
        return "skillCandidateId{" +
                "skillId='" + skillId + '\'' +
                ", candidateId='" + candidateId + '\'' +
                '}';
    }
}