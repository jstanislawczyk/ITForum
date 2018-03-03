package itforum.test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;

import itforum.web.LoginController;

public class LoginControllerTest {
	
	@Test
	public void shouldShowLoginPage() throws Exception{
		LoginController loginController = new LoginController();
		MockMvc mockMvc = standaloneSetup(loginController).build();
		mockMvc.perform(get("/login")).andExpect(view().name("loginPage"));
	}
}
