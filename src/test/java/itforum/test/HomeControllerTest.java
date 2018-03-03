package itforum.test;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;

import itforum.entities.ForumCategory;
import itforum.repositories.ForumCategoryRepository;
import itforum.repositories.ForumPostRepository;
import itforum.repositories.UserRepository;
import itforum.web.HomeController;

public class HomeControllerTest {
	
	@Test
	public void shouldShowHomePage() throws Exception{
		UserRepository mockUserRepository = mock(UserRepository.class);
		ForumPostRepository mockForumPostRepository = mock(ForumPostRepository.class);
		ForumCategoryRepository mockForumCategoryRepository = mock(ForumCategoryRepository.class);
		
		HomeController homeController = new HomeController(mockUserRepository, mockForumPostRepository, mockForumCategoryRepository);
		MockMvc mockMvc = standaloneSetup(homeController).build();
		
		mockMvc.perform(get("/")).andExpect(view().name("home"));
		mockMvc.perform(get("/homepage")).andExpect(view().name("home"));
	}
	
	@Test
	public void shouldLoadHomepageAttributes() throws Exception{
		UserRepository mockUserRepository = mock(UserRepository.class);
		ForumPostRepository mockForumPostRepository = mock(ForumPostRepository.class);
		ForumCategoryRepository mockForumCategoryRepository = mock(ForumCategoryRepository.class);
		
		List<ForumCategory> expectedCategories = createCategoryList(10);
		Long numberOfPosts = 33L;
		Long numberOfUsers = 11L;
		
		when(mockForumCategoryRepository.findAllCategories()).thenReturn(expectedCategories);
		when(mockForumPostRepository.countPosts()).thenReturn(numberOfPosts);
		when(mockUserRepository.countUsers()).thenReturn(numberOfUsers);
		
		HomeController homeController = new HomeController(mockUserRepository, mockForumPostRepository, mockForumCategoryRepository);
		MockMvc mockMvc = standaloneSetup(homeController).build();
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
