package itforum.test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import itforum.entities.ForumPost;
import itforum.entities.PostComment;
import itforum.entities.User;
import itforum.repositories.ForumPostRepository;
import itforum.repositories.PostCommentRepository;
import itforum.repositories.UserRepository;
import itforum.web.PostController;

public class PostControllerTest {
	
	private MockMvc mockMvc;
	private PostController postController;
	private ForumPostRepository mockForumPostRepository;
	private PostCommentRepository mockPostCommentRepository;
	private UserRepository mockUserRepository;
	
	@Before
	public void setup(){
		mockForumPostRepository = mock(ForumPostRepository.class);
		mockPostCommentRepository = mock(PostCommentRepository.class);
		mockUserRepository = mock(UserRepository.class);
		postController = new PostController(mockForumPostRepository, mockPostCommentRepository, mockUserRepository);
		
		mockMvc = MockMvcBuilders
						.standaloneSetup(postController)
						.build();
	}
	
	@Test
	public void shouldNotFoundPageIfPostDoesNotExists() throws Exception{
		Long postId = 14L;

		when(mockForumPostRepository.findPostById(postId)).thenReturn(null);
		
		mockMvc.perform(get("/post/"+postId))
					.andExpect(status().isNotFound());
	}
	
	@Test
	public void shouldShowPostWithComments() throws Exception{
		Long postId = 14L;
		List<PostComment> expectedComments = createExpectedCommentsList(7);
		List<String> expectedNicknames= createExpectedUsersNicknamesList(7);	
		User expectedUser = new User.Builder().id(1L).nick("ExpectedUser").password("useruser").build();
		ForumPost expectedForumPost = new ForumPost(postId, "test", "test content", new Timestamp(System.currentTimeMillis()), expectedUser);
		
		when(mockPostCommentRepository.getAllCommentsWithUserIdByPostId(postId)).thenReturn(expectedComments);
		when(mockUserRepository.getUserById(1L)).thenReturn(expectedUser);
		when(mockForumPostRepository.findPostById(postId)).thenReturn(expectedForumPost);
		
		mockMvc.perform(get("/post/"+postId))
				.andExpect(view().name("forumPostPage"))
				.andExpect(model().attributeExists("nick","post","postComment","comments","nicknames"))
				.andExpect(model().attribute("nick", expectedForumPost.getUser().getNick()))
				.andExpect(model().attribute("post", expectedForumPost))
				.andExpect(model().attribute("comments", expectedComments))
				.andExpect(model().attribute("nicknames", expectedNicknames));		
	}
	
	private List<PostComment> createExpectedCommentsList(int count){
		List<PostComment> expectedComments = new ArrayList<>();
		
		for(int i=0; i<count; i++){
			expectedComments.add(new PostComment(new Long(i), "Comment content", new Timestamp(System.currentTimeMillis()), 1L));
		}
		
		return expectedComments;
	}
	
	private List<String> createExpectedUsersNicknamesList(int count){
		List<String> expectedNicknames = new ArrayList<>();
		
		for(int i=0; i<count; i++){
			expectedNicknames.add("ExpectedUser");
		}
		
		return expectedNicknames;
	}
}
