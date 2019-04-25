package fi.haagahelia.project.ITRecruitment.domain;

import java.util.List;
import java.util.ArrayList;

public class SkillSet {

	private List<SkillCandidate> skillitems;
	
	public SkillSet() {
		skillitems = new ArrayList<>();
	}
	
	public void addItem(SkillCandidate item) {
		this.skillitems.add(item);
	}

	public List<SkillCandidate> getSkillitems() {
		return skillitems;
	}

	public void setSkillitems(List<SkillCandidate> skillitems) {
		this.skillitems = skillitems;
	}
	
}
