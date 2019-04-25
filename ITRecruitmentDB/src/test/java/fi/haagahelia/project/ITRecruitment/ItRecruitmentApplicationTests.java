package fi.haagahelia.project.ITRecruitment;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.ui.Model;

import fi.haagahelia.project.ITRecruitment.web.ITRecruitController;
import fi.haagahelia.project.ITRecruitment.web.FileController;
import fi.haagahelia.project.ITRecruitment.web.CandidateController;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ItRecruitmentApplicationTests {
	
    @Autowired
    private ITRecruitController itController;
    
    @Autowired
    private FileController fileController;
    
    @Autowired
    private CandidateController cController;

	@Test
    public void contexLoads() throws Exception {
        assertThat(itController).isNotNull();
        assertThat(fileController).isNotNull();
        assertThat(cController).isNotNull();
    }
	
    @Test
    public void testITRecruitController() {
    	ITRecruitController itRecruitController = new ITRecruitController();
        String result = itRecruitController.cancelAdd();
        assertEquals(result, "redirect:candidatelist");
    }

}
