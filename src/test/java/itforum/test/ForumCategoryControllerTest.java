package itforum.test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.sql.Timestamp;
import java.util.LinkedList;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import itforum.entities.ForumCategory;
import itforum.entities.ForumPost;
import itforum.repositories.ForumCategoryRepository;
import itforum.repositories.ForumPostRepository;
import itforum.web.ForumCategoryController;


public class ForumCategoryControllerTest {
	
	private MockMvc mockMvc;
	private ForumCategoryController forumCategoryController;
	private ForumPostRepository mockForumPostRepository;
	private ForumCategoryRepository mockForumCategoryRepository;
	
	@Before
	public void setup(){
		mockForumPostRepository = mock(ForumPostRepository.class);
		mockForumCategoryRepository = mock(ForumCategoryRepository.class);
		forumCategoryController = new ForumCategoryController(mockForumPostRepository, mockForumCategoryRepository);
		
		mockMvc = MockMvcBuilders
					.standaloneSetup(forumCategoryController)
					.build();
	}
	
	@Test
	public void shouldNotFoundCategory() throws Exception{	
		String wrongCategoryTitle = "Scala";
		
		when(mockForumCategoryRepository.checkIfCategoryExistsByTitle(wrongCategoryTitle)).thenReturn(false);
		
		mockMvc.perform(get("/category/"+wrongCategoryTitle))
			.andExpect(status().isNotFound());
	}
	
	@Test
	public void shouldShowCategoryPageWithPosts() throws Exception{
		String categoryTitle ="PHP";
		ForumCategory expectedForumCategory = new ForumCategory(1L, categoryTitle, "PHP category");
		LinkedList<ForumPost> expectedForumPosts = createExpectedForumPostList(10);
		
		when(mockForumCategoryRepository.findCategoryByTitle(categoryTitle)).thenReturn(expectedForumCategory);
		when(mockForumPostRepository.findAllPostsByCategorySortByNewest(categoryTitle)).thenReturn(expectedForumPosts);
		when(mockForumCategoryRepository.checkIfCategoryExistsByTitle(categoryTitle)).thenReturn(true);
		
		mockMvc.perform(get("/category/"+categoryTitle))
			.andExpect(view().name("categoryPage"))
			.andExpect(model().attributeExists("categoryTitle", "posts"))
			.andExpect(model().attribute("categoryTitle", categoryTitle))
			.andExpect(model().attribute("posts", expectedForumPosts));
		
	}
	
	private LinkedList<ForumPost> createExpectedForumPostList(int count){
		LinkedList<ForumPost> forumPosts = new LinkedList<ForumPost>();
		for(int i=0; i<count; i++){
			forumPosts.add(new ForumPost(new Long(i), "test", "test content", new Timestamp(System.currentTimeMillis())));
		}
		return forumPosts;
	}
}
