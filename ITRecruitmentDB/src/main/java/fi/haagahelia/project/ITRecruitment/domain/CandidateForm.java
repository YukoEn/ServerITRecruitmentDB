package fi.haagahelia.project.ITRecruitment.domain;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;;


public class CandidateForm {

	private Long id;
	
	@NotEmpty
    @Size(max=30)
	private String firstName;
    
	@NotEmpty
    @Size(max=30)
    private String lastName;

    private String streetaddress;
    
    private String postcode;

    private String postoffice;    

    private String country;

    private String phone;

	@NotEmpty
    private String email;

    private Category category;


	public CandidateForm() {}
	
	
	public CandidateForm(String firstName, String lastName, String streetaddress, String postcode, String postoffice, String country, String phone, String email, Category category) {	
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


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}


}