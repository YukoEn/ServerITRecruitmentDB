package fi.haagahelia.project.ITRecruitment.web;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import fi.haagahelia.project.ITRecruitment.domain.Candidate;
import fi.haagahelia.project.ITRecruitment.domain.Category;

public class CandidateRowMapper implements RowMapper<Candidate> {

	@Override
	public Candidate mapRow(ResultSet rs, int rowNum) throws SQLException {
		Candidate candidate = new Candidate();
		candidate.setId(rs.getLong("candidate_id"));
		candidate.setFirstName(rs.getString("first_name"));
		candidate.setLastName(rs.getString("last_name"));			
		candidate.setStreetaddress(rs.getString("streetaddress"));
		candidate.setPostcode(rs.getString("postcode"));
		candidate.setPostoffice(rs.getString("postoffice"));
		candidate.setCountry(rs.getString("country"));
		candidate.setPhone(rs.getString("phone"));
		candidate.setEmail(rs.getString("email"));
		Category category = new Category();
		category.setId(rs.getLong("category_id"));
		candidate.setCategory(category);
    return candidate;
	}
}
