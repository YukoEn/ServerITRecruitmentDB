package fi.haagahelia.project.ITRecruitment.domain;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name = "candidate")
public class Candidate implements Serializable {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    @NotNull
    @Column(name = "candidate_id", columnDefinition="BigInt(20)")
	private long id;
	
    @Size(max = 50)
    @Column(name = "first_name")
	private String firstName;
    
    @Size(max = 50)
    @Column(name = "last_name")
    private String lastName;
 
    @Size(max = 50)
    @Column(name = "streetaddress")
    private String streetaddress;
    
    @Column(name = "postcode", columnDefinition="char(5)")
    private String postcode;
    
    @Size(max = 50)
    @Column(name = "postoffice")
    private String postoffice;    
    
    @Size(max = 50)
    @Column(name = "country")
    private String country;
    
    @Column(name = "phone", columnDefinition="char(20)")
    private String phone;
    
    @Size(max = 50)
    @Column(name = "email")
    private String email;
    
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "candidate")
    //@JsonIgnore
	private List<SkillCandidate> scandidates;
	
	@OneToOne(fetch = FetchType.EAGER, mappedBy = "candidate")
    @JsonIgnore
    @JoinColumn(name = "fileModel_id", insertable = false, updatable = false)
	private FileModel fileModel;
	
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "category_id", insertable = false, updatable = false)
    //@JoinColumn(name = "categoryId")
    private Category category;
    


	public Candidate() {}
	
	public Candidate(long id, String firstName, String lastName, String streetaddress, String postcode, String postoffice, String country, String phone, String email, Category category) {
	this.id = id;
	this.firstName = firstName;
    this.lastName = lastName;
    this.streetaddress = streetaddress;
    this.postcode = postcode;
    this.postoffice = postoffice;
    this.country = country;
    this.phone = phone;
    this.email = email;
	this.category = category;
}
	

	//public Candidate(long id, String firstName, String lastName, String streetaddress, String postcode, String postoffice, String country, String phone, String email, Category category) {
		//this.id = id;
	public Candidate(String firstName, String lastName, String streetaddress, String postcode, String postoffice, String country, String phone, String email, Category category) {	
		this.firstName = firstName;
        this.lastName = lastName;
        this.streetaddress = streetaddress;
        this.postcode = postcode;
        this.postoffice = postoffice;
        this.country = country;
        this.phone = phone;
        this.email = email;
		this.category = category;
	}

	//public Candidate(long id, String firstName, String lastName, String streetaddress, String postcode, String postoffice, String country, String phone, String email, Category category, FileModel fileModel) {
		//this.id = id;
		public Candidate(String firstName, String lastName, String streetaddress, String postcode, String postoffice, String country, String phone, String email, Category category, FileModel fileModel) {	
		this.firstName = firstName;
        this.lastName = lastName;
        this.streetaddress = streetaddress;
        this.postcode = postcode;
        this.postoffice = postoffice;
        this.country = country;
        this.phone = phone;
        this.email = email;
		this.category = category;
		this.fileModel = fileModel;
	}


	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getStreetaddress() {
		return streetaddress;
	}

	public void setStreetaddress(String streetaddress) {
		this.streetaddress = streetaddress;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public String getPostoffice() {
		return postoffice;
	}

	public void setPostoffice(String postoffice) {
		this.postoffice = postoffice;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<SkillCandidate> getScandidates() {
		return scandidates;
	}

	public void setScandidates(List<SkillCandidate> scandidates) {
		this.scandidates = scandidates;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
	

	public FileModel getFileModel() {
		return fileModel;
	}

	public void setFileModel(FileModel fileModel) {
		this.fileModel = fileModel;
	}

	@Override
    public String toString() {
        return String.format(
                "Candidate[id=%d, firstName='%s', lastName='%s', streetaddress='%s', postcode='%s', postoffice='%s', country='%s' phone='%s', email='%s', category=%d, , fileModel=%d ]",
                id, firstName, lastName, streetaddress, postcode, postoffice, country, phone, email, category, fileModel);
    }



}