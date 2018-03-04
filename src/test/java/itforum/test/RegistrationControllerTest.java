package itforum.test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import java.sql.Timestamp;

import org.junit.Test;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import itforum.entities.User;
import itforum.repositories.UserRepository;
import itforum.web.RegistrationController;

public class RegistrationControllerTest {

	@Test
	public void shouldShowRegistrationPage() throws Exception{
		UserRepository mockUserRepository = mock(UserRepository.class);
		PasswordEncoder mockPasswordEncoder = mock(PasswordEncoder.class);
		
		RegistrationController registrationController = new RegistrationController(mockUserRepository, mockPasswordEncoder);
		MockMvc mockMvc = standaloneSetup(registrationController).build();
		
		mockMvc.perform(get("/register")).andExpect(view().name("registrationPage"));
	}
	
	@Test
	public void shouldProcessRegistration() throws Exception{
		UserRepository mockUserRepository = mock(UserRepository.class);
		PasswordEncoder mockPasswordEncoder = mock(PasswordEncoder.class);
		
		User unsavedUser = new User("sampleUser", "test@gmail.com", "password", new Timestamp(System.currentTimeMillis()), 0, "USER", true);
		User savedUser = new User(1L, "sampleUser", "password", "test@gmail.com", new Timestamp(System.currentTimeMillis()), 0, "USER", true);
		
		when(mockUserRepository.saveUser(unsavedUser)).thenReturn(savedUser);
		when(mockUserRepository.isEmailAvailable(unsavedUser.getEmail())).thenReturn(true);
		when(mockUserRepository.isNickAvailable(unsavedUser.getNick())).thenReturn(true);
		
		RegistrationController registrationController = new RegistrationController(mockUserRepository, mockPasswordEncoder);
		MockMvc mockMvc = standaloneSetup(registrationController).build();
		
		mockMvc.perform(
			post("/register")
				.param("nick", unsavedUser.getNick())
				.param("email", unsavedUser.getEmail())
				.param("password", unsavedUser.getPassword()))	
			.andExpect(redirectedUrl("/profile/"+savedUser.getNick()));
		
	}
}
