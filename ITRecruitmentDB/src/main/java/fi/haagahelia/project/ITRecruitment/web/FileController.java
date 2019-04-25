package fi.haagahelia.project.ITRecruitment.web;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import fi.haagahelia.project.ITRecruitment.domain.Candidate;
import fi.haagahelia.project.ITRecruitment.domain.FileModel;
import fi.haagahelia.project.ITRecruitment.repository.FileModelRepository;
import fi.haagahelia.project.ITRecruitment.web.CandidateRowMapper;

@Controller
public class FileController {
	@Autowired
	private FileModelRepository repository;
	
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Value("${upload.path}")
    private String uploadFolder;


	// Save new file
    @PostMapping("/upload")
    public String fileUpload(@RequestParam("file") MultipartFile file, @ModelAttribute("candidateUpload") Candidate cUpload, Model model, RedirectAttributes redirectAttributes) {
    	// Image Base64.getEncoder().encodeToString(file.file)
    	// <img  th:src="@{'data:image/jpeg;base64,'+${file.file}}" />
    	
        if (file.isEmpty()) {
        	model.addAttribute("msg", "Upload failed");
        	model.addAttribute("cId", cUpload.getId());
            return "uploadstatus";
        }

        try {
            //FileModel fileModel = new FileModel(file.getOriginalFilename(), file.getContentType(), file.getBytes(), cUpload);
            //repository.save(fileModel);
        	jdbcTemplate.update("INSERT IGNORE INTO file_model(file_name, mime_type, file, candidate_id) "
            		+ "VALUES (?, ?, ?, ?)", file.getOriginalFilename(), file.getContentType(), file.getBytes(), cUpload.getId());
        	redirectAttributes.addAttribute("cId", cUpload.getId());
    
            return "redirect:/files";
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    	model.addAttribute("cId", cUpload.getId());
        return "uploadstatus";
    }
    
    @RequestMapping(value = "/back/{id}", method = RequestMethod.GET)
    public String passId(@PathVariable("id") Long cId, Model model, RedirectAttributes redirectAttributes) {
    	redirectAttributes.addAttribute("candidateId", cId);
        return "redirect:../pass";
    }
    
	// Fetch candidate and pass to upload.html
    @GetMapping("/pass")
    public String passCandidate(@ModelAttribute("candidateId") Long cId, Model model) {
        String sql = "SELECT * FROM candidate WHERE candidate_id = " + cId;
    	Candidate candidate = jdbcTemplate.queryForObject(sql, new CandidateRowMapper());
    	model.addAttribute("candidateUpload", candidate);
    	return "upload";
    }
    
	// Fetch the files for the candidate of interest
    @GetMapping("/files")
    public String fileList(@ModelAttribute("cId") Long candidateId, Model model) {
    	List<Map<String, Object>> files = jdbcTemplate.queryForList("SELECT id, file_name, mime_type, candidate_id FROM file_model WHERE candidate_id = " + candidateId);
    	model.addAttribute("files", files);
    	model.addAttribute("cId", candidateId);
    	
    	//model.addAttribute("files", repository.findAll());  	
    	return "filelist";
    }
    
	// Download the uploaded file
	@GetMapping("/file/{id}")
	public ResponseEntity<byte[]> getFile(@PathVariable Long id) {
		Optional<FileModel> fileOptional = repository.findById(id);
		
		if(fileOptional.isPresent()) {
			FileModel file = fileOptional.get();
			return ResponseEntity.ok()
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFileName() + "\"")
					.body(file.getFile());	
		}
		
		return ResponseEntity.status(404).body(null);
	}

	// Delete the uploaded file
	@GetMapping("/deletefile/{id}/{cid}")
	public String deleteFile(@PathVariable("id") Long id, @PathVariable("cid") Long cId, RedirectAttributes redirectAttributes ) {
		//repository.deleteById(id);
        jdbcTemplate.update("DELETE FROM file_model WHERE id = ? AND candidate_id = ?", id, cId);
    	redirectAttributes.addAttribute("cId", cId);
        
        return "redirect:../../files";
	}
	
    // Finish candidate skill
    @RequestMapping(value = "/finish")
    public String finish(){
        return "redirect:candidatelist";
    }
    
}