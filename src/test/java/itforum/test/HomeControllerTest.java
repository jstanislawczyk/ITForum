package itforum.test;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import itforum.entities.ForumCategory;
import itforum.repositories.ForumCategoryRepository;
import itforum.repositories.ForumPostRepository;
import itforum.repositories.UserRepository;
import itforum.web.HomeController;

public class HomeControllerTest {
	
	private MockMvc mockMvc;
	private HomeController homeController;
	private UserRepository mockUserRepository;
	private ForumPostRepository mockForumPostRepository;
	private ForumCategoryRepository mockForumCategoryRepository;
	
	@Before
	public void setup(){
		mockUserRepository = mock(UserRepository.class);
		mockForumPostRepository = mock(ForumPostRepository.class);
		mockForumCategoryRepository = mock(ForumCategoryRepository.class);
		homeController = new HomeController(mockUserRepository, mockForumPostRepository, mockForumCategoryRepository);
		
		mockMvc = MockMvcBuilders
					.standaloneSetup(homeController)
					.build();
	}
	
	@Test
	public void shouldShowHomePage() throws Exception{			
		mockMvc.perform(get("/")).andExpect(view().name("home"));
		mockMvc.perform(get("/homepage")).andExpect(view().name("home"));
	}
	
	@Test
	public void shouldLoadHomepageAttributes() throws Exception{	
		List<ForumCategory> expectedCategories = createCategoryList(10);
		Long numberOfPosts = 33L;
		Long numberOfUsers = 11L;
		
		when(mockForumCategoryRepository.findAllCategories()).thenReturn(expectedCategories);
		when(mockForumPostRepository.countPosts()).thenReturn(numberOfPosts);
		when(mockUserRepository.countUsers()).thenReturn(numberOfUsers);
		
		mockMvc.perform(get("/"))
			.andExpect(view().name("home"))
			.andExpect(model().attributeExists("categories", "totalPosts", "totalMembers"))
			.andExpect(model().attribute("totalPosts", numberOfPosts))
			.andExpect(model().attribute("totalMembers", numberOfUsers))
			.andExpect(model().attribute("categories", hasItems(expectedCategories.toArray())));
	}
	
	private List<ForumCategory> createCategoryList(int count){
		List<ForumCategory> categories = new ArrayList<ForumCategory>();
		
		for(int i=0; i<count; i++){
			categories.add(new ForumCategory(new Long(i),"title", "description"));
		}
		
		return categories;
	}
}
