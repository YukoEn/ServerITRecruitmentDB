package fi.haagahelia.project.ITRecruitment.domain;

import javax.persistence.Entity;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;

@Entity
@Table(name="file_model")
public class FileModel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Size(max = 20)
	@Column(name = "id")
	private long id;
	
	@Column(name = "file_name")
	private String fileName;
	
	@Column(name = "mime_type")
	private String mimeType;
	
	@Column(name = "base64str")
	private String base64str;

	@Lob
	@Column(name="file")
	private byte[] file;
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "candidate_id", insertable = false, updatable = false)
	private Candidate candidate;
	
	public FileModel() {}
	
	public FileModel(String fileName, String mimeType, byte[] file, Candidate candidate) {
		this.fileName = fileName;
		this.mimeType = mimeType;
		this.file = file;
		this.candidate = candidate;
	}

	public Candidate getCandidateFM() {
		return candidate;
	}

	public void setCandidateFM(Candidate candidate) {
		this.candidate = candidate;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getMimeType() {
		return mimeType;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

	public byte[] getFile() {
		return file;
	}

	public void setFile(byte[] file) {
		this.file = file;
	}


}
