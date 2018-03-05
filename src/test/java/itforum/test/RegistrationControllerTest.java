package itforum.test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.sql.Timestamp;

import org.junit.Before;
import org.junit.Test;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import itforum.entities.User;
import itforum.repositories.UserRepository;
import itforum.web.RegistrationController;

public class RegistrationControllerTest {
	
	private MockMvc mockMvc;
	private RegistrationController registrationController;
	private UserRepository mockUserRepository;
	private PasswordEncoder mockPasswordEncoder;
	
	@Before
	public void setup(){
		mockUserRepository = mock(UserRepository.class);
		mockPasswordEncoder = mock(PasswordEncoder.class);
		registrationController = new RegistrationController(mockUserRepository, mockPasswordEncoder);
		
		mockMvc = MockMvcBuilders
				.standaloneSetup(registrationController)
				.build();
	}

	@Test
	public void shouldShowRegistrationPage() throws Exception{
		
		mockMvc.perform(get("/register")).andExpect(view().name("registrationPage"));
	}
	
	@Test
	public void shouldProcessRegistration() throws Exception{
		
		User unsavedUser = new User("sampleUser", "test@gmail.com", "password", new Timestamp(System.currentTimeMillis()), 0, "USER", true);
		User savedUser = new User(1L, "sampleUser", "password", "test@gmail.com", new Timestamp(System.currentTimeMillis()), 0, "USER", true);
		
		when(mockUserRepository.saveUser(unsavedUser)).thenReturn(savedUser);
		when(mockUserRepository.isEmailAvailable(unsavedUser.getEmail())).thenReturn(true);
		when(mockUserRepository.isNickAvailable(unsavedUser.getNick())).thenReturn(true);
		
		mockMvc.perform(
			post("/register")
				.param("nick", unsavedUser.getNick())
				.param("email", unsavedUser.getEmail())
				.param("password", unsavedUser.getPassword()))	
			.andExpect(redirectedUrl("/profile/"+savedUser.getNick()));
	}
	
	@Test
	public void shouldFailRegistrationWhenEmailIsNotAvailable() throws Exception{
		
		User unsavedUser = new User("sampleUser", "test@gmail.com", "password", new Timestamp(System.currentTimeMillis()), 0, "USER", true);
		
		when(mockUserRepository.isEmailAvailable(unsavedUser.getEmail())).thenReturn(false);
		when(mockUserRepository.isNickAvailable(unsavedUser.getNick())).thenReturn(true);
		
		mockMvc.perform(
			post("/register")
				.param("nick", unsavedUser.getNick())
				.param("email", unsavedUser.getEmail())
				.param("password", unsavedUser.getPassword()))	
			.andExpect(view().name("registrationPage"));	
	}
	
	@Test
	public void shouldFailRegistrationWhenNickIsNotAvailable() throws Exception{
		
		User unsavedUser = new User("sampleUser", "test@gmail.com", "password", new Timestamp(System.currentTimeMillis()), 0, "USER", true);
		
		when(mockUserRepository.isEmailAvailable(unsavedUser.getEmail())).thenReturn(true);
		when(mockUserRepository.isNickAvailable(unsavedUser.getNick())).thenReturn(false);

		mockMvc.perform(
			post("/register")
				.param("nick", unsavedUser.getNick())
				.param("email", unsavedUser.getEmail())
				.param("password", unsavedUser.getPassword()))	
			.andExpect(view().name("registrationPage"));	
	}
}
