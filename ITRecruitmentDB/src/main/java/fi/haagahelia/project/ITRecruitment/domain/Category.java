package fi.haagahelia.project.ITRecruitment.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Entity
@Table(name = "category")
public class Category implements Serializable {
    @Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "category_id", columnDefinition="BigInt(20)")
    private Long id;
    
    @Size(max = 50)
    @Column(name = "category_name")
    private String name;
    
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "category")
	private List<Candidate> candidates;
    
    public Category() {

    }

	public Category(String name) {
		this.name = name;
    }
    
    
	public Category(Long id, String name) {
		this.id = id;
		this.name = name;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long categoryId) {
		this.id = categoryId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Candidate> getCandidates() {
		return candidates;
	}

	public void setCandidates(List<Candidate> candidates) {
		this.candidates = candidates;
	}
    
}
