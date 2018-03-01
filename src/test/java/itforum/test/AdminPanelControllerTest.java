package itforum.test;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;

import itforum.web.AdminPanelController;

public class AdminPanelControllerTest {
	
	@Test
	public void shouldShowAdminPanel() throws Exception{
		AdminPanelController adminPanelController = new AdminPanelController();
		MockMvc mockMvc = standaloneSetup(adminPanelController).build();
		
		mockMvc.perform(get("/admin").with(user("admin").password("adminadmin").roles("ADMIN"))).andExpect(view().name("adminPage"));
	}
}
