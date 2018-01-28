package itforum.test;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Objects;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import itforum.repositories.ForumPostRepository;
import itforum.repositories.UserRepository;
import itforum.web.HomeController;

public class HomeControllerTest {
	
	@InjectMocks
	HomeController homeController;
	
	@Mock
	UserRepository userRepository;
	
	@Mock
	ForumPostRepository forumPostRepository;
	
	private MockMvc mockMvc;
	
	@Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(homeController).build();
    }

	@Test
    public void testHomePage() throws Exception {
        when(userRepository.countUsers()).thenReturn(5L);
 
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("home"))
                .andExpect(model().attribute("totalPosts", Objects.toString(forumPostRepository.countPosts())))
                .andExpect(model().attribute("totalMembers",Objects.toString(userRepository.countUsers())));
    }
}
