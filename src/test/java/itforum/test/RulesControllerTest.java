package itforum.test;

import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import itforum.web.RulesController;

public class RulesControllerTest {
	
	@Test
	public void testRulesPage() throws Exception {
		RulesController rulesController = new RulesController();
		MockMvc mockMvc = standaloneSetup(rulesController).build();
		mockMvc.perform(get("/rules")).andExpect(view().name("rulesPage"));
	}
}
